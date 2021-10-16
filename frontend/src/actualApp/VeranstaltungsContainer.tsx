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
import 'ag-grid-community/dist/styles/ag-theme-material.css';

export default function VeranstaltungsContainer() {

  const [veranstaltungsData, setVeranstaltungsData] = useState([] as Veranstaltung[]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("")
  let veranstaltungsIds = [] as number[]
  const path = `${baseUrl}/getVeranstaltungen`;
  const {getData} = useGetRequest({path: path})
  const {getCalendar} = usePostRequestCalendar({
    path: `${baseUrl}/sememesteriCal`,
    veranstaltungsIds: veranstaltungsIds
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

  const onButtonClick = () => {
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

  if (loading) {
    return <Loading/>
  }

  if (error.length > 0) {
    return (
      <Error msg={error}/>
    );
  }

  return (
    <div>
      <div className="ag-theme-material" style={{height: 800, width: 1300}}>
        <h2>H-BRS Kalendergenerator</h2>
        <p>Anleitung: Wähle dir einfach alle Veranstaltungen aus die du besuchen möchtest.
          Du kannst die Tabelle nach belieben sortieren und mit dem Burgerbutton kannst du auch gezielt
          nach Keywords suchen. Wenn du alle Veranstaltungen ausgewählt hast klick einfach
          auf den Button "Hol dir deinen Kalender!" Du kriegst deinen Kalender als .ics Datei ausgegeben,
          diese Datei kannst du einfach in der Kalendersoftware deiner Wahl importieren.
          Wenn du nicht weißt wie das geht, google es für deine Software.</p>
        <h3><a href={"https://github.com/Hochgesand/H-BRSiCalGenerator"}>Gebt mir einen Stern auf Github ❤</a></h3>
        <p>Ich übernehme keine Haftung für die Richtigkeit der generierten Daten, alles nach bestem Wissen und Gewissen.</p>
        <p>Die Seite befindet sich noch in der Beta Phase, Fehler bitte melden! (a(at)andrevr.de)</p>
        <button onClick={onButtonClick}>Hol dir deinen Kalender!</button>
        <AgGridReact
          rowData={veranstaltungsData}
          enableRangeSelection={true}
          rowSelection={"multiple"}
          ref={gridRef}
          rowMultiSelectWithClick={true}
          // @ts-ignore
          onFirstDataRendered={() => gridRef.current.api.sizeColumnsToFit()}
        >
          <AgGridColumn field="name" width={400} sortable={true} filter={true} checkboxSelection={true} resizable={true} headerName={"Veranstaltung"}/>
          <AgGridColumn field="prof" width={150} sortable={true} filter={true} resizable={true}/>
          <AgGridColumn field="studienGangSemester" width={400} sortable={true} filter={true} resizable={true} headerName={"Fachbereich / Semester"} />
        </AgGridReact>
      </div>
      <br/>
      <br/>
      <br/>
      <br/>
      <br/>
      <br/>
      <br/>
      <br/>
      <br/>
      <br/>
      <br/>
      <br/>
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

  );

}
