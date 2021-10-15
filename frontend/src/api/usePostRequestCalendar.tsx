interface PostRequestProps {
  readonly path: string;
  readonly veranstaltungsIds: number[];
}

export default function usePostRequestCalendar({ path, veranstaltungsIds }: PostRequestProps) {
  const getCalendar = () => fetch(path, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({veranstaltungsIds} )
  }).then(async (response) => {
    if (!response.ok){
      throw Error("Could not fetch data");
    }
    return response.blob()
  });

  return { getCalendar  };
}
