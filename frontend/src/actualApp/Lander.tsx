import VeranstaltungsSelector from "./VeranstaltungsSelector/VeranstaltungsSelector";
import React, {useEffect, useState} from "react";
import {baseUrl} from "../Objects/endpoints";
import useGetRequest from "../api/useGetRequest";
import Loading from "../Loading";
import Error from "../Error";
import {useHistory} from "react-router-dom";

export default function Lander() {
  const history = useHistory();
  const studiengaengePath = `${baseUrl}/getStudiengaenge`;
  const {getData} = useGetRequest({path: studiengaengePath})
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [studiengaenge, setStudiengaenge] = useState([] as string[]);
  const [selectedStudiengang, setSelectedStudiengang] = useState("");

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
    history.push("/FAQ");
  }

  if (loading) {
    return <Loading/>
  }

  if (error.length > 0) {
    return (
      <Error msg={error}/>
    );
  }

  return(
    <div className={"max-h-screen"}>
        {selectedStudiengang.length === 0 ? <div className={"rounded-box p-3 bg-base-300 md:w-1/3 w-full"}>
          <div className={"rounded-box p-4 bg-base-100 mb-4"}>
            <h2 className={"text-4xl mb-2"}>H-BRS Kalendergenerator v1.1</h2>
            <p>Ich übernehme keine Haftung für die Richtigkeit der generierten Daten, alles nach bestem Wissen und
              Gewissen. </p>
            <p>Fehler bitte an a@andrevr.de melden.</p>
            <p>Ich habs nicht fürs Smartphone entwickelt, benutzt es lieber am PC. Mobile first ist noch nicht ganz so meine stärke</p>
            <p>Wenn's euch gefällt, empfehlt es euren Kommilitonen! 😁</p>
          </div>
          <div className={"dropdown w-full"}>
            <button className={"btn md:btn-lg w-full"} onClick={showHelp}>FAQ / HILFE!</button>
          <div tabIndex={0} className="btn md:btn-lg w-full mt-2">Bitte Studiengang auswählen!</div>
          <ul tabIndex={0} className="p-2 shadow menu dropdown-content bg-base-300 rounded-box w-full">
            {studiengaenge.map(studiengang => (
              <li className={"m-1"}>
                <button
                  className={"btn"}
                  onClick={() => setSelectedStudiengang(studiengang)}
                >
                  {studiengang}
                </button>
              </li>
            ))}
          </ul>
        </div>
      </div> : ""}

      {selectedStudiengang.length !== 0 ? <VeranstaltungsSelector veranstaltung={selectedStudiengang}/> : ""}
    </div>
  );
}
