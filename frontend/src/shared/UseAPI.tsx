import {useEffect, useState} from "react";
import APIService from "./APIService";

export default function useAPI<T>(method: string, ...params: any[]) {
  const [data, setData]           = useState([] as T);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError]         = useState("");

  const getData = async () => {
    setError("");

    try {
      setIsLoading(true);
      // @ts-ignore
      const temp: T = await APIService[method](...params);
      setData(temp);
    } catch (e: any) {
      setError(e.message);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    getData();
  }, []);

  return { data, isLoading, error, getData };
}
