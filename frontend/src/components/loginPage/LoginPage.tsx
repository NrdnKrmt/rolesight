import {useNavigate} from "react-router-dom";
import {useEffect} from "react";
import "./LoginPage.css"

type Props = {
    user: string
}

export default function LoginPage({user}: Readonly<Props>) {
    const navigate = useNavigate();

    useEffect(() => {
        if (user && user !== "anonymousUser") {
            navigate("/");
        }
    }, [user, navigate]);

    function login() {
        const host: string = window.location.host === "localhost:5173" ? "http://localhost:8080" : window.location.origin

        window.open(host + "/oauth2/authorization/github", "_self")
    }

    return (
        <div className="login-container">
            <button onClick={login}>Login with GitHub</button>
        </div>
    )
}
