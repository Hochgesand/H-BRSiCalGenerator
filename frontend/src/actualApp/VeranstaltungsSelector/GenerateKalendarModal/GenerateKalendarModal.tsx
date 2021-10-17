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
  const gridRef = useRef(null);
  const {getCalendar} = usePostRequestCalendar({
    path: `${baseUrl}/sememesteriCal`,
    veranstaltungsIds: props.veranstaltungsIds
  })
  const {getCalendarEmailResponse} = usePostRequestCalendarEmail({
    path: `${baseUrl}/sememesteriCalEmail`,
    veranstaltungsIds: props.veranstaltungsIds,
    email: email
  })

  const onButtonClickDownloadCalendar = () => {
    const downloadCalendar = () => {
      getCalendar().then(blob => saveAs(blob, 'calendar.ics'))
        .catch(err => {
            setError(err.message)
          }
        );
    }
    downloadCalendar();
  }

  const onEmailWantToSchick = () => {
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

  if (error.length > 0) {
    return (
      <Error msg={error}/>
    );
  }

  return (
    <>
      {props.showKalendarModal ?
        <div className={"container flex mx-auto z-10 absolute inset-0 justify-center rounded-box h-screen"}>
          <div className={"m-auto rounded-box bg-base-300 w-3/4 xl:w-2/3 2xl:1 h-3/4 flex-none"}>
            <div className={"h-30 p-4"}>
              <button className={"btn btn-lg w-full mb-4"} type={"submit"}
                      disabled={props.veranstaltungsIds.length === 0}
                      onClick={onButtonClickDownloadCalendar}>Download calendar.ics
              </button>
              <div className={"rounded-box bg-base-300 grid grid-cols-2 gap-4 mb-4 w-auto"}>
                <input
                  className="appearance-none w-full bg-base-200 text-white border border-white rounded py-4 px-4 leading-tight focus:outline-none focus:bg-base-400 text-2xl"
                  id="grid-first-email" type="text" placeholder="E-Mail" onChange={e => setEmail(e.target.value)}
                />
                <button className={"btn btn-lg w-full"} type={"submit"} disabled={props.veranstaltungsIds.length === 0}
                        onClick={onEmailWantToSchick}>Schick's per
                  E-Mail
                </button>
              </div>
              <button className={"btn btn-lg w-full mb-4"} onClick={() => props.setShowKalendarModal(false)}>Abbrechen
              </button>
            </div>
            <p className={"text-3xl ml-4"}>Ausgewählte Module/Veranstaltungen</p>
            <div className={"ag-theme-alpine-dark rounded-box p-4 bg-base-300"} style={{height: 700}}>
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
