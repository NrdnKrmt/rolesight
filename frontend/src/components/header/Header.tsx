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
            <h1><Link to="/">Rolesight</Link></h1>
            {user && user !== "anonymousUser" && (
                <button onClick={logout}>Logout</button>
            )}
            <p>{user}</p>
        </div>
    )
}

export default Header
