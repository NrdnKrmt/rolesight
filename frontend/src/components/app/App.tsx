import {Routes, Route} from "react-router-dom";
import PreferenceInfo from "../preferenceInfo/PreferenceInfo.tsx";
import AddGame from "../addGame/AddGame.tsx";

function App() {

    return (
        <>
            <h1>Rolesight</h1>
            <Routes>
                <Route path="/" element={<PreferenceInfo/>}/>
                <Route path="/add" element={<AddGame/>}/>
            </Routes>
        </>
    )
}

export default App
