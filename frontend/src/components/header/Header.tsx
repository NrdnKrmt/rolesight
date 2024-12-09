import { Link } from "react-router-dom"
import "./Header.css"

function Header() {

    return (
        <div className="header-container">
            <h1><Link to="/">Rolesight</Link></h1>
        </div>
    )
}

export default Header
