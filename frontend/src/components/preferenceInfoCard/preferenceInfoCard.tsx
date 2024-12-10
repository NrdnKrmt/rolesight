import axios from "axios";
import {Preference} from "../preferenceInfo/Preference.ts";

type Props = {
    prop1: Preference
}

function PreferenceInfoCard(props: Readonly<Props>) {
    const { prop1: pref } = props;

    const handleRemovePreference = (userId: string, gameId: string): void => {
        axios
            .delete(`/api/users/${userId}/preferences/${gameId}`, {})
            .then(response => {
                console.log(response.data);
                window.location.reload();
            })
    };

    return (
        <>
            <h2>{pref.gameName}</h2>
            <p><strong>Genre:</strong> {pref.gameGenre}</p>
            <img src={pref.gameImage} alt={pref.gameName} className="game-image"/>
            <p>{pref.gameDescription}</p>
            <h3>{pref.preferredRole}</h3>
            <button onClick={() => handleRemovePreference("1", `${pref.gameId}`)}>Remove</button>
        </>
    )
}

export default PreferenceInfoCard
