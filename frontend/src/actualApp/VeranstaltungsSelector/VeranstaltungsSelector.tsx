import {useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useRef, useState} from "react";
import Veranstaltung from "../../Objects/Veranstaltung";
import {baseUrl} from "../../Objects/endpoints";
import useGetRequest from "../../api/useGetRequest";
import Loading from "../../Loading";
import Error from "../../Error";
import debounce from 'lodash.debounce';

import '../../index.scss'
import GenerateKalendarModal from "./GenerateKalendarModal/GenerateKalendarModal";

export default function VeranstaltungsSelector() {
    const params = useParams();
    const history = useNavigate();

    const [veranstaltungsData, setVeranstaltungsData] = useState([] as Veranstaltung[]);
    const [searchedVeranstaltungsData, setSearchedVeranstaltungsData] = useState([] as Veranstaltung[]);
    const [selectedDataProp, setSelectedDataProp] = useState([] as Veranstaltung[])
    const [veranstaltungsIds, setVeranstaltungsIds] = useState([] as number[])
    const [showKalendarModal, setShowKalendarModal] = useState(false);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const veranstaltungsPath = `${baseUrl}/getVeranstaltungByStudiengang?studiengang=${params.studiengang}`;
    const searchfield = useRef(null)

    const {getData} = useGetRequest({path: veranstaltungsPath})

    useEffect(() => {
        async function fetchData() {
            await getData().then(function (json) {
                setVeranstaltungsData(json);
                setSearchedVeranstaltungsData(json)
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

    const debouncedSave = (e: string) => {
        const callback = debounce(() => {
            if (e.length === 0) {
                setSearchedVeranstaltungsData(veranstaltungsData)
                return
            }

            const searchVeranstaltung: Veranstaltung[] = []
            // eslint-disable-next-line array-callback-return
            veranstaltungsData.find(x => {
                let name: string = (x.name).toLowerCase()
                if (name.includes(e.toLowerCase()))
                    searchVeranstaltung.push(x)
            })

            setSearchedVeranstaltungsData(searchVeranstaltung)
        }, 1000)
        callback()
    }

    function showHelp() {
        history("/FAQ");
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
                        <input disabled={false} ref={searchfield} placeholder={"Modulsuche"} onChange={e => debouncedSave(e.target.value)}
                             className={"appearance-none w-full bg-base-200 border border-white rounded py-4 px-4 leading-tight focus:outline-none focus:bg-base-400 mb-4"}/>
                        <fieldset className="border-t border-b border-gray-500">
                            <div className="divide-y divide-gray-500">
                                {searchedVeranstaltungsData.map((veranstaltung, id) => (
                                    <div className="relative flex items-start py-2 md:py-4" key={id}>
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
