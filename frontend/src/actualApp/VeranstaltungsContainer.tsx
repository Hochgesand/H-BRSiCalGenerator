import {useEffect, useRef, useState} from "react";
import {baseUrl} from "./endpoints";
import useGetRequest from "../api/useGetRequest";
import Veranstaltung from "./Veranstaltung";
import Loading from "../Loading";
import Error from "../Error";
import {AgGridColumn, AgGridReact} from "ag-grid-react";
import usePostRequestCalendar from "../api/usePostRequestCalendar";
import {saveAs} from 'file-saver'
import {useHistory} from "react-router-dom";
import usePostRequestCalendarEmail from "../api/usePostRequestCalendarEmail";

import 'ag-grid-community/dist/styles/ag-grid.css';
import 'ag-grid-community/dist/styles/ag-theme-alpine-dark.css';
import '../index.scss'

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
          alert("Deine Email wurde rausgeschickt^^ Wenn du keine E-Mail bekommen hast, versuche es erneut, " +
            "vergiss aber nicht das ich dich nach zu vielen Versuchen für eine gewisse Zeit blockieren werde!")
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
      <div className={"grid grid-rows-3 grid-rows-none gap-4 2xl:w-7/12 mb-4 xl:w-9/12"}>
        <div className={"rounded-box p-4 bg-base-300 "}>
          <h2 className={"text-4xl mb-2"}>H-BRS iCal Kalendergenerator</h2>
          <p>Die Seite befindet sich noch in der Beta Phase, Fehler bitte melden! (a(at)andrevr.de)</p>
          <p>Ich übernehme keine Haftung für die Richtigkeit der generierten Daten, alles nach bestem Wissen und
            Gewissen.</p>
        </div>
        <div className={"grid grid-cols-2 gap-4 rounded-box p-3 bg-base-300"}>
          <button onClick={onButtonClickDownloadCalendar} className={"btn btn-lg w-full"}>Hol dir deinen Kalender!</button>
          <a href={"https://github.com/Hochgesand/H-BRSiCalGenerator"} target="_blank" rel="noopener noreferrer">
            <button className={"btn btn-lg w-full"}>Gib mir einen Stern auf Github ❤</button>
          </a>
        </div>
        <div className={"grid grid-cols-3 gap-4 rounded-box p-3 bg-base-300 flex-row"}>
          <input
            className="appearance-none  w-full bg-base-200 text-white border border-white rounded py-4 px-4 leading-tight focus:outline-none focus:bg-base-400 text-2xl"
            id="grid-first-email" type="text" placeholder="E-Mail" onChange={e => setEmail(e.target.value)}
          />
          <button className={"btn btn-lg w-full"} type={"submit"} onClick={onEmailWantToSchick}>Schicks per E-Mail</button>
          <button className={"btn btn-lg w-full"} onClick={handleClick}>HILFE!</button>
        </div>
      </div>

      <div className={"ag-theme-alpine-dark rounded-box p-3 bg-base-300"} style={{height: 800}}>
        <AgGridReact
          rowData={veranstaltungsData}
          enableRangeSelection={true}
          rowSelection={"multiple"}
          ref={gridRef}
          rowMultiSelectWithClick={true}
          // @ts-ignore
          onFirstDataRendered={() => gridRef.current.api.sizeColumnsToFit()}
        >
          <AgGridColumn field="name" width={380} sortable={true} filter={true} checkboxSelection={true} resizable={true}
                        headerName={"Veranstaltung"} floatingFilter={true}/>
          <AgGridColumn field="prof" width={150} sortable={true} filter={true} resizable={true}
                        floatingFilter={true}/>
          <AgGridColumn field="studienGangSemester" width={400} sortable={true} filter={true} resizable={true}
                        floatingFilter={true} headerName={"Fachbereich / Semester"}/>
        </AgGridReact>
      </div>

    </div>
  );

}
