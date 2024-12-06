import {Routes, Route} from "react-router-dom";
import PreferenceInfo from "../preferenceInfo/PreferenceInfo.tsx";
import AddGame from "../addGame/AddGame.tsx";
import Header from "../header/Header.tsx";

function App() {

    return (
        <>
            <Header/>
            <Routes>
                <Route path="/" element={<PreferenceInfo/>}/>
                <Route path="/add" element={<AddGame/>}/>
            </Routes>
        </>
    )
}

export default App
