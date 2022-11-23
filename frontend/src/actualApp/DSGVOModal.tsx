import React, {useEffect, useState} from "react";

export default function DSGVOModal() {
  const [acknowledge, setAcknowledge] = useState(true);

  useEffect(() => {
    const ack = localStorage.getItem("hasAcknowledge")
    if (ack === "true"){
      setAcknowledge(true)
      return
    } else {
      localStorage.setItem("hasAcknowledge", "false")
      setAcknowledge(false)
    }
    }, []
  )

  const writeAck = function (){
    localStorage.setItem("hasAcknowledge", "true")
    setAcknowledge(true)
  }

  const writeNoTrack = function (){
    localStorage.setItem("notrack", "true")
  }

  if (acknowledge){
    return(
      <></>
    )
  } else {
    return(
      <div className={"modal modal-open"}>
        <div className="modal-box w-11/12 max-w-5xl">
          <h3 className="font-bold text-lg">DSGVO Hinweis.</h3>
          <ul>
            <li>
              <p className="pt-4">
                Wenn du meine API missbrauchst, werde ich deine IP speichern.
                Das soll natürlich keine Drohung sein, das mache ich nur weil ich in den letzten Monaten
                schlechte Erfahrung gemacht habe und ich so meine API schütze.
                Mit der Nutzung dieser Anwendung stimmst du dem zu.
              </p>
            </li>
            <li>
              <p className="pt-4">
                Außerdem speichere ich deine Auswahl der Veranstaltungen, deinen Studiengang und deine pseudonymisierte (sha-512 hash) E-Mail Adresse.
                Vielleicht mache ich mal ne Auswertung der Daten oder so.
                Wenn du das nicht willst, klick einfach auf "Nein speichere nicht meine Daten"
              </p>
            </li>
          </ul>
          <div className="grid grid-cols-1 grid-rows-2 md:grid-cols-2 md:grid-rows-1 gap-3 mt-4">
            <div className={"w-full "}>
              <label className="btn btn-md w-full" onClick={() => {
                writeNoTrack();
                writeAck()
              }}>Nein, speichere nicht meine Daten</label>
            </div>
            <div className={"w-full m-0"}>
              <label className="btn btn-md w-full" onClick={writeAck}>JA!, du darfst meine Daten netterweise nutzen :)</label>
            </div>
          </div>
        </div>
      </div>
    )
  }
}
