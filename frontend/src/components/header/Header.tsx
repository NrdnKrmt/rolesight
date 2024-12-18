import { Link } from "react-router-dom"
import "./Header.css"
import axios from "axios";

type Props = {
    user: string
    getUser: () => void
}

function Header({user, getUser}: Readonly<Props>) {

    function logout() {
        axios.post("/api/users/logout")
            .then(()=> getUser())
    }

    return (
        <div className="header-container">
            <h1 className="headline"><Link to="/">Rolesight</Link></h1>
            {user && user !== "anonymousUser" && (
                <button className="logout-button" onClick={logout}>LOGOUT</button>
            )}
        </div>
    )
}

export default Header
