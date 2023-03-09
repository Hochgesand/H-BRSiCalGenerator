import Meeting from "../../../Objects/Meeting";
import React from "react";

export default function VeranstaltungsListe(props: {veranstaltungen: Meeting[], selectedVeranstaltungen: Meeting[], setSelectedVeranstaltungen: React.Dispatch<React.SetStateAction<Meeting[]>>, defaultSelected: boolean}){
  const addItemToSelectedItems = (i: Meeting) => {
    let newSelectedItems: Meeting[] = []
    props.selectedVeranstaltungen.forEach(x => {
      newSelectedItems.push(x)
    })
    newSelectedItems.push(i)
    props.setSelectedVeranstaltungen(newSelectedItems)
  }

  const removeItemToSelectedItems = (i: number) => {
    let newSelectedItems: Meeting[] = []
    props.selectedVeranstaltungen.forEach(x => {
      if (x.id !== i)
        newSelectedItems.push(x)
    })
    props.setSelectedVeranstaltungen(newSelectedItems)
  }

  const checkIfIsSelected = (meeting: Meeting): boolean => {
    let temp = false
    props.selectedVeranstaltungen.forEach(x => {
      if (x.name === meeting.name)
        temp = true
    })
    return temp
  }

  return(
    <fieldset className="border-t border-b border-gray-500">
      <div className="divide-y divide-gray-500">
        {props.veranstaltungen.map((veranstaltung, id) => (
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
                    addItemToSelectedItems(veranstaltung)
                    event.target.checked = true
                  } else {
                    removeItemToSelectedItems(veranstaltung.id)
                    event.target.checked = false
                  }
                }}
                className="focus:ring-base-500 h-10 w-10 text-primary border-gray-300 rounded"
              />
            </div>
            <div className="min-w-0 flex-1 text-sm">
              <label htmlFor="comments" className="font-medium text-gray-700">
                {veranstaltung.name}
              </label>
              <p id="comments-description" className="text-gray-500">
                {veranstaltung.professor}
              </p>
            </div>
          </div>
        ))}
      </div>
    </fieldset>
  )
}
