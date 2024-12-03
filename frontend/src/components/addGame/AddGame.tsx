import {useEffect, useState} from "react";
import {Game} from "./Game.ts";
import axios from "axios";
import "./AddGame.css"

function AddGame() {

    const [gamesToAdd, setGamesToAdd] = useState<Game[]>([]);

    useEffect(() => {
        axios
            .get("/api/games")
            .then((response) => {
                setGamesToAdd(response.data);
            })
    }, []);

    return (
        <div className="games-to-add-container">
                {gamesToAdd.map((pref) => (
                    <div className="game-to-add-container" key={pref.id}>
                        <img src={pref.image} alt={pref.name} className="game-image"/>
                        <div className="game-info">
                            <h2>{pref.name}</h2>
                            <p><strong>Genre:</strong> {pref.genre}</p>
                            <p>{pref.description}</p>
                            <button>Add</button>
                        </div>
                    </div>
                ))}
        </div>
    )
}

export default AddGame
