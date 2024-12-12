import axios from "axios";
import {useState} from "react";
import {Game} from "../addGame/Game.ts";
import RoleSelect from "../roleSelect/RoleSelect.tsx";

type Props = {
    prop1: Game
}

function AddGameCard(props: Readonly<Props>) {
    const { prop1: game } = props;

    const [selectedRole, setSelectedRole] = useState<string>("");

    const handleAddPreference = (userId: string, gameId: string, role: string): void => {
        axios
            .post(`/api/users/${userId}/preferences/${gameId}/${role}`, {})
            .then(response => {
                console.log(response.data);
                window.location.reload();
            })
    };

    return (
        <>
            <img src={game.image} alt={game.name} className="game-image"/>
            <div className="game-info">
                <h2>{game.name}</h2>
                <p><strong>Genre:</strong> {game.genre}</p>
                <p>{game.description}</p>
                <RoleSelect setSelectedRole={setSelectedRole} />
                <button onClick={() => handleAddPreference("1", `${game.id}`, `${selectedRole}`)}>Add</button>
            </div>
        </>
    )
}

export default AddGameCard
