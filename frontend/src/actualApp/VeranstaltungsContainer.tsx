import {useEffect, useRef, useState} from "react";
import {baseUrl} from "./endpoints";
import useGetRequest from "../api/useGetRequest";
import Veranstaltung from "./Veranstaltung";
import Loading from "../Loading";
import Error from "../Error";
import {AgGridColumn, AgGridReact} from "ag-grid-react";
import usePostRequestCalendar from "../api/usePostRequestCalendar";
import {saveAs} from 'file-saver'

import 'ag-grid-community/dist/styles/ag-grid.css';
import 'ag-grid-community/dist/styles/ag-theme-alpine-dark.css';
import '../index.scss'
import {useHistory} from "react-router-dom";
import usePostRequestCalendarEmail from "../api/usePostRequestCalendarEmail";

export default function VeranstaltungsContainer() {
  const history = useHistory();
  const [veranstaltungsData, setVeranstaltungsData] = useState([] as Veranstaltung[]);
  const [email, setEmail] = useState("")
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("")
  let veranstaltungsIds = [] as number[]
  const veranstaltungsPath = `${baseUrl}/getVeranstaltungen`;
  const {getData} = useGetRequest({path: veranstaltungsPath})
  const {getCalendar} = usePostRequestCalendar({
    path: `${baseUrl}/sememesteriCal`,
    veranstaltungsIds: veranstaltungsIds
  })
  const {getCalendarEmailResponse} = usePostRequestCalendarEmail({
    path: `${baseUrl}/sememesteriCalEmail`,
    veranstaltungsIds: veranstaltungsIds,
    email: email
  })
  const gridRef = useRef(null)

  useEffect(() => {
    async function fetchData() {
      await getData().then(function (json) {
        setVeranstaltungsData(json);
        setLoading(false)
      }).catch(err => {
          setError(err.message)
          setLoading(false);
        }
      );
    }

    fetchData();
  }, []); // eslint-disable-line react-hooks/exhaustive-deps

  function handleClick() {
    history.push("/FAQ");
  }

  const onButtonClickDownloadCalendar = () => {
    // @ts-ignore
    const selectedNodes = gridRef.current.api.getSelectedNodes()
    // @ts-ignore
    const selectedData = selectedNodes.map(node => node.data)
    // @ts-ignore
    selectedData.map(node => veranstaltungsIds.push(node.id))
    const downloadCalendar = () => {
      getCalendar().then(blob => saveAs(blob, 'calendar.ics'))
        .catch(err => {
            setError(err.message)
            setLoading(false);
          }
        );
    }
    downloadCalendar();
  }

  const onEmailWantToSchick = () => {
    // @ts-ignore
    const selectedNodes = gridRef.current.api.getSelectedNodes()
    // @ts-ignore
    const selectedData = selectedNodes.map(node => node.data)
    // @ts-ignore
    selectedData.map(node => veranstaltungsIds.push(node.id))
    const sentCalendarEmail = () => {
      getCalendarEmailResponse()
        .then(response => {
        })
        .catch(err => {
            setError(err.message)
          }
        );
    }
    sentCalendarEmail();
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
    <div className={""}>
      <div className={"grid grid-cols-3 mb-4 max-h-96 gap-4"}>
        <div className={"col-span-2 2xl:col-span-1 rounded-box p-4 bg-base-300 flex-grow-0"}>
          <h2 className={"text-4xl mb-2"}>H-BRS Kalendergenerator</h2>
          <p>Anleitung: Wähle dir einfach alle Veranstaltungen aus die du besuchen möchtest.
            Du kannst die Tabelle nach belieben sortieren und mit dem Burgerbutton kannst du auch gezielt
            nach Keywords suchen. Wenn du alle Veranstaltungen ausgewählt hast klick einfach
            auf den Button "Hol dir deinen Kalender!" Du kriegst deinen Kalender als .ics Datei ausgegeben,
            diese Datei kannst du einfach in der Kalendersoftware deiner Wahl importieren.
            Wenn du nicht weißt wie das geht, google es für deine Software.</p>
          <p>Ich übernehme keine Haftung für die Richtigkeit der generierten Daten, alles nach bestem Wissen und
            Gewissen.</p>
          <p>Die Seite befindet sich noch in der Beta Phase, Fehler bitte melden! (a(at)andrevr.de)</p>
        </div>

        <div className={"rounded-box p-4 bg-base-300 flex-grow-0"}>
          <h3>Packages und Technologien die ich benutzt habe:</h3>
          <p>Spring Boot (Java):</p>
          <ul>
            <li>Apache POI um Exceltabellen zu parsen</li>
            <li>ical4j um den Calender zu generieren</li>
          </ul>
          <p>React:</p>
          <ul>
            <li>AGGrid für die Tabelle, kein Bock gehabt selber Sortieralgorithmen zu implementieren</li>
            <li>Locker noch ca. 2000 Packages mehr die mit React gekommen sind, npm macht npm sachen</li>
          </ul>
        </div>
      </div>

      <div className={"ag-theme-alpine-dark rounded-box p-3 bg-base-300"} ag-grid="gridOptions" style={{height: 800}}>
        <AgGridReact
          rowData={veranstaltungsData}
          enableRangeSelection={true}
          rowSelection={"multiple"}
          ref={gridRef}
          floatingFilter={true}
          rowMultiSelectWithClick={true}
          // @ts-ignore
          onFirstDataRendered={() => gridRef.current.api.sizeColumnsToFit()}
        >
          <AgGridColumn field="name" width={380} sortable={true} filter={true} checkboxSelection={true} resizable={true}
                        headerName={"Veranstaltung"}/>
          <AgGridColumn field="prof" width={150} sortable={true} filter={true} resizable={true}/>
          <AgGridColumn field="studienGangSemester" width={400} sortable={true} filter={true} resizable={true}
                        headerName={"Fachbereich / Semester"}/>
        </AgGridReact>
      </div>
      <div className={"grid grid-rows-1 grid-cols-5 mt-4 gap-4 xl:w-9/12 "}>
        <button onClick={onButtonClickDownloadCalendar} className={"btn btn-lg"}>Hol dir deinen Kalender!</button>
        <a href={"https://github.com/Hochgesand/H-BRSiCalGenerator"} target="_blank" rel="noopener noreferrer">
          <button className={"btn btn-lg"}>Gib mir einen Stern auf Github ❤</button>
        </a>
        <input
          className="appearance-none w-auto bg-base-200 text-white border border-red-500 rounded py-4 px-4 leading-tight focus:outline-none focus:bg-base-400 text-2xl"
          id="grid-first-email" type="text" placeholder="E-Mail" onChange={e => setEmail(e.target.value)}
        />
        <button className={"btn btn-lg"} type={"submit"} onClick={() => onEmailWantToSchick()}>Schicks per E-Mail</button>
        <button className={"btn btn-lg"} onClick={() => handleClick()}>HILFE!</button>
        <button className={"btn btn-lg"} >DIE E-MAIL WIRD RAUSGEHEN, ICH HAB NUR NOCH KEIN FEEDBACK IMPLEMENTIERT!!!</button>
      </div>
    </div>
  );

}
