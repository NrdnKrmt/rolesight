import axios from "axios";
import {useEffect, useState} from "react";
import {Preference} from "./Preference.ts"
import "./PreferenceInfo.css"

function PreferenceInfo() {

    const [preferences, setPreferences] = useState<Preference[]>([]);

    useEffect(() => {
        axios
            .get("/api/users/1/preferences")
            .then((response) => {
                setPreferences(response.data);
            })
    }, []);

    return (
        <div className="preference-info-container">
            <h2>Preferred Games:</h2>
            <div className="preferences-boxes">
                {preferences.map((pref) => (
                    <div className="preference-box" key={pref.gameName}>
                    <p>{pref.gameName}</p>
                    <p>({pref.role})</p>
                    </div>
                ))}
            </div>
        </div>
    )
}

export default PreferenceInfo
