import {useEffect, useState} from "react";
import {Game} from "./Game.ts";
import axios from "axios";
import "./AddGame.css"
import AddGameCard from "../addGameCard/AddGameCard.tsx";
import {useNavigate} from "react-router-dom";

type Props = {
    user: string;
}

export default function AddGame({user}: Readonly<Props>) {

    const [availableGames, setAvailableGames] = useState<Game[]>([]);

    useEffect(() => {
        axios
            .get(`/api/games/${user}`)
            .then((response) => {
                setAvailableGames(response.data);
            })
    }, [user]);

    const navigate = useNavigate();

    function goToHome() {
        navigate("/");
    }

    return (
        <div className="available-games-container">
            <div className="available-games-head">
                <h2>Available Games:</h2>
                <button className="back-button" onClick={goToHome}>↩ Back</button>
            </div>
            <div className="game-boxes">
                {availableGames.map((game) => (
                    <div className="game-container" key={game.id}>
                        <AddGameCard game={game} setAvailableGames={setAvailableGames} user={user}/>
                    </div>
                ))}
            </div>
        </div>
    )
}
