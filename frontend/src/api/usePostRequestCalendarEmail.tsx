interface PostRequestProps {
  readonly path: string;
  readonly veranstaltungsIds: number[];
  readonly email: string;
}

export default function usePostRequestCalendar({ path, veranstaltungsIds, email }: PostRequestProps) {
  const getCalendarEmailResponse = () => fetch(path, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({veranstaltungsIds, email} )
  }).then(async (response) => {
    if (!response.ok){
      throw Error("Could not send E-Mail");
    }
    return response
  });

  return { getCalendarEmailResponse  };
}
