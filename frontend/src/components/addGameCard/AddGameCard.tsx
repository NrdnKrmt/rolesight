import axios from "axios";
import {Dispatch, SetStateAction, useState} from "react";
import {Game} from "../addGame/Game.ts";
import RoleSelect from "../roleSelect/RoleSelect.tsx";

type Props = {
    game: Game
    setAvailableGames: Dispatch<SetStateAction<Game[]>>
}

function AddGameCard({game, setAvailableGames}: Readonly<Props>) {

    const [selectedRole, setSelectedRole] = useState<string>("");

    const handleAddPreference = (userId: string, gameId: string, role: string): void => {
        axios
            .post(`/api/users/${userId}/preferences/${gameId}/${role}`, {})
            .then(response => {
                console.log(response.data);
                setAvailableGames((prev) => prev.filter(
                    (currentAvailableGame) => gameId !== currentAvailableGame.id
                ))
            })
    };

    return (
        <>
            <img src={game.image} alt={game.name} className="game-image"/>
            <div className="game-info">
                <h2>{game.name}</h2>
                <p><strong>Genre:</strong> {game.genre}</p>
                <p>{game.description}</p>
                <RoleSelect setSelectedRole={setSelectedRole}/>
                <button onClick={() => handleAddPreference("1", `${game.id}`, `${selectedRole}`)}>Add</button>
            </div>
        </>
    )
}

export default AddGameCard
