import React, {useState} from "react";
import usePostRequestCalendar from "../../../api/usePostRequestCalendar";
import {baseUrl} from "../../../Objects/endpoints";
import usePostRequestCalendarEmail from "../../../api/usePostRequestCalendarEmail";
import {saveAs} from "file-saver";
import Error from "../../../Error";

import '../../../index.scss'
import Veranstaltung from "../../../Objects/Veranstaltung";

export interface kalendarModalInterface {
    showKalendarModal: boolean;
    setShowKalendarModal: React.Dispatch<React.SetStateAction<boolean>>
    veranstaltungsIds: number[]
    selectedData: Veranstaltung[]
}

export default function GenerateKalendarModal(props: kalendarModalInterface) {
    const [email, setEmail] = useState("");
    const [error, setError] = useState("");
    let downloadUrl: string = "";
    const [loading, setLoading] = useState(false)
    const postGetiCal = usePostRequestCalendar({
        path: (`${baseUrl}/calender/sememesteriCal`),
        veranstaltungsIds: props.veranstaltungsIds
    })
    const postGetCSV = usePostRequestCalendar({
        path: (`${baseUrl}/calender/sememesteriCalAsCSV`),
        veranstaltungsIds: props.veranstaltungsIds
    })
    const {getCalendarEmailResponse} = usePostRequestCalendarEmail({
        path: `${baseUrl}/calender/sememesteriCalEmail`,
        body: {
            veranstaltungsIds: props.veranstaltungsIds,
            email: email
        }
    })

    const onButtonClickDownloadCalendar = () => {
        setLoading(true)
        const downloadCalendar = () => {
            postGetiCal.getCalendar().then(blob => {
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

    const onButtonClickDownloadCalendarAsCsv = () => {
        setLoading(true)
        const downloadCalendar = () => {
            postGetCSV.getCalendar().then(blob => {
                setLoading(false)
                saveAs(blob, 'calendar.csv')
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
                .then(async response => {
                    setLoading(false)
                    alert(await response.text())
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
                    <div
                        className={"m-auto rounded-box bg-base-300 md:w-3/4 w-full xl:w-2/3 2xl:1 h-screen flex-none z-20"}>
                        <div className={"p-4"}>
                            {error.length > 0 ? <Error msg={error}/> : null}
                            <div className={"h-0 h-auto"}>
                                <div className={"grid grid-cols-2 grid-rows-1 gap-4"}>
                                    <button className={`btn h-14 md:btn-lg w-full mb-4 ${loading ? 'loading' : null}`}
                                            type={"submit"}
                                            disabled={props.veranstaltungsIds.length === 0 || loading}
                                            onClick={() => {
                                                downloadUrl = (`${baseUrl}/sememesteriCal`)
                                                console.log(downloadUrl)
                                                onButtonClickDownloadCalendar()
                                            }}>Download calendar.ics
                                    </button>
                                    <button className={`btn h-14 md:btn-lg w-full mb-4 ${loading ? 'loading' : null}`}
                                            type={"submit"}
                                            disabled={props.veranstaltungsIds.length === 0 || loading}
                                            onClick={() => {
                                                downloadUrl = `${baseUrl}/sememesteriCalAsCSV`
                                                onButtonClickDownloadCalendarAsCsv()
                                            }}>Download Kalender als CSV
                                    </button>
                                </div>
                                <div className={"rounded-box bg-base-200 h-6 w-full mb-4"}><p
                                  className={"w-full text-center"}>ODER</p></div>
                            </div>
                            <div className={"rounded-box bg-base-300 grid grid-cols-2 gap-4 mb-4 w-auto"}>
                                <input
                                    className="appearance-none w-full bg-base-200 border border-white rounded py-4 px-4 leading-tight focus:outline-none focus:bg-base-400 text-2xl"
                                    id="grid-first-email" type="email" placeholder="E-Mail"
                                    onChange={e => setEmail(e.target.value)}
                                />
                                <button className={`btn btn-lg w-full ${loading ? 'loading' : null}`} type={"submit"}
                                        disabled={props.veranstaltungsIds.length === 0 || loading || email.length === 0}
                                        onClick={onEmailWantToSchick}>Schick's per E-Mail
                                </button>
                            </div>
                            <button className={"btn btn-md w-full md:mb-4"} onClick={() => {
                                props.setShowKalendarModal(false)
                                setError("")
                            }} disabled={loading}>Abbrechen
                            </button>
                        </div>
                        <div className={"rounded-box p-3 bg-base-300 md:w-3/4 w-full m-auto pl-5 pr-5"}>
                            <p className={"text-3xl mb-4"}>Ausgewählte Module/Veranstaltungen</p>
                            <fieldset className="border-t border-b border-gray-500">
                                <div className="divide-y divide-gray-500">
                                    {props.selectedData.map((veranstaltung, id) => (
                                        <div className="relative flex items-start py-2 md:py-4" key={id}>
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
                : null}
        </>
    );
}
