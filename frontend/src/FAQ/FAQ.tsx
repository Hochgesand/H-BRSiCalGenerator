import {useNavigate} from "react-router-dom";

export default function FAQ() {
    let history = useNavigate();
    return (
        <div className={"flex-col gap-3 w-full"}>
            <div className={"md:flex-row grid grid-cols-2 md:grid-cols-1 gap-4 grid-rows-2 p-4 rounded-box bg-base-300 mb-4 md:w-1/4 h-20 md:h-44"}>
                <button
                  className="btn md:btn-lg md:w-full 1/2"
                  onClick={() => history("/")}>
                    Back
                </button>
                <a href={"https://moin.meister.ovh:8443/swagger-ui/index.html"} className={"md:ml-0 md:w-full 1/2"}>
                    <button className="btn md:btn-lg w-full">
                        API Dokumentation
                    </button>
                </a>
            </div>
            <div className={"rounded-box p-4 bg-base-300 flex-grow-0 w-full md:w-3/4"}>
                <p className={"text-3xl"}>Anleitung:</p>
                <p className={"text-2xl"}>Schritt 1:</p>
                <p>Wähle dir einfach alle Veranstaltungen aus die du besuchen möchtest.
                    Du kannst die Tabelle nach belieben sortieren und mit dem Burgerbutton kannst du auch gezielt
                    nach Keywords suchen. Wenn du alle Veranstaltungen ausgewählt hast klicke auf "Hol dir deinen
                    Kalender" ... </p>
                <p className={"text-2xl"}>Schritt 2: </p>
                <p>Jetzt hast du zwei Möglichkeiten:
                    1.: Klicke auf "Download calendar.ics" und importiere es wie in Beispiel 3 einfach selber am
                    Rechner.
                    2.: Tippe deine E-Mail Adresse ein und lasse es dir bequem per E-Mail Schicken. Klicke dafür einfach
                    nachdem
                    du deine E-Mail Adresse eingetragen hast auf "Schick's per E-Mail".
                    Damit kriegste dann die ics auf's Handy und kannst sie ggf. sogar noch einfacher importieren.
                </p>
                <p className={"text-2xl"}>Misc:</p>
                <p>Packages und Technologien die ich benutzt habe:</p>
                <p>Backend:</p>
                <ul>
                    <li> - Springboot</li>
                    <li> - Apache POI um Exceltabellen zu parsen</li>
                    <li> - ical4j um den Calender zu generieren</li>
                </ul>
                <p>Frontend:</p>
                <ul>
                    <li> - React</li>
                    <li> - TailwindCSS für's CSS</li>
                </ul>
                <p className={"text-2xl"}>FAQ:</p>
                <p>Q: Wie kriegst du die Stundenpläne in die Anwendung importiert?</p>
                <p>A: Ich zieh mir einfach die Stundenpläne von Eva und aktualisiere Sie jeden Tag damit alle Änderungen
                    frisch
                    auf meinem Backend liegen.</p>
                <p>Q: Welche Daten erhebst du?</p>
                <p>A: Fast keine. Ich logge nichts und möchte das auch nicht. Die einzigen Dinge die ich speichere sind
                    IP Adressen die die Api missbrauchen.</p>
                <p>Q: Und wann wird meine IP-Adresse gespeichert?</p>
                <p>A: Sobald ihr ein 429er Error bekommt (Too Many Requests) wird eure IP Adresse gespeichert.</p>
            </div>

            <div className={"mt-5"}>
                <p className={"text-3xl"}>Beispiel 1: "Ich studiere Bachelor Informatik und möchte u.a. das Modul Analysis beim Herrn Hülsmann besuchen"</p>
                <img src={process.env.PUBLIC_URL + "/NVIDIA_Share_6Xr3MBSxD0.gif"} alt={""}/>
            </div>
            <div className={"mt-5"}>
                <p className={"text-3xl"}>Beispiel 2: "Is ja alles schön und toll, aber was mache ich jetzt mit dieser
                    komischen
                    calendar.ics Datei? (Beispiel an Google Kalender)"</p>
                <img src={process.env.PUBLIC_URL + "/Z795yqtZO4.gif"} alt={""}/>
            </div>

        </div>
    );
}
