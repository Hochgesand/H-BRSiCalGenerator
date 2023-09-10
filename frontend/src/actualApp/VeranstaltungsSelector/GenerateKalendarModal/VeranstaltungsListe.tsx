import Meeting from "../../../Objects/Meeting";
import React, {Dispatch, SetStateAction} from "react";

interface DisplayMeetingData{
  selectedMeetings: Meeting[]
  setSelectedMeetings: Dispatch<SetStateAction<Meeting[]>>
  filteredMeetings: Meeting[]
  defaultSelected: boolean
}

export default function VeranstaltungsListe(props: DisplayMeetingData){
  const addItemToSelectedItems = (newMeeting: Meeting) => {
    let newSelectedItems: Meeting[] = []
    props.selectedMeetings.forEach(x => {
      newSelectedItems.push(x)
    })
    if (newSelectedItems.find(x => x.id === newMeeting.id) === undefined)
      newSelectedItems.push(newMeeting)
    props.setSelectedMeetings(newSelectedItems)
  }

  const removeItemToSelectedItems = (meetingToRemove: Meeting) => {
    let newSelectedItems: Meeting[] = []
    props.selectedMeetings.forEach(x => {
      if (x.id !== meetingToRemove.id)
        newSelectedItems.push(x)
    })
    props.setSelectedMeetings(newSelectedItems)
  }

  return(
    <fieldset className="border-t border-b border-gray-500">
      <div className="divide-y divide-gray-500">
        {props.filteredMeetings.map((meeting) => (
          <div className="relative flex items-start py-2 md:py-4">
            <div className="mr-3 m-auto flex items-center h-5">
              <input
                id="comments"
                aria-describedby="comments-description"
                name="comments"
                type="checkbox"
                checked={props.defaultSelected}
                onChange={event => {
                  if (event.target.checked) {
                    addItemToSelectedItems(meeting)
                    event.target.checked = true
                  } else {
                    removeItemToSelectedItems(meeting)
                    event.target.checked = false
                  }
                }}
                className="focus:ring-base-500 h-10 w-10 text-primary border-gray-300 rounded"
              />
            </div>
            <div className="min-w-0 flex-1 text-sm">
              <label htmlFor="comments" className="font-medium text-gray-700">
                {meeting.name}
              </label>
              <p id="comments-description" className="text-gray-500">
                Bei: {meeting.professor}, Semester: {meeting.semester}
              </p>
            </div>
          </div>
        ))}
      </div>
    </fieldset>
  )
}
