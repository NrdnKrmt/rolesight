import axios from "axios";
import {useEffect, useState} from "react";
import {Preference} from "./Preference.ts"
import "./PreferenceInfo.css"
import {useNavigate} from "react-router-dom";
import PreferenceInfoCard from "../preferenceInfoCard/preferenceInfoCard.tsx";

function PreferenceInfo() {

    const [preferences, setPreferences] = useState<Preference[]>([]);

    useEffect(() => {
        axios
            .get("/api/users/1/preferences")
            .then((response) => {
                setPreferences(response.data);
            })
    }, []);

    const navigate = useNavigate();

    function goToAdd() {
        navigate("/add");
    }

    return (
        <div className="preference-info-container">
            <div className="add-game">
                <button onClick={goToAdd}>Add Game</button>
            </div>
            <div className="preferences-boxes">
                {preferences.map((pref) => (
                    <div className="preference-box" key={pref.gameId}>
                        <PreferenceInfoCard pref={pref} setPreferences={setPreferences}/>
                    </div>
                ))}
            </div>
        </div>
    )
}

export default PreferenceInfo
