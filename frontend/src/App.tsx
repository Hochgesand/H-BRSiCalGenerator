import React from 'react';
import './App.scss';
import Lander from "./actualApp/Lander";
import {BrowserRouter, Routes} from 'react-router-dom';
import {Route} from "react-router-native";
import FAQ from "./FAQ/FAQ";

function App() {
    return (
        <div className="App">
            <BrowserRouter>
                <Routes>
                    <Route path={"/H-BRSiCalGenerator"} element={<Lander/>}/>
                    <Route path={"/H-BRSiCalGenerator/FAQ"} element={<FAQ/>}/>
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;
