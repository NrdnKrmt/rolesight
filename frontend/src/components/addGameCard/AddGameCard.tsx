import axios from "axios";
import {Dispatch, SetStateAction, useState} from "react";
import {Game} from "../addGame/Game.ts";
import RoleSelect from "../roleSelect/RoleSelect.tsx";
import "./AddGameCard.css"

type Props = {
    game: Game
    setAvailableGames: Dispatch<SetStateAction<Game[]>>
    user: string;
}

function AddGameCard({game, setAvailableGames, user}: Readonly<Props>) {

    const [selectedRole, setSelectedRole] = useState<string>("");

    const handleAddPreference = async (userId: string, gameId: string, role: string): Promise<void> => {
        await axios
            .post(`/api/users/${userId}/preferences/${gameId}/${role}`, {})

        setAvailableGames((prev) => prev.filter(
                (currentAvailableGame) => gameId !== currentAvailableGame.id
            )
        )
    };

    return (
        <>
            <img src={game.image} alt={game.name} className="game-image"/>
            <div className="game-info">
                <h2 className="game-name">{game.name}</h2>
                <p className="game-genre"><strong>Genre:</strong> {game.genre}</p>
                <p className="game-description">{game.description}</p>
                <div className="role-select">
                    <RoleSelect setSelectedRole={setSelectedRole}/>
                    <button className="add-button"
                            onClick={() => handleAddPreference(`${user}`, `${game.id}`, `${selectedRole}`)}>Add
                    </button>
                </div>
            </div>
        </>
    )
}

export default AddGameCard
