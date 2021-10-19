import React, {useRef, useState} from "react";
import usePostRequestCalendar from "../../../api/usePostRequestCalendar";
import {baseUrl} from "../../../Objects/endpoints";
import usePostRequestCalendarEmail from "../../../api/usePostRequestCalendarEmail";
import {saveAs} from "file-saver";
import Error from "../../../Error";
import {AgGridColumn, AgGridReact} from "ag-grid-react";

import 'ag-grid-community/dist/styles/ag-grid.css';
import 'ag-grid-community/dist/styles/ag-theme-alpine-dark.css';
import '../../../index.scss'

export interface kalendarModalInterface {
  showKalendarModal: boolean;
  setShowKalendarModal: React.Dispatch<React.SetStateAction<boolean>>
  veranstaltungsIds: number[]
  selectedData: any[]
}

export default function GenerateKalendarModal(props: kalendarModalInterface) {
  const [email, setEmail] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false)
  const gridRef = useRef(null);
  const {getCalendar} = usePostRequestCalendar({
    path: `${baseUrl}/sememesteriCal`,
    veranstaltungsIds: props.veranstaltungsIds
  })
  const {getCalendarEmailResponse} = usePostRequestCalendarEmail({
    path: `${baseUrl}/sememesteriCalEmail`,
    body: {
      veranstaltungsIds: props.veranstaltungsIds,
      email: email
    }
  })

  const onButtonClickDownloadCalendar = () => {
    setLoading(true)

    const downloadCalendar = () => {
      getCalendar().then(blob => {
        setLoading(false)
        saveAs(blob, 'calendar.ics')
        props.setShowKalendarModal(false)
        setError("")
      })
      .catch(err => {
          setError(err.message)
          console.log(error)
          setLoading(false)
        }
      );
    }
    downloadCalendar();
  }

  const onEmailWantToSchick = () => {
    setLoading(true)
    const sentCalendarEmail = () => {
      getCalendarEmailResponse()
        .then(response => {
          setLoading(false)
          alert("Deine Email wurde rausgeschickt^^ Wenn du keine E-Mail bekommen hast, versuche es erneut. " +
            "Vergiss aber nicht das ich dich nach zu vielen Versuchen für eine gewisse Zeit blockieren werde!")
          setError("")
          props.setShowKalendarModal(false)
        })
        .catch(err => {
            setError(err.message)
            console.log(error)
            setLoading(false)
          }
        );
    }
    sentCalendarEmail();
  }

  return (
    <>
      {props.showKalendarModal ?
        <div className={"container flex mx-auto z-10 absolute inset-0 justify-center rounded-box h-screen"}>
          <div className={"m-auto rounded-box bg-base-300 w-3/4 xl:w-2/3 2xl:1 h-screen flex-none z-20"} >
            <div className={"h-30 p-4"}>
              {error.length > 0 ? <Error msg={error} /> : null}
              <button className={`btn btn-lg w-full mb-4 ${loading ? 'loading' : null}`} type={"submit"}
                      disabled={props.veranstaltungsIds.length === 0 || loading}
                      onClick={onButtonClickDownloadCalendar}>Download calendar.ics
              </button>
              <div className={"rounded-box bg-base-200 h-6 w-full mb-4"}><p className={"w-full text-center"}>ODER</p></div>
              <div className={"rounded-box bg-base-300 grid grid-cols-2 gap-4 mb-4 w-auto"}>
                <input
                  className="appearance-none w-full bg-base-200 border border-white rounded py-4 px-4 leading-tight focus:outline-none focus:bg-base-400 text-2xl"
                  id="grid-first-email" type="email" placeholder="E-Mail" onChange={e => setEmail(e.target.value)}
                />
                <button className={`btn btn-lg w-full ${loading ? 'loading' : null}`} type={"submit"}
                        disabled={props.veranstaltungsIds.length === 0 || loading || email.length === 0}
                        onClick={onEmailWantToSchick}>Schick's per E-Mail
                </button>
              </div>
              <button className={"btn btn-md w-full mb-4"} onClick={() => {
                props.setShowKalendarModal(false)
                setError("")
              }} disabled={loading}>Abbrechen
              </button>
            </div>
            <p className={"text-3xl ml-4"}>Ausgewählte Module/Veranstaltungen</p>
            <div className={"ag-theme-alpine-dark rounded-box p-4 bg-base-300 h-screen"} >
              <AgGridReact
                ref={gridRef}
                rowData={props.selectedData}
                // @ts-ignore
                onFirstDataRendered={() => gridRef.current.api.sizeColumnsToFit()}
              >
                <AgGridColumn field="name" width={380}
                              headerName={"Veranstaltung"}/>
                <AgGridColumn field="studienGangSemester" width={400} headerName={"Fachbereich / Semester"}/>
              </AgGridReact>
            </div>
          </div>
        </div>
        : null}
    </>
  );
}
