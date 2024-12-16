import axios from "axios";
import {Preference} from "../preferenceInfo/Preference.ts";
import RoleSelect from "../roleSelect/RoleSelect.tsx";
import {Dispatch, SetStateAction, useState} from "react";

type Props = {
    pref: Preference
    setPreferences: Dispatch<SetStateAction<Preference[]>>
    user: string
}

function PreferenceInfoCard({pref, setPreferences, user}: Readonly<Props>) {

    const [preference, setPreference] = useState<Preference>(pref);
    const [selectedRole, setSelectedRole] = useState<string>("");
    const [editMode, setEditMode] = useState<boolean>(false);

    const handleRemovePreference = async (userId: string, gameId: string): Promise<void> => {
        await axios
            .delete(`/api/users/${userId}/preferences/${gameId}`, {})

        setPreferences((prev) => prev.filter(
            (currentPreferences) => gameId !== currentPreferences.gameId
        ))
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
            <img src={preference.gameImage} alt={preference.gameName} className="game-image"/>
            <div>
                <h2>{preference.gameName}</h2>
                <p><strong>Genre:</strong> {preference.gameGenre}</p>
                <p>{preference.gameDescription}</p>
                <h3>{preference.preferredRole}</h3>
                {editMode ? (
                    <>
                        <RoleSelect setSelectedRole={setSelectedRole}/>
                        <button
                            onClick={() => handleEditPreference(`${user}`, `${preference.gameId}`, `${selectedRole}`)}>Update
                        </button>
                        <button onClick={() => setEditMode(false)}>Cancel</button>
                    </>
                ) : (
                    <>
                        <button onClick={() => setEditMode(true)}>Edit</button>
                        <button onClick={() => handleRemovePreference(`${user}`, `${preference.gameId}`)}>Remove</button>
                    </>
                )}
            </div>
        </>
    )
}

export default PreferenceInfoCard
