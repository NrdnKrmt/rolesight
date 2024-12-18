import axios from "axios";
import {Preference} from "../preferenceInfo/Preference.ts";
import RoleSelect from "../roleSelect/RoleSelect.tsx";
import {Dispatch, SetStateAction, useState} from "react";
import "./preferenceInfoCard.css"

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
            <div className="pref-game-info">
                <h2 className="pref-game-name">{preference.gameName}</h2>
                <p className="pref-game-genre"><strong>Genre:</strong> {preference.gameGenre}</p>
                <p className="pref-game-description">{preference.gameDescription}</p>
                <h3 className="pref-role">{preference.preferredRole}</h3>
                <div className="modify">
                    {editMode ? (
                        <div>
                            <RoleSelect setSelectedRole={setSelectedRole}/>
                            <button
                                className="update-button" onClick={() => handleEditPreference(`${user}`, `${preference.gameId}`, `${selectedRole}`)}>✓
                            </button>
                            <button
                                className="cancel-button" onClick={() => setEditMode(false)}>Cancel
                            </button>
                        </div>
                    ) : (
                        <>
                            <button className="edit-button" onClick={() => setEditMode(true)}>Edit</button>
                            <button className="remove-button" onClick={() => handleRemovePreference(`${user}`, `${preference.gameId}`)}>Remove
                            </button>
                        </>
                    )}
                </div>
            </div>
        </>
    )
}

export default PreferenceInfoCard
