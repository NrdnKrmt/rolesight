import { Link } from "react-router-dom"
import "./Header.css"

function Header() {

    function login() {
        const host: string = window.location.host === "localhost:5173" ? "http://localhost:8080" : window.location.origin

        window.open(host + "/oauth2/authorization/github", "_self")
    }

    return (
        <div className="header-container">
            <h1><Link to="/">Rolesight</Link></h1>
            <button onClick={login}>Login</button>
        </div>
    )
}

export default Header
