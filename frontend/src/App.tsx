import React from 'react';
import './App.scss';
import Lander from "./actualApp/Lander";
import {Routes} from 'react-router-dom';
import {Route} from "react-router";
import FAQ from "./FAQ/FAQ";
import VeranstaltungsSelector from "./actualApp/VeranstaltungsSelector/VeranstaltungsSelector";

function App() {
    return (
        <div className="App">
            <Routes>
                <Route path={"/"} element={<Lander/>}/>
                <Route path={"/H-BRSiCalGenerator"} element={<Lander/>}/>
                <Route path={"/H-BRSiCalGenerator/FAQ"} element={<FAQ/>}/>
                <Route path={"/H-BRSiCalGenerator/:studiengang"} element={<VeranstaltungsSelector/>}/>
            </Routes>
        </div>
    );
}

export default App;
