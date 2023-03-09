import {useNavigate, useParams} from "react-router-dom";
import React, {Dispatch, SetStateAction, useEffect, useState} from "react";
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
  const [filteredMeetings, setFilteredMeetings] = useState([] as Meeting[]);
  const [selectedMeetings, setSelectedMeetings] = useState([] as Meeting[])
  const [showKalendarModal, setShowKalendarModal] = useState(false);
  const [isFiltered, setIsFiltered] = useState(false)

  useEffect(() => {
    cleanNonSelectedMeetings()
    if (selectedMeetings.length + filteredMeetings.length !== meetingData.data.length)
      setIsFiltered(true)
    else
      setIsFiltered(false)
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [selectedMeetings, filteredMeetings])

  const resetFilter = () => {
    const notSelectedMeeting: Meeting[] = []
    meetingData.data.forEach(meeting => {
      if (selectedMeetings.find(x => x.id === meeting.id) === undefined)
        notSelectedMeeting.push(meeting)
    })

    setFilteredMeetings(notSelectedMeeting)
  }

  const cleanNonSelectedMeetings = () => {
    let newSearchedMeetings = [] as Meeting[]
    filteredMeetings.forEach(m => {
      if (selectedMeetings.find(x => x === m) === undefined)
        newSearchedMeetings.push(m)
    })
    if (newSearchedMeetings.length !== filteredMeetings.length)
      setFilteredMeetings(newSearchedMeetings)
  }

  function showHelp() {
    navigate("/H-BRSiCalGenerator/FAQ");
  }

  const showCalendarGenerationModal = () => {
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
            <button onClick={showCalendarGenerationModal}
                    className={"btn md:btn-lg w-full"}>Kalender generieren!
            </button>
          </div>
          <div className={"rounded-box p-3 bg-base-300 md:w-3/4 w-full m-auto"}>
            <StringSearch meetingData={meetingData.data} setFilteredMeetings={setFilteredMeetings}/>
            <div
              className={"grid md:grid-cols-3 md:grid-rows-1 grid-cols-1 grid-rows-3 gap-4 rounded-box p-3 bg-base-300 w-full m-auto"}>
              <ProfessorSearchDropdown meetingData={meetingData.data} setFilteredMeetings={setFilteredMeetings}/>
              <SemesterSearchDropdown meetingData={meetingData.data} setFilteredMeetings={setFilteredMeetings}/>
              <button className={`btn ${isFiltered ? "btn-error" : "btn-disabled"} mr-0 w-full`} onClick={resetFilter}>ResetFilter</button>
            </div>
            <VeranstaltungsListe selectedMeetings={selectedMeetings} setSelectedMeetings={setSelectedMeetings}
                                 filteredMeetings={selectedMeetings} defaultSelected={true}/>
            <VeranstaltungsListe selectedMeetings={selectedMeetings} setSelectedMeetings={setSelectedMeetings}
                                 filteredMeetings={filteredMeetings} defaultSelected={false}/>
          </div>
        </div>
      </div>
    </>
  );
}

interface SearchProps {
  meetingData: Meeting[]
  setFilteredMeetings: Dispatch<SetStateAction<Meeting[]>>
}

function StringSearch(props: SearchProps) {
  const searchModuleByString = (e: string) => {
    const callback = debounce(() => {
      props.setFilteredMeetings([])

      if (e.length < 3) {
        props.setFilteredMeetings(props.meetingData)
        return
      }

      const searchVeranstaltung: Meeting[] = []
      props.meetingData.forEach(x => {
        let name: string = (x.name).toLowerCase()
        if (name.includes(e.toLowerCase()))
          searchVeranstaltung.push(x)
      })

      props.setFilteredMeetings(searchVeranstaltung)
    }, 300)
    callback()
  }

  return (
    <input disabled={false} placeholder={"Modulsuche"}
           onChange={e => searchModuleByString(e.target.value)}
           className={"appearance-none w-full bg-base-200 border border-white rounded py-4 px-4 leading-tight focus:outline-none focus:bg-base-400"}/>
  )
}

function ProfessorSearchDropdown(props: SearchProps) {
  const [professors, setProfessors] = useState([] as string[])

  useEffect(() => {
    props.setFilteredMeetings(props.meetingData)
    const profs = [] as string[]
    props.meetingData.forEach(veranstaltung => {
      let foundProf = profs.find(n => n === veranstaltung.professor)
      if (foundProf === undefined) {
        profs.push(veranstaltung.professor)
      }
    })
    profs.sort()
    setProfessors(profs)
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [props.meetingData])

  const searchByProfessor = (prof: string) => {
    const searchVeranstaltung: Meeting[] = []
    // eslint-disable-next-line array-callback-return
    props.meetingData.find(x => {
      let name: string = (x.professor).toLowerCase()
      if (name.includes(prof.toLowerCase()))
        searchVeranstaltung.push(x)
    })
    props.setFilteredMeetings(searchVeranstaltung)
  }

  return (
    <div className="dropdown">
      <label tabIndex={0} className="btn btn-info mr-3 w-full">Professoren</label>
      <ul tabIndex={0} className="dropdown-content menu py-2 shadow bg-base-100 rounded-box w-52">
        {professors.map((value) => (
          <button className={"btn btn-sm my-1 m-2"} onClick={() => searchByProfessor(value)}>{value}</button>
        ))}
      </ul>
    </div>
  )
}

function SemesterSearchDropdown(props: SearchProps) {
  const [semesters, setSemesters] = useState([] as number[])

  useEffect(() => {
    const semesters = [] as number[]
    props.meetingData.forEach(veranstaltung => {
      let foundProf = semesters.find(n => n === veranstaltung.semester)
      if (foundProf === undefined) {
        semesters.push(veranstaltung.semester)
      }
    })
    semesters.sort()
    setSemesters(semesters)
  }, [props.meetingData])

  const searchBySemester = (semester: number) => {
    const searchVeranstaltung: Meeting[] = []
    // eslint-disable-next-line array-callback-return
    props.meetingData.forEach(x => {
      let name: number = (x.semester)
      if (name === semester)
        searchVeranstaltung.push(x)
    })
    props.setFilteredMeetings(searchVeranstaltung)
  }

  return (
    <div className="dropdown">
      <label tabIndex={0} className="btn btn-info mr-3 w-full">Semester</label>
      <ul tabIndex={0} className="dropdown-content menu py-2 shadow bg-base-100 rounded-box w-52">
        {semesters.map((value) => (
          <button className={"btn btn-sm my-1 m-2"} onClick={() => searchBySemester(value)}>{value}</button>
        ))}
      </ul>
    </div>
  )
}
