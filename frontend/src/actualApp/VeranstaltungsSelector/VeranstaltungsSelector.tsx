import {useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useRef, useState} from "react";
import Meeting from "../../Objects/Meeting";
import Loading from "../../Loading";
import Error from "../../Error";
import debounce from 'lodash.debounce';

import '../../index.scss'
import GenerateKalendarModal from "./GenerateKalendarModal/GenerateKalendarModal";
import VeranstaltungsListe from "./GenerateKalendarModal/VeranstaltungsListe";
import useAPI from "../../shared/UseAPI";

export default function VeranstaltungsSelector() {
  const params = useParams();
  const navigate = useNavigate();

  const meetingData = useAPI<Meeting[]>("getMeetings", params.studiengang)
  const [searchedVeranstaltungsData, setSearchedVeranstaltungsData] = useState([] as Meeting[]);
  const [selectedMeetings, setSelectedMeetings] = useState([] as Meeting[])
  const [professors, setProfessors] = useState([] as string[])
  const [semesters, setSemesters] = useState([] as number[])
  const [showKalendarModal, setShowKalendarModal] = useState(false);
  const searchfield = useRef(null)

  useEffect(() => {
    const profs = [] as string[]
    meetingData.data.forEach(veranstaltung => {
      let foundProf = profs.find(n => n === veranstaltung.professor)
      if (foundProf === undefined) {
        profs.push(veranstaltung.professor)
      }
    })
    setProfessors(profs)

    const semesters = [] as number[]
    meetingData.data.forEach(veranstaltung => {
      let foundProf = semesters.find(n => n === veranstaltung.semester)
      if (foundProf === undefined) {
        semesters.push(veranstaltung.semester)
      }
    })
    setSemesters(semesters)
  }, [meetingData.data])

  useEffect(() => {
    let newSearchedMeetings = [] as Meeting[]
    searchedVeranstaltungsData.forEach(m => {
      const isSelected = selectedMeetings.find(x => x == m)
      if (isSelected === undefined) {
        newSearchedMeetings.push(m)
      }
    })

    setSearchedVeranstaltungsData(newSearchedMeetings)
  }, [selectedMeetings])

  useEffect(() => {
    setSearchedVeranstaltungsData(meetingData.data)
  }, [meetingData.data])

  const searchModuleByString = (e: string) => {
    const callback = debounce(() => {
      setSearchedVeranstaltungsData([])

      if (e.length === 0) {
        setSearchedVeranstaltungsData(meetingData.data)
        return
      }

      const searchVeranstaltung: Meeting[] = []
      // eslint-disable-next-line array-callback-return
      meetingData.data.find(x => {
        let name: string = (x.name).toLowerCase()
        if (name.includes(e.toLowerCase()))
          searchVeranstaltung.push(x)
      })
      setSearchedVeranstaltungsData([])
      setSearchedVeranstaltungsData(searchVeranstaltung)
    }, 1000)
    callback()
  }

  const searchByProfessor = (prof: string) => {
    const searchVeranstaltung: Meeting[] = []
    // eslint-disable-next-line array-callback-return
    meetingData.data.find(x => {
      let name: string = (x.professor).toLowerCase()
      if (name.includes(prof.toLowerCase()))
        searchVeranstaltung.push(x)
    })
    setSearchedVeranstaltungsData(searchVeranstaltung)
  }

  const searchBySemester = (semester: number) => {
    const searchVeranstaltung: Meeting[] = []
    // eslint-disable-next-line array-callback-return
    meetingData.data.forEach(x => {
      let name: number = (x.semester)
      if (name === semester)
        searchVeranstaltung.push(x)
    })
    setSearchedVeranstaltungsData(searchVeranstaltung)
  }

  const resetFilter = () => {
    setSearchedVeranstaltungsData(meetingData.data)
  }

  function showHelp() {
    navigate("/H-BRSiCalGenerator/FAQ");
  }

  const showCalendarGenerationModal = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    setShowKalendarModal(true);
  }

  if (meetingData.isLoading) {
    return <Loading/>
  }

  if (meetingData.error.length > 0) {
    return (
      <Error msg={meetingData.error}/>
    );
  }

  return (
    <>
      <GenerateKalendarModal showKalendarModal={showKalendarModal} setShowKalendarModal={setShowKalendarModal}
                             selectedData={selectedMeetings} meetings={selectedMeetings}/>

      <div className={showKalendarModal ? "filter blur-lg" : ""}>
        <div
          className={"grid grid-rows-3 grid-rows-none gap-4 2xl:w-10/12 mb-4 xl:w-11/12 m-auto max-h-screen"}>
          <div className={"rounded-box md:p-4 p-2 bg-base-300"}>
            <h2 className={"md:text-4xl text-2xl mb-2 text-center font-bold"}>H-BRS Kalendergenerator</h2>
          </div>
          <div
            className={"grid md:grid-cols-2 md:grid-rows-1 grid-cols-1 grid-rows-2 gap-4 rounded-box p-3 bg-base-300 md:w-3/4 w-full m-auto"}>
            <div className={"w-full grid-cols-2 grid gap-4"}>
              <button className={"btn md:btn-lg w-fully"} onClick={() => navigate("/H-BRSiCalGenerator")}>Startseite
              </button>
              <button className={"btn md:btn-lg w-fully"} onClick={showHelp}>FAQ / HILFE!</button>
            </div>
            <button onClick={e => showCalendarGenerationModal(e)}
                    className={"btn md:btn-lg w-full"}>Kalender generieren!
            </button>
          </div>
          <div className={"rounded-box p-3 bg-base-300 md:w-3/4 w-full m-auto"}>
            <input disabled={false} ref={searchfield} placeholder={"Modulsuche"}
                   onChange={e => searchModuleByString(e.target.value)}
                   className={"appearance-none w-full bg-base-200 border border-white rounded py-4 px-4 leading-tight focus:outline-none focus:bg-base-400"}/>
            <div className={"grid md:grid-cols-3 md:grid-rows-1 grid-cols-1 grid-rows-2 gap-4 rounded-box p-3 bg-base-300 md:w-3/4 w-full m-auto"}>
              <div className="dropdown">
                <label tabIndex={0} className="btn btn-info mr-3 w-full">Professoren</label>
                <ul tabIndex={0} className="dropdown-content menu py-2 shadow bg-base-100 rounded-box w-52">
                  {professors.map((value, index, array) => (
                    <button className={"btn btn-sm my-1"} onClick={() => searchByProfessor(value)}>{value}</button>
                  ))}
                </ul>
              </div>
              <div className="dropdown">
                <label tabIndex={0} className="btn btn-info mr-3 w-full">Semester</label>
                <ul tabIndex={0} className="dropdown-content menu py-2 shadow bg-base-100 rounded-box w-52">
                  {semesters.map((value, index, array) => (
                    <button className={"btn btn-sm my-1"} onClick={() => searchBySemester(value)}>{value}</button>
                  ))}
                </ul>
              </div>
              <div>
                <button className={"btn btn-info mr-0 w-full"} onClick={resetFilter}>ResetFilter</button>
              </div>
            </div>
            <VeranstaltungsListe veranstaltungen={selectedMeetings} selectedVeranstaltungen={selectedMeetings}
                                 setSelectedVeranstaltungen={setSelectedMeetings} defaultSelected={true}/>
            <VeranstaltungsListe veranstaltungen={searchedVeranstaltungsData} selectedVeranstaltungen={selectedMeetings}
                                 setSelectedVeranstaltungen={setSelectedMeetings} defaultSelected={false}/>
          </div>
        </div>
      </div>
    </>
  );

}
