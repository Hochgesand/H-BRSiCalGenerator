import {useHistory} from "react-router-dom";
import React, {useEffect, useRef, useState} from "react";
import Veranstaltung from "../../Objects/Veranstaltung";
import {baseUrl} from "../../Objects/endpoints";
import useGetRequest from "../../api/useGetRequest";
import Loading from "../../Loading";
import Error from "../../Error";
import {AgGridColumn, AgGridReact} from "ag-grid-react";

import 'ag-grid-community/dist/styles/ag-grid.css';
import 'ag-grid-community/dist/styles/ag-theme-alpine-dark.css';
import '../../index.scss'
import GenerateKalendarModal from "./GenerateKalendarModal/GenerateKalendarModal";

export default function VeranstaltungsSelector() {
  const history = useHistory();
  const [veranstaltungsData, setVeranstaltungsData] = useState([] as Veranstaltung[]);
  const [selectedDataProp, setSelectedDataProp] = useState([])
  const [veranstaltungsIds, setVeranstaltungsIds] = useState([] as number[])
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showKalendarModal, setShowKalendarModal] = useState(false);
  const veranstaltungsPath = `${baseUrl}/getVeranstaltungen`;
  const {getData} = useGetRequest({path: veranstaltungsPath})
  const gridRef = useRef(null)

  useEffect(() => {
    async function fetchData() {
      await getData().then(function (json) {
        setVeranstaltungsData(json);
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

  function showHelp() {
    history.push("/FAQ");
  }

  const showCalendarGenerationModal = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    let veranstaltungsIdsTemp = [] as number[]
    // @ts-ignore
    const selectedNodes = gridRef.current.api.getSelectedNodes()
    // @ts-ignore
    const selectedData = selectedNodes.map(node => node.data)
    // @ts-ignore
    selectedData.map(node => veranstaltungsIdsTemp.push(node.id))
    setSelectedDataProp(selectedData)
    setVeranstaltungsIds(veranstaltungsIdsTemp)
    e.stopPropagation()
    setError("")
    setShowKalendarModal(true);
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
    <>
      <GenerateKalendarModal showKalendarModal={showKalendarModal} setShowKalendarModal={setShowKalendarModal}
                             selectedData={selectedDataProp} veranstaltungsIds={veranstaltungsIds}/>
      <div className={showKalendarModal ? "filter blur-lg" : ""} onClick={() => setShowKalendarModal(false)}>
        <div className={"grid grid-rows-3 grid-rows-none gap-4 2xl:w-10/12 mb-4 xl:w-11/12"}>
          <div className={"rounded-box p-4 bg-base-300 "}>
            <h2 className={"text-4xl mb-2"}>H-BRS Kalendergenerator v1.0</h2>
            <p>Ich übernehme keine Haftung für die Richtigkeit der generierten Daten, alles nach bestem Wissen und
              Gewissen. Fehler bitte an a@andrevr.de melden.</p>
            <p>Wenn's euch gefällt, empfehlt es euren Kommilitonen! 😁</p>
          </div>
          <div className={"grid grid-cols-3 gap-4 rounded-box p-3 bg-base-300"}>
            <button onClick={e => showCalendarGenerationModal(e)} className={"btn btn-lg w-full"}>Hol dir deinen Kalender!</button>
            <a href={"https://github.com/Hochgesand/H-BRSiCalGenerator"} target="_blank" rel="noopener noreferrer">
              <button className={"btn btn-lg w-full"}>Gib mir einen Stern auf Github ❤</button>
            </a>
            <button className={"btn btn-lg w-full"} onClick={showHelp}>FAQ / HILFE!</button>
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
            <AgGridColumn field="name" width={380} sortable={true} filter={true} checkboxSelection={true}
                          resizable={true}
                          headerName={"Veranstaltung"} floatingFilter={true}/>
            <AgGridColumn field="studienGangSemester" width={400} sortable={true} filter={true} resizable={true}
                          floatingFilter={true} headerName={"Fachbereich / Semester"}/>
            <AgGridColumn field="prof" width={150} sortable={true} filter={true} resizable={true}
                          floatingFilter={true}/>
          </AgGridReact>
        </div>
      </div>
    </>
  );

}
