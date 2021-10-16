import {useHistory} from "react-router-dom";

export default function FAQ(){
  let history = useHistory();
  return(
    <div className={"p-5 flex-col gap-3"}>
      <button
        className="btn btn-lg"
        onClick={() => history.goBack()}>
        Back
      </button>
      <div className={"mt-5"}>
        <p className={"text-3xl"}>Beispiel 1: "Ich bin bei Herr Berrendorf in EidiP und gehöre der Gruppe D an.</p>
        <img src={process.env.PUBLIC_URL + "/FUiTCuS2BJ.gif"} alt={""}/>
      </div>
      <div className={"mt-5"}>
        <p className={"text-3xl"}>Beispiel 2: "Hilfe ich sehe nicht den ganzen Text!</p>
        <img src={process.env.PUBLIC_URL + "/aTkfaQusHi.gif"} alt={""}/>
      </div>
      <div className={"mt-5"}>
        <p className={"text-3xl"}>Beispiel 3: "Is ja alles schön und toll, aber was mache ich jetzt mit dieser komischen calendar.ics Datei (Beispiel an Google Kalender)"</p>
        <img src={process.env.PUBLIC_URL + "/Z795yqtZO4.gif"} alt={""}/>
      </div>
    </div>
  );
}
