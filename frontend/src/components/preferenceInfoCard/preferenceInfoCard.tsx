import axios from "axios";
import {Preference} from "../preferenceInfo/Preference.ts";
import RoleSelect from "../roleSelect/RoleSelect.tsx";
import {useState} from "react";

type Props = {
    preference: Preference
}

function PreferenceInfoCard(props: Readonly<Props>) {

    const [preference, setPreference] = useState<Preference>(props.preference);
    const [selectedRole, setSelectedRole] = useState<string>("");
    const [editMode, setEditMode] = useState<boolean>(false);

    const handleRemovePreference = (userId: string, gameId: string): void => {
        axios
            .delete(`/api/users/${userId}/preferences/${gameId}`, {})
            .then(response => {
                console.log(response.data);
                window.location.reload();
            })
    };

    const handleEditPreference = (userId: string, gameId: string, role: string): void => {
        axios
            .put(`/api/users/${userId}/preferences/${gameId}/${role}`, {})
            .then(response => {
                console.log(response.data);
                setPreference((prev) => ({
                    ...prev,
                    preferredRole: role
                }));
                setEditMode(false);
            })
    };

    return (
        <>
            <h2>{preference.gameName}</h2>
            <p><strong>Genre:</strong> {preference.gameGenre}</p>
            <img src={preference.gameImage} alt={preference.gameName} className="game-image"/>
            <p>{preference.gameDescription}</p>
            <h3>{preference.preferredRole}</h3>
            {editMode ? (
                <>
                    <RoleSelect setSelectedRole={setSelectedRole} />
                    <button onClick={() => handleEditPreference("1", `${preference.gameId}`, `${selectedRole}`)}>Update</button>
                    <button onClick={() => setEditMode(false)}>Cancel</button>
                </>
            ) : (
                <>
                    <button onClick={() => setEditMode(true)}>Edit</button>
                    <button onClick={() => handleRemovePreference("1", `${preference.gameId}`)}>Remove</button>
                </>
            )}
        </>
    )
}

export default PreferenceInfoCard
