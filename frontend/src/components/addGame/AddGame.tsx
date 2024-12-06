import {useEffect, useState} from "react";
import {Game} from "./Game.ts";
import axios from "axios";

import "./AddGame.css"
import AddGameCard from "../addGameCard/AddGameCard.tsx";

function AddGame() {

    const [availableGames, setAvailableGames] = useState<Game[]>([]);

    useEffect(() => {
        axios
            .get("/api/games/1")
            .then((response) => {
                setAvailableGames(response.data);
            })
    }, []);

    return (
        <div className="available-games-container">
            {availableGames.map((pref) => (
                <div className="game-container" key={pref.id}>
                    <AddGameCard prop1={pref}/>
                </div>
            ))}
        </div>
    )
}

export default AddGame
