import {useEffect, useState} from "react";
import {baseUrl} from "./endpoints";
import useGetRequest from "../api/useGetRequest";
import Veranstaltung from "./Veranstaltung";
import Loading from "../Loading";
import Error from "../Error";

export default function VeranstaltungsContainer(){

  const [veranstaltungsData, setVeranstaltungsData] = useState([] as Veranstaltung[]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("")
  const path = `${baseUrl}/getVeranstaltungen`;
  const {getData} = useGetRequest({path: path})

  useEffect(() => {
    async function fetchData() {
      await getData().then(function (json){
        let veranstaltungen: Veranstaltung[] = [];
        veranstaltungen = json

        setVeranstaltungsData(json);
        setLoading(false)
      }).catch( err =>{
          setError(err.message)
          setLoading(false);
        }
      );
    }
    fetchData();
  }, []); // eslint-disable-line react-hooks/exhaustive-deps

  if (loading){
    return <Loading/>
  }

  if (error.length > 0){
    return(
      <Error msg={error}/>
    );
  }

  return(
    <div>
      hat funktioniert!
    </div>
  );

}
