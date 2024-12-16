import {Routes, Route} from "react-router-dom";
import PreferenceInfo from "../preferenceInfo/PreferenceInfo.tsx";
import AddGame from "../addGame/AddGame.tsx";
import Header from "../header/Header.tsx";
import {useEffect, useState} from "react";
import axios from "axios";
import ProtectedRoute from "../../ProtectedRoute.tsx";
import LoginPage from "../loginPage/LoginPage.tsx";

export default function App() {

    const [user, setUser] = useState<string>("")

    useEffect(() => {
        getUser()
    }, []);

    function getUser() {
        axios.get("/api/users/me")
            .then(response => {
                console.log(response.data)
                setUser(response.data)
            })
    }

    return (
        <>
            <Header user={user} getUser={getUser}/>
            <Routes>
                <Route path="/login" element={<LoginPage user={user}/>}/>
                <Route element={<ProtectedRoute user={user}/>}>
                    <Route path="/" element={<PreferenceInfo user={user}/>}/>
                    <Route path="/add" element={<AddGame user={user}/>}/>
                </Route>
            </Routes>
        </>
    )
}
