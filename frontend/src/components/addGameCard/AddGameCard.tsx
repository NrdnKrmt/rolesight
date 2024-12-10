import axios from "axios";
import {useState} from "react";
import {Game} from "../addGame/Game.ts";

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
                <select id="role-select" onChange={event => setSelectedRole(event.target.value)}>
                    <option value="">Select a role</option>
                    <option value="Tank">Tank</option>
                    <option value="Damage Dealer">Damage Dealer</option>
                    <option value="Support">Support</option>
                </select>
                <button onClick={() => handleAddPreference("1", `${game.id}`, `${selectedRole}`)}>Add</button>
            </div>
        </>
    )
}

export default AddGameCard
