interface GetRequestProps {
  readonly path: string;
}

export default function useGetRequest({ path }: GetRequestProps) {
  const getData = () => fetch(path, {
    method: "get",
    headers: ""
      ? new Headers({
        "Content-Type": "application/json"
      }) : undefined
  }).then(async (response) => {
    if (!response.ok){
      return response.text().then(text => {throw Error(response.status + ": " + text)});
    }
    return response.json()
  });

  return { getData  };
}
