import axios from "axios";
import {useState} from "react";
import {Game} from "../addGame/Game.ts";

type Props = {
    prop1: Game
}

function AddGameCard(props: Readonly<Props>) {
    const { prop1: pref } = props;

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
            <img src={pref.image} alt={pref.name} className="game-image"/>
            <div className="game-info">
                <h2>{pref.name}</h2>
                <p><strong>Genre:</strong> {pref.genre}</p>
                <p>{pref.description}</p>
                <select id="role-select" onChange={event => setSelectedRole(event.target.value)}>
                    <option value="">Select a role</option>
                    <option value="Tank">Tank</option>
                    <option value="Damage Dealer">Damage Dealer</option>
                    <option value="Support">Support</option>
                </select>
                <button onClick={() => handleAddPreference("1", `${pref.id}`, `${selectedRole}`)}>Add</button>
            </div>
        </>
    )
}

export default AddGameCard
