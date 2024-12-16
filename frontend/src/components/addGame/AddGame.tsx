import {useEffect, useState} from "react";
import {Game} from "./Game.ts";
import axios from "axios";
import "./AddGame.css"
import AddGameCard from "../addGameCard/AddGameCard.tsx";

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
    }, []);

    return (
        <div className="available-games-container">
            {availableGames.map((game) => (
                <div className="game-container" key={game.id}>
                    <AddGameCard game={game} setAvailableGames={setAvailableGames} user={user}/>
                </div>
            ))}
        </div>
    )
}
