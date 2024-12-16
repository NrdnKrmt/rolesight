import { Link } from "react-router-dom"
import "./Header.css"
import axios from "axios";

function Header() {

    function login() {
        const host: string = window.location.host === "localhost:5173" ? "http://localhost:8080" : window.location.origin

        window.open(host + "/oauth2/authorization/github", "_self")
    }

    function getUser() {
        axios.get("/api/users/me")
            .then(response => {
                console.log(response.data)
            })
    }

    return (
        <div className="header-container">
            <h1><Link to="/">Rolesight</Link></h1>
            <button onClick={login}>Login</button>
            <button onClick={getUser}>Me</button>
        </div>
    )
}

export default Header
