import {useNavigate} from "react-router-dom";

export default function FAQ(){
  let history = useNavigate();
  return(
    <div className={"p-5 flex-col gap-3"}>
      <button
        className="btn btn-lg mb-4"
        onClick={() => history("/")}>
        Back
      </button>
      <div className={"rounded-box p-4 bg-base-300 flex-grow-0 w-full md:w-3/4"}>
        <p className={"text-3xl"}>Anleitung:</p>
        <p className={"text-2xl"}>Schritt 1:</p>
        <p>Wähle dir einfach alle Veranstaltungen aus die du besuchen möchtest.
          Du kannst die Tabelle nach belieben sortieren und mit dem Burgerbutton kannst du auch gezielt
          nach Keywords suchen. Wenn du alle Veranstaltungen ausgewählt hast klicke auf "Hol dir deinen Kalender" ... </p>
        <p className={"text-2xl"}>Schritt 2: </p>
        <p>Jetzt hast du zwei Möglichkeiten:
          1.: Klicke auf "Download calendar.ics" und importiere es wie in Beispiel 3 einfach selber am Rechner.
          2.: Tippe deine E-Mail Adresse ein und lasse es dir bequem per E-Mail Schicken. Klicke dafür einfach nachdem
           du deine E-Mail Adresse eingetragen hast auf "Schick's per E-Mail".
          Damit kriegste dann die ics auf's Handy und kannst sie ggf. sogar noch einfacher importieren.
        </p>
        <p className={"text-2xl"}>Misc:</p>
        <p>Packages und Technologien die ich benutzt habe:</p>
        <p>Spring Boot (Java):</p>
        <ul>
          <li> - Apache POI um Exceltabellen zu parsen</li>
          <li> - ical4j um den Calender zu generieren</li>
        </ul>
        <p>React:</p>
        <ul>
          <li> - AGGrid für die Tabellen</li>
          <li> - TailwindCSS für's CSS</li>
        </ul>
        <p className={"text-2xl"}>FAQ:</p>
        <p>Q: Wie kriegst du die Stundenpläne in die Anwendung importiert?</p>
        <p>A: Ich zieh mir einfach die Stundenpläne von Eva und aktualisiere Sie jeden Tag damit alle Änderungen frisch
          auf meinem Backend liegen.</p>
        <p>Q: Welche Daten erhebst du?</p>
        <p>A: Fast keine. Ich logge nichts und möchte das auch nicht. Die einzigen Dinge die ich speichere sind IP Adressen die die Api missbrauchen.</p>
        <p>Q: Und wann wird meine IP-Adresse gespeichert?</p>
        <p>A: Sobald ihr ein 429er Error bekommt (Too Many Requests) wird eure IP Adresse gespeichert.</p>
      </div>

      <div className={"mt-5"}>
        <p className={"text-3xl"}>Beispiel 1: "Ich bin bei Herr Berrendorf in EidiP und gehöre der Übungsgruppe D an.</p>
        <img src={process.env.PUBLIC_URL + "/FUiTCuS2BJ.gif"} alt={""}/>
      </div>
      <div className={"mt-5"}>
        <p className={"text-3xl"}>Beispiel 2: "Hilfe ich sehe nicht den ganzen Text!</p>
        <img src={process.env.PUBLIC_URL + "/aTkfaQusHi.gif"} alt={""}/>
      </div>
      <div className={"mt-5"}>
        <p className={"text-3xl"}>Beispiel 3: "Is ja alles schön und toll, aber was mache ich jetzt mit dieser komischen
          calendar.ics Datei? (Beispiel an Google Kalender)"</p>
        <img src={process.env.PUBLIC_URL + "/Z795yqtZO4.gif"} alt={""}/>
      </div>

    </div>
  );
}
