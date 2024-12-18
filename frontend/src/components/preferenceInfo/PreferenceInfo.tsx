import axios from "axios";
import {useEffect, useState} from "react";
import {Preference} from "./Preference.ts"
import "./PreferenceInfo.css"
import {useNavigate} from "react-router-dom";
import PreferenceInfoCard from "../preferenceInfoCard/preferenceInfoCard.tsx";

type Props = {
    user: string
}

export default function PreferenceInfo({user}: Readonly<Props>) {

    const [preferences, setPreferences] = useState<Preference[]>([]);

    useEffect(() => {
        axios
            .get(`/api/users/${user}/preferences`)
            .then((response) => {
                setPreferences(response.data);
            })
    }, [user]);

    const navigate = useNavigate();

    function goToAdd() {
        navigate("/add");
    }

    return (
        <div className="preference-info-container">
            <div className="preferences-head">
                <h2>My Preferences:</h2>
                <button className="add-game-button" onClick={goToAdd}>Add Game</button>
            </div>
            <div className="preferences-boxes">
                {preferences.map((pref) => (
                    <div className="preference-box" key={pref.gameId}>
                        <PreferenceInfoCard pref={pref} setPreferences={setPreferences} user={user}/>
                    </div>
                ))}
            </div>
        </div>
    )
}
