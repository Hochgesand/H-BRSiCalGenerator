interface PostRequestProps {
  readonly path: string;
  readonly veranstaltungsIds: number[];
}

export default function usePostRequestCalendar({ path, veranstaltungsIds }: PostRequestProps) {
  const notrack: boolean = localStorage.getItem("notrack") !== null
  const getCalendar = () => fetch(path, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({veranstaltungsIds, notrack} )
  }).then(async (response) => {
    if (!response.ok){
      return response.text().then(text => {throw Error(text)});
    }
    return response.blob()
  });

  return { getCalendar  };
}
