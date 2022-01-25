import VeranstaltungsSelector from "./VeranstaltungsSelector/VeranstaltungsSelector";
import React, {useEffect, useState} from "react";
import {baseUrl} from "../Objects/endpoints";
import useGetRequest from "../api/useGetRequest";
import Loading from "../Loading";
import Error from "../Error";

export default function Lander() {
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

  if (loading) {
    return <Loading/>
  }

  if (error.length > 0) {
    return (
      <Error msg={error}/>
    );
  }

  return(
    <div>
      <div className={"rounded-box p-4 bg-base-300 mb-4"}>
        <h2 className={"text-4xl mb-2"}>H-BRS Kalendergenerator v1.0</h2>
        <p>Ich übernehme keine Haftung für die Richtigkeit der generierten Daten, alles nach bestem Wissen und
          Gewissen. Fehler bitte an a@andrevr.de melden.</p>
        <p>Wenn's euch gefällt, empfehlt es euren Kommilitonen! 😁</p>
      </div>

      {selectedStudiengang.length === 0 ? <div className={"rounded-box p-3 bg-base-300 w-1/4"}>
        <div className={"dropdown w-full"}>
          <div tabIndex={0} className=" btn btn-lg w-full">Bitte Studiengang auswählen!</div>
          <ul tabIndex={0} className="p-2 shadow menu dropdown-content bg-base-100 rounded-box w-full">
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
