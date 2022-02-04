import React, {useEffect, useState} from "react";
import {baseUrl} from "../Objects/endpoints";
import useGetRequest from "../api/useGetRequest";
import Loading from "../Loading";
import Error from "../Error";
import {useNavigate} from "react-router-dom";
import {Studiengang} from "../Objects/Studiengang";

export default function Lander(this: any) {
    const navigate = useNavigate();
    const studiengaengePath = `${baseUrl}/studiengang/getStudiengaenge`;
    const {getData} = useGetRequest({path: studiengaengePath})
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [studiengaenge, setStudiengaenge] = useState([] as Studiengang[]);

    useEffect(() => {
        async function fetchData() {
            await getData().then(function (json) {
                setStudiengaenge(json);
                setLoading(false)
                setError("")
            }).catch(err => {
                    console.log(err.message)
                    setError(err.message)
                    setLoading(false);
                }
            );
        }

        fetchData();
    }, []); // eslint-disable-line react-hooks/exhaustive-deps

    function showHelp() {
        navigate("/H-BRSiCalGenerator/FAQ");
    }

    if (loading) {
        return <Loading/>
    }

    if (error.length > 0) {
        return (
            <Error msg={error}/>
        );
    }

    return (
        <div className={"max-h-screen"}>
            <div className={"rounded-box p-3 bg-base-300 md:w-1/2 w-full m-auto"}>
                <div className={"rounded-box p-4 bg-base-100 mb-4"}>
                    <h2 className={"text-3xl mb-2 text-left md:text-center font-bold"}>H-BRS Kalendergenerator</h2>
                    <p>Ich übernehme keine Haftung für die Richtigkeit der generierten Daten, alles nach bestem Wissen
                        und
                        Gewissen. </p>
                    <p>Fehler bitte an andre@schmitz.gg oder Discord: "Nullteiler#5241" melden.</p>
                    <p>Jetzt auch mobileoptimized/responsive!</p>
                    <p>Wenn's euch gefällt, empfehlt es euren Kommilitonen! 😁</p>
                </div>
                <div className={"dropdown w-full"}>
                    <a href={"https://github.com/Hochgesand/H-BRSiCalGenerator"} target="_blank"
                       rel="noopener noreferrer">
                        <button className={"btn md:btn-lg w-full mb-2 btn-ghost"}>Gib mir einen Stern auf Github ❤
                        </button>
                    </a>
                    <button className={"btn md:btn-lg w-full"} onClick={showHelp}>FAQ / HILFE!</button>
                    <div tabIndex={0} className="btn md:btn-lg w-full mt-2">Bitte Studiengang auswählen!</div>
                    <ul tabIndex={0} className="p-2 shadow menu dropdown-content bg-base-300 rounded-box w-full">
                        {studiengaenge.map(studiengang => (
                            <li className={"m-1 text-white"}>
                                <button
                                    className={"btn"}
                                    onClick={() => navigate(`/H-BRSiCalGenerator/${studiengang.name}`)}
                                >
                                    {studiengang.name}
                                </button>
                            </li>
                        ))}
                    </ul>
                </div>
            </div>
            <a href={process.env.PUBLIC_URL + "/NVIDIA_Share_zDDqgVCJOh.gif"}>
                <div className={"mt-4 rounded-box bg-base-300 px-3 pt-3 pb-4 md:w-1/2 w-full m-auto md:invisible md:h-0"}>
                    <p className={"text-2xl font-bold text-center"}>Tipp des Jahrtausends</p>
                    <div className={"text-center font-bold"}>
                        <p>Pack mich auf den Homescreen!</p>
                        <p>Drück mich!</p>
                    </div>
                    <img src={process.env.PUBLIC_URL + "/ScreenshotMitIcon.png"} alt={"Heftiges Bild"} className={"h-60 m-auto"}/>
                </div>
            </a>
        </div>
    );
}
