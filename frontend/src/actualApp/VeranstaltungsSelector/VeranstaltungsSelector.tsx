import {useHistory} from "react-router-dom";
import React, {useEffect, useRef, useState} from "react";
import Veranstaltung from "../../Objects/Veranstaltung";
import {baseUrl} from "../../Objects/endpoints";
import useGetRequest from "../../api/useGetRequest";
import Loading from "../../Loading";
import Error from "../../Error";
import {AgGridColumn, AgGridReact} from "ag-grid-react";

import 'ag-grid-community/dist/styles/ag-grid.css';
import 'ag-grid-community/dist/styles/ag-theme-material.css';
import '../../index.scss'
import GenerateKalendarModal from "./GenerateKalendarModal/GenerateKalendarModal";

interface veranstaltungsProp {
  readonly veranstaltung: string
}

export default function VeranstaltungsSelector({veranstaltung}: veranstaltungsProp) {
  const history = useHistory();
  const [veranstaltungsData, setVeranstaltungsData] = useState([] as Veranstaltung[]);
  const [selectedDataProp, setSelectedDataProp] = useState([])
  const [veranstaltungsIds, setVeranstaltungsIds] = useState([] as number[])
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showKalendarModal, setShowKalendarModal] = useState(false);
  const veranstaltungsPath = `${baseUrl}/getVeranstaltungByStudiengang?studiengang=${veranstaltung}`;
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
        <div className={"grid grid-rows-3 grid-rows-none gap-4 2xl:w-10/12 mb-4 xl:w-11/12 m-auto"}>
          <div className={"rounded-box md:p-4 p-2 bg-base-300"}>
            <h2 className={"md:text-4xl text-2xl mb-2 text-center"}>H-BRS Kalendergenerator v1.1</h2>
          </div>
          <div
            className={"grid md:grid-cols-2 md:grid-rows-1 grid-cols-1 grid-rows-2 gap-4 rounded-box p-3 bg-base-300 md:w-3/4 w-full m-auto"}>
            <button className={"btn md:btn-lg w-fully"} onClick={showHelp}>FAQ / HILFE!</button>
            <button onClick={e => showCalendarGenerationModal(e)} className={"btn md:btn-lg w-full"}>Kalender generieren!</button>
          </div>

          <div className={"ag-theme-material rounded-box p-3 bg-base-300 md:w-3/4 w-full m-auto"} style={{height: 800}}>
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
              <AgGridColumn field="prof" width={150} sortable={true} filter={true} resizable={true}
                            floatingFilter={true}/>
            </AgGridReact>
          </div>
        </div>
      </div>
    </>
  );

}
