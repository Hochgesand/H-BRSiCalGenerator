import {useHistory} from "react-router-dom";
import React, {useEffect, useState} from "react";
import Veranstaltung from "../../Objects/Veranstaltung";
import {baseUrl} from "../../Objects/endpoints";
import useGetRequest from "../../api/useGetRequest";
import Loading from "../../Loading";
import Error from "../../Error";

import '../../index.scss'
import GenerateKalendarModal from "./GenerateKalendarModal/GenerateKalendarModal";

interface veranstaltungsProp {
    readonly veranstaltung: string
}

export default function VeranstaltungsSelector({veranstaltung}: veranstaltungsProp) {
    const history = useHistory();
    const [veranstaltungsData, setVeranstaltungsData] = useState([] as Veranstaltung[]);
    const [selectedDataProp, setSelectedDataProp] = useState([] as Veranstaltung[])
    const [veranstaltungsIds, setVeranstaltungsIds] = useState([] as number[])
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [showKalendarModal, setShowKalendarModal] = useState(false);
    const veranstaltungsPath = `${baseUrl}/getVeranstaltungByStudiengang?studiengang=${veranstaltung}`;
    const {getData} = useGetRequest({path: veranstaltungsPath})

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
        let selectedIdsOfVeranstaltungen: number[] = []
        selectedDataProp.forEach(x => selectedIdsOfVeranstaltungen.push(x.id))
        setVeranstaltungsIds(selectedIdsOfVeranstaltungen)
        e.stopPropagation()
        setError("")
        setShowKalendarModal(true);
    }

    const addItemToSelectedItems = (i: number) => {
        let newSelectedItems = selectedDataProp

        let selectedVeranstaltung: Veranstaltung = veranstaltungsData.find(x => {
            if (x.id === i)
                return x
            return null
        })!

        if (selectedVeranstaltung === null)
            return

        newSelectedItems.push(selectedVeranstaltung)
        setSelectedDataProp(newSelectedItems)
    }

    const removeItemToSelectedItems = (i: number) => {
        let newSelectedItems: Veranstaltung[] = []
        selectedDataProp.forEach(x => {
            if (x.id !== i)
                newSelectedItems.push(x)
        })
        setSelectedDataProp(newSelectedItems)
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
                <div
                    className={"grid grid-rows-3 grid-rows-none gap-4 2xl:w-10/12 mb-4 xl:w-11/12 m-auto max-h-screen"}>
                    <div className={"rounded-box md:p-4 p-2 bg-base-300"}>
                        <h2 className={"md:text-4xl text-2xl mb-2 text-center"}>H-BRS Kalendergenerator v1.1</h2>
                    </div>
                    <div
                        className={"grid md:grid-cols-2 md:grid-rows-1 grid-cols-1 grid-rows-2 gap-4 rounded-box p-3 bg-base-300 md:w-3/4 w-full m-auto"}>
                        <button className={"btn md:btn-lg w-fully"} onClick={showHelp}>FAQ / HILFE!</button>
                        <button onClick={e => showCalendarGenerationModal(e)}
                                className={"btn md:btn-lg w-full"}>Kalender generieren!
                        </button>
                    </div>

                    <div className={"rounded-box p-3 bg-base-300 md:w-3/4 w-full m-auto"}>
                        <fieldset className="border-t border-b border-gray-500">
                            <div className="divide-y divide-gray-500">
                                {veranstaltungsData.map((veranstaltung) => (
                                    <div className="relative flex items-start py-2 md:py-4">
                                        <div className="mr-3 m-auto flex items-center h-5">
                                            <input
                                                id="comments"
                                                aria-describedby="comments-description"
                                                name="comments"
                                                type="checkbox"
                                                onChange={event => {
                                                    if (event.target.checked) {
                                                        addItemToSelectedItems(veranstaltung.id)
                                                    } else {
                                                        removeItemToSelectedItems(veranstaltung.id)
                                                    }
                                                }}
                                                className="focus:ring-base-500 h-10 w-10 text-indigo-600 border-gray-300 rounded"
                                            />
                                        </div>
                                        <div className="min-w-0 flex-1 text-sm">
                                            <label htmlFor="comments" className="font-medium text-gray-700">
                                                {veranstaltung.name}
                                            </label>
                                            <p id="comments-description" className="text-gray-500">
                                                {veranstaltung.prof}
                                            </p>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
        </>
    );

}
