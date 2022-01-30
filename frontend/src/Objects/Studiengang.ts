import Veranstaltung from "./Veranstaltung";

export interface Studiengang {
  id: number
  name: string
  veranstaltungen: Veranstaltung[]
}
