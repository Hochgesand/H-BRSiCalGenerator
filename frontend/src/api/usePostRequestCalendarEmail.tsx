interface PostRequestProps {
  readonly path: string;
  readonly body: any;
}

export default function usePostRequestCalendar({ path, body: {veranstaltungsIds, email} }: PostRequestProps) {
  const notrack: boolean = localStorage.getItem("notrack") !== null
  const getCalendarEmailResponse = () => fetch(path, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({veranstaltungsIds, email, notrack} )
  }).then(async (response) => {
    if (!response.ok){
      return response.text().then(text => {throw Error(text + "soos")});
    }
    return response
  });

  return { getCalendarEmailResponse  };
}
