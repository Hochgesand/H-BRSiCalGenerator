import axios from "axios";

async function getRequest(path: string, token: string) {
  return axios.get(path, {headers: {
      Authorization: `Bearer ${token}`
    }});
}

// TODO: params solution is temporary
async function postRequest(path: string, ...params: any) {
  return axios.post(path, params);
}

const APIService = {
}

export default APIService;
