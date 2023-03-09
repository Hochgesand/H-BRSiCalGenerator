import axios, {Axios} from "axios";
import Meeting from "../Objects/Meeting";
import {baseUrl} from "../Objects/endpoints";

async function getRequest(path: string, token: string) {
  return axios.get(path, {headers: {
      Authorization: `Bearer ${token}`
    }}).then(async (response) => {
    if (response.status != 200){
      throw Error("Could not fetch data");
    }
    return response.data
  })
}

// TODO: params solution is temporary
async function postRequest(path: string, ...params: any) {
  return axios.post(path, params).then(async (response) => {
    if (response.status != 200){
      throw Error("Could not fetch data");
    }
    return response.data
  })
}

const APIService = {
  getMeetings: async(course: string) => {
    const response: Meeting[] = await getRequest(`${baseUrl}/veranstaltung/getVeranstaltungByStudiengang?studiengang=${course}`, "")
    response.sort((firstElement, secondElement) => { if (firstElement.name < secondElement.name) {
        return -1;
      }
        if (firstElement > secondElement) {
          return 1;
        }
        return 0; }
    );
    return response
  }
}

export default APIService;
