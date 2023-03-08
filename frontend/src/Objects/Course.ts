import Meeting from "./Meeting";

export interface Course {
  id: number
  name: string
  meetings: Meeting[]
}
