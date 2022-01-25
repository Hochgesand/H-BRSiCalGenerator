(this["webpackJsonphbrs-ical-generator-frontend"]=this["webpackJsonphbrs-ical-generator-frontend"]||[]).push([[0],{21:function(e,n,t){},42:function(e,n,t){},74:function(e,n,t){"use strict";t.r(n);var a=t(1),r=t.n(a),s=t(17),c=t.n(s),i=(t(21),t(42),t(12)),l=t(7),d=t(5),u=t.n(d),o=t(2),h="https://moin.meister.ovh:8443";function b(e){var n=e.path;return{getData:function(){return fetch(n,{method:"get",headers:void 0}).then(function(){var e=Object(i.a)(u.a.mark((function e(n){return u.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(n.ok){e.next=2;break}return e.abrupt("return",n.text().then((function(e){throw Error(n.status+": "+e)})));case 2:return e.abrupt("return",n.json());case 3:case"end":return e.stop()}}),e)})));return function(n){return e.apply(this,arguments)}}())}}}var j=t(0);function m(){return Object(j.jsx)("button",{className:"btn btn-lg loading",children:"loading"})}function g(e){return Object(j.jsx)("div",{className:"alert alert-error mb-4",children:Object(j.jsxs)("div",{className:"flex-1",children:[Object(j.jsx)("svg",{xmlns:"http://www.w3.org/2000/svg",fill:"none",viewBox:"0 0 24 24",className:"w-6 h-6 mx-2 stroke-current",children:Object(j.jsx)("path",{"stroke-linecap":"round","stroke-linejoin":"round","stroke-width":"2",d:"M18.364 18.364A9 9 0 005.636 5.636m12.728 12.728A9 9 0 015.636 5.636m12.728 12.728L5.636 5.636"})}),Object(j.jsx)("label",{children:e.msg})]})})}var f=t(13);t(34),t(35);function p(e){var n=e.path,t=e.veranstaltungsIds;return{getCalendar:function(){return fetch(n,{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({veranstaltungsIds:t})}).then(function(){var e=Object(i.a)(u.a.mark((function e(n){return u.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(n.ok){e.next=2;break}return e.abrupt("return",n.text().then((function(e){throw Error(e)})));case 2:return e.abrupt("return",n.blob());case 3:case"end":return e.stop()}}),e)})));return function(n){return e.apply(this,arguments)}}())}}}var x=t(27);function O(e){var n=Object(a.useState)(""),t=Object(l.a)(n,2),r=t[0],s=t[1],c=Object(a.useState)(""),d=Object(l.a)(c,2),o=d[0],b=d[1],m="",O=Object(a.useState)(!1),w=Object(l.a)(O,2),v=w[0],k=w[1],S=Object(a.useRef)(null),N=p({path:"".concat(h,"/sememesteriCal"),veranstaltungsIds:e.veranstaltungsIds}),C=p({path:"".concat(h,"/sememesteriCalAsCSV"),veranstaltungsIds:e.veranstaltungsIds}),A=function(e){var n=e.path,t=e.body,a=t.veranstaltungsIds,r=t.email;return{getCalendarEmailResponse:function(){return fetch(n,{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({veranstaltungsIds:a,email:r})}).then(function(){var e=Object(i.a)(u.a.mark((function e(n){return u.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(n.ok){e.next=2;break}return e.abrupt("return",n.text().then((function(e){throw Error(e+"soos")})));case 2:return e.abrupt("return",n);case 3:case"end":return e.stop()}}),e)})));return function(n){return e.apply(this,arguments)}}())}}}({path:"".concat(h,"/sememesteriCalEmail"),body:{veranstaltungsIds:e.veranstaltungsIds,email:r}}),y=A.getCalendarEmailResponse;return Object(j.jsx)(j.Fragment,{children:e.showKalendarModal?Object(j.jsx)("div",{className:"container flex mx-auto z-10 absolute inset-0 justify-center rounded-box h-screen",children:Object(j.jsxs)("div",{className:"m-auto rounded-box bg-base-300 md:w-3/4 w-full xl:w-2/3 2xl:1 h-screen flex-none z-20",children:[Object(j.jsxs)("div",{className:"h-30 p-4",children:[o.length>0?Object(j.jsx)(g,{msg:o}):null,Object(j.jsxs)("div",{className:"grid grid-cols-2 grid-rows-1 gap-4",children:[Object(j.jsx)("button",{className:"btn btn-lg w-full mb-4 ".concat(v?"loading":null),type:"submit",disabled:0===e.veranstaltungsIds.length||v,onClick:function(){m="".concat(h,"/sememesteriCal"),console.log(m),k(!0),N.getCalendar().then((function(n){k(!1),Object(x.saveAs)(n,"calendar.ics"),e.setShowKalendarModal(!1),b("")})).catch((function(e){b(e.message),console.log(o),k(!1)}))},children:"Download calendar.ics"}),Object(j.jsx)("button",{className:"btn btn-lg w-full mb-4 ".concat(v?"loading":null),type:"submit",disabled:0===e.veranstaltungsIds.length||v,onClick:function(){m="".concat(h,"/sememesteriCalAsCSV"),k(!0),C.getCalendar().then((function(n){k(!1),Object(x.saveAs)(n,"calendar.csv"),e.setShowKalendarModal(!1),b("")})).catch((function(e){b(e.message),console.log(o),k(!1)}))},children:"Download Kalender as CSV (BETA)"})]}),Object(j.jsx)("div",{className:"rounded-box bg-base-200 h-6 w-full mb-4",children:Object(j.jsx)("p",{className:"w-full text-center",children:"ODER"})}),Object(j.jsxs)("div",{className:"rounded-box bg-base-300 grid grid-cols-2 gap-4 mb-4 w-auto",children:[Object(j.jsx)("input",{className:"appearance-none w-full bg-base-200 border border-white rounded py-4 px-4 leading-tight focus:outline-none focus:bg-base-400 text-2xl",id:"grid-first-email",type:"email",placeholder:"E-Mail",onChange:function(e){return s(e.target.value)}}),Object(j.jsx)("button",{className:"btn btn-lg w-full ".concat(v?"loading":null),type:"submit",disabled:0===e.veranstaltungsIds.length||v||0===r.length,onClick:function(){k(!0);y().then(function(){var n=Object(i.a)(u.a.mark((function n(t){return u.a.wrap((function(n){for(;;)switch(n.prev=n.next){case 0:return k(!1),n.t0=alert,n.next=4,t.text();case 4:n.t1=n.sent,(0,n.t0)(n.t1),b(""),e.setShowKalendarModal(!1);case 8:case"end":return n.stop()}}),n)})));return function(e){return n.apply(this,arguments)}}()).catch((function(e){b(e.message),console.log(o),k(!1)}))},children:"Schick's per E-Mail"})]}),Object(j.jsx)("button",{className:"btn btn-md w-full mb-4",onClick:function(){e.setShowKalendarModal(!1),b("")},disabled:v,children:"Abbrechen"})]}),Object(j.jsx)("p",{className:"text-3xl ml-4",children:"Ausgew\xe4hlte Module/Veranstaltungen"}),Object(j.jsx)("div",{className:"ag-theme-alpine-dark rounded-box p-4 bg-base-300 h-screen",children:Object(j.jsxs)(f.AgGridReact,{ref:S,rowData:e.selectedData,onFirstDataRendered:function(){return S.current.api.sizeColumnsToFit()},children:[Object(j.jsx)(f.AgGridColumn,{field:"name",width:380,headerName:"Veranstaltung"}),Object(j.jsx)(f.AgGridColumn,{field:"studienGangSemester",width:400,headerName:"Fachbereich / Semester"})]})})]})}):null})}function w(e){var n=e.veranstaltung,t=Object(o.f)(),r=Object(a.useState)([]),s=Object(l.a)(r,2),c=s[0],d=s[1],p=Object(a.useState)([]),x=Object(l.a)(p,2),w=x[0],v=x[1],k=Object(a.useState)([]),S=Object(l.a)(k,2),N=S[0],C=S[1],A=Object(a.useState)(!0),y=Object(l.a)(A,2),I=y[0],z=y[1],B=Object(a.useState)(""),D=Object(l.a)(B,2),E=D[0],M=D[1],R=Object(a.useState)(!1),F=Object(l.a)(R,2),T=F[0],G=F[1],K=b({path:"".concat(h,"/getVeranstaltungByStudiengang?studiengang=").concat(n)}).getData,H=Object(a.useRef)(null);Object(a.useEffect)((function(){function e(){return(e=Object(i.a)(u.a.mark((function e(){return u.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,K().then((function(e){d(e),z(!1),M("")})).catch((function(e){console.log(e.message),M(e.message),z(!1)}));case 2:case"end":return e.stop()}}),e)})))).apply(this,arguments)}!function(){e.apply(this,arguments)}()}),[]);return I?Object(j.jsx)(m,{}):E.length>0?Object(j.jsx)(g,{msg:E}):Object(j.jsxs)(j.Fragment,{children:[Object(j.jsx)(O,{showKalendarModal:T,setShowKalendarModal:G,selectedData:w,veranstaltungsIds:N}),Object(j.jsxs)("div",{className:T?"filter blur-lg":"",onClick:function(){return G(!1)},children:[Object(j.jsx)("div",{className:"grid grid-rows-2 grid-rows-none gap-4 2xl:w-10/12 mb-4 xl:w-11/12",children:Object(j.jsxs)("div",{className:"grid md:grid-cols-3 md:grid-rows-1 grid-cols-1 grid-rows-3 gap-4 rounded-box p-3 bg-base-300",children:[Object(j.jsx)("button",{onClick:function(e){return function(e){var n=[],t=H.current.api.getSelectedNodes().map((function(e){return e.data}));t.map((function(e){return n.push(e.id)})),v(t),C(n),e.stopPropagation(),M(""),G(!0)}(e)},className:"btn btn-lg w-full",children:"Hol dir deinen Kalender!"}),Object(j.jsx)("a",{href:"https://github.com/Hochgesand/H-BRSiCalGenerator",target:"_blank",rel:"noopener noreferrer",children:Object(j.jsx)("button",{className:"btn btn-lg w-full",children:"Gib mir einen Stern auf Github \u2764"})}),Object(j.jsx)("button",{className:"btn btn-lg w-full",onClick:function(){t.push("/FAQ")},children:"FAQ / HILFE!"})]})}),Object(j.jsx)("div",{className:"ag-theme-alpine-dark rounded-box p-3 bg-base-300",style:{height:800},children:Object(j.jsxs)(f.AgGridReact,{rowData:c,enableRangeSelection:!0,rowSelection:"multiple",ref:H,rowMultiSelectWithClick:!0,onFirstDataRendered:function(){return H.current.api.sizeColumnsToFit()},children:[Object(j.jsx)(f.AgGridColumn,{field:"name",width:380,sortable:!0,filter:!0,checkboxSelection:!0,resizable:!0,headerName:"Veranstaltung",floatingFilter:!0}),Object(j.jsx)(f.AgGridColumn,{field:"prof",width:150,sortable:!0,filter:!0,resizable:!0,floatingFilter:!0})]})})]})]})}function v(){var e=b({path:"".concat(h,"/getStudiengaenge")}).getData,n=Object(a.useState)(!0),t=Object(l.a)(n,2),r=t[0],s=t[1],c=Object(a.useState)(""),d=Object(l.a)(c,2),o=d[0],f=d[1],p=Object(a.useState)([]),x=Object(l.a)(p,2),O=x[0],v=x[1],k=Object(a.useState)(""),S=Object(l.a)(k,2),N=S[0],C=S[1];return Object(a.useEffect)((function(){function n(){return(n=Object(i.a)(u.a.mark((function n(){return u.a.wrap((function(n){for(;;)switch(n.prev=n.next){case 0:return n.next=2,e().then((function(e){v(e),s(!1),f("")})).catch((function(e){console.log(e.message),f(e.message),s(!1)}));case 2:case"end":return n.stop()}}),n)})))).apply(this,arguments)}!function(){n.apply(this,arguments)}()}),[]),r?Object(j.jsx)(m,{}):o.length>0?Object(j.jsx)(g,{msg:o}):Object(j.jsxs)("div",{children:[Object(j.jsxs)("div",{className:"rounded-box p-4 bg-base-300 mb-4",children:[Object(j.jsx)("h2",{className:"text-4xl mb-2",children:"H-BRS Kalendergenerator v1.1"}),Object(j.jsx)("p",{children:"Ich \xfcbernehme keine Haftung f\xfcr die Richtigkeit der generierten Daten, alles nach bestem Wissen und Gewissen. Fehler bitte an a@andrevr.de melden."}),Object(j.jsx)("p",{children:"Ich habs nicht f\xfcrs Smartphone entwickelt, benutzt es lieber am PC. Da sieht alles ordentlich aus"}),Object(j.jsx)("p",{children:"Wenn's euch gef\xe4llt, empfehlt es euren Kommilitonen! \ud83d\ude01"})]}),0===N.length?Object(j.jsx)("div",{className:"rounded-box p-3 bg-base-300 md:w-1/4 w-full",children:Object(j.jsxs)("div",{className:"dropdown w-full",children:[Object(j.jsx)("div",{tabIndex:0,className:" btn btn-lg w-full",children:"Bitte Studiengang ausw\xe4hlen!"}),Object(j.jsx)("ul",{tabIndex:0,className:"p-2 shadow menu dropdown-content bg-base-300 rounded-box w-full",children:O.map((function(e){return Object(j.jsx)("li",{className:"m-1",children:Object(j.jsx)("button",{className:"btn",onClick:function(){return C(e)},children:e})})}))})]})}):"",0!==N.length?Object(j.jsx)(w,{veranstaltung:N}):""]})}var k=function(){return Object(j.jsx)("div",{className:"App",children:Object(j.jsx)(v,{})})},S=function(e){e&&e instanceof Function&&t.e(3).then(t.bind(null,75)).then((function(n){var t=n.getCLS,a=n.getFID,r=n.getFCP,s=n.getLCP,c=n.getTTFB;t(e),a(e),r(e),s(e),c(e)}))},N=t(19);function C(){var e=Object(o.f)();return Object(j.jsxs)("div",{className:"p-5 flex-col gap-3",children:[Object(j.jsx)("button",{className:"btn btn-lg mb-4",onClick:function(){return e.goBack()},children:"Back"}),Object(j.jsxs)("div",{className:"rounded-box p-4 bg-base-300 flex-grow-0 w-3/4",children:[Object(j.jsx)("p",{className:"text-3xl",children:"Anleitung:"}),Object(j.jsx)("p",{className:"text-2xl",children:"Schritt 1:"}),Object(j.jsx)("p",{children:'W\xe4hle dir einfach alle Veranstaltungen aus die du besuchen m\xf6chtest. Du kannst die Tabelle nach belieben sortieren und mit dem Burgerbutton kannst du auch gezielt nach Keywords suchen. Wenn du alle Veranstaltungen ausgew\xe4hlt hast klicke auf "Hol dir deinen Kalender" ... '}),Object(j.jsx)("p",{className:"text-2xl",children:"Schritt 2: "}),Object(j.jsx)("p",{children:'Jetzt hast du zwei M\xf6glichkeiten: 1.: Klicke auf "Download calendar.ics" und importiere es wie in Beispiel 3 einfach selber am Rechner. 2.: Tippe deine E-Mail Adresse ein und lasse es dir bequem per E-Mail Schicken. Klicke daf\xfcr einfach nachdem du deine E-Mail Adresse eingetragen hast auf "Schick\'s per E-Mail". Damit kriegste dann die ics auf\'s Handy und kannst sie ggf. sogar noch einfacher importieren.'}),Object(j.jsx)("p",{className:"text-2xl",children:"Misc:"}),Object(j.jsx)("p",{children:"Packages und Technologien die ich benutzt habe:"}),Object(j.jsx)("p",{children:"Spring Boot (Java):"}),Object(j.jsxs)("ul",{children:[Object(j.jsx)("li",{children:" - Apache POI um Exceltabellen zu parsen"}),Object(j.jsx)("li",{children:" - ical4j um den Calender zu generieren"})]}),Object(j.jsx)("p",{children:"React:"}),Object(j.jsxs)("ul",{children:[Object(j.jsx)("li",{children:" - AGGrid f\xfcr die Tabelle, kein Bock gehabt selber Sortieralgorithmen zu implementieren"}),Object(j.jsx)("li",{children:" - Locker noch ca. 2000 Packages mehr die mit React gekommen sind, npm macht npm sachen"}),Object(j.jsx)("li",{children:" - TailwindCSS f\xfcr's CSS"})]}),Object(j.jsx)("p",{className:"text-2xl",children:"FAQ:"}),Object(j.jsx)("p",{children:"Q: Warum machste die App nicht responsive damit ich das auch bequem auf dem Handy nutzen kann?"}),Object(j.jsx)("p",{children:"A: Warum nutzt du die App nicht einfach auf dem Rechner und schickst dir den Stundenplan per E-Mail?"}),Object(j.jsx)("p",{children:"Q: Warum werden manche Veranstaltungen doppelt angezeigt?"}),Object(j.jsx)("p",{children:"A: Manche Veranstaltungen gibt es Fachbereich/Semester \xfcbergreifend und ich habe noch kein Ablauf im Backend implementiert die schaut ob die Eventuhrzeiten/R\xe4ume \xfcbereinstimmen damit ich gew\xe4hrleisten kann das die auch tats\xe4chlich die gleichen Veranstaltungen sind. Beispielsweise k\xf6nnte ja eine Veranstaltung f\xfcr Nachschreiber existieren und damit z.B. in einem anderen Raum stattfinden. W\xe4hlt daher immer die Veranstaltung f\xfcr euren Fachbereich/Semester."}),Object(j.jsx)("p",{children:"Q: Wie kriegst du die Stundenpl\xe4ne in die Anwendung importiert?"}),Object(j.jsx)("p",{children:"A: Ich zieh mir einfach die Stundenpl\xe4ne von Eva und aktualisiere Sie jeden Tag damit alle \xc4nderungen frisch auf meinem Backend liegen."}),Object(j.jsx)("p",{children:"Q: Welche Daten erhebst du?"}),Object(j.jsx)("p",{children:"A: Fast keine. Ich logge nichts und m\xf6chte das auch nicht. Die einzigen Dinge die ich speichere sind IP Adressen die die Api missbrauchen. Wie ich die erfasse erkl\xe4re ich weiter unten. Ich habe \xfcberlegt ob ich vielleicht Metriken dar\xfcber erfasse und vielleicht jedes Semesterende anonymisierte Statistiken bereitstelle (das erstellen eines Kalenders ist sowieso anonym solange man es ohne E-Mail macht). Das w\xe4re vielleicht interessant. Allerdings bin ich mir da noch nicht ganz so sicher ob ich das mache. Wenn ich daran arbeite, werde ich das hier vorher bekannt geben. Ich nutze keine Daten ohne vorher zu fragen."}),Object(j.jsx)("p",{children:"Q: Und wann werde ich jetzt genau geloggt?"}),Object(j.jsx)("p",{children:"A: Sobald ihr ein 429er Error bekommt (Too Many Requests) wird eure IP Adresse gespeichert. Wenn diese IP Adresse weiterhin versucht Requests zu schicken wird sie dauerhaft gebannt. Wenn das Problem \xf6fter auftritt werde ich die IP Adresse dem entsprechenden Anbieter melden. Daf\xfcr m\xfcsst ihr aber schon sehr aggressiv vorgehen. Wenn ihr blockiert werdet weil ihr spa\xdf dran habt meine Anwendung zu testen oder eure Hackerskills beweisen wollt, d\xfcrft ihr das gerne tun. Meldet euch bei mir ich hab damit keine Probleme, ganz im gegenteil, ich w\xfcrde mich sogar freuen :) Schickt mir einfach ne E-Mail an a@andrevr.de."})]}),Object(j.jsxs)("div",{className:"mt-5",children:[Object(j.jsx)("p",{className:"text-3xl",children:'Beispiel 1: "Ich bin bei Herr Berrendorf in EidiP und geh\xf6re der Gruppe D an.'}),Object(j.jsx)("img",{src:"/H-BRSiCalGenerator/FUiTCuS2BJ.gif",alt:""})]}),Object(j.jsxs)("div",{className:"mt-5",children:[Object(j.jsx)("p",{className:"text-3xl",children:'Beispiel 2: "Hilfe ich sehe nicht den ganzen Text!'}),Object(j.jsx)("img",{src:"/H-BRSiCalGenerator/aTkfaQusHi.gif",alt:""})]}),Object(j.jsxs)("div",{className:"mt-5",children:[Object(j.jsx)("p",{className:"text-3xl",children:'Beispiel 3: "Is ja alles sch\xf6n und toll, aber was mache ich jetzt mit dieser komischen calendar.ics Datei (Beispiel an Google Kalender)"'}),Object(j.jsx)("img",{src:"/H-BRSiCalGenerator/Z795yqtZO4.gif",alt:""})]})]})}c.a.render(Object(j.jsx)(r.a.StrictMode,{children:Object(j.jsx)(N.a,{children:Object(j.jsxs)(o.c,{children:[Object(j.jsx)(o.a,{exact:!0,path:"/FAQ",children:Object(j.jsx)(C,{})}),Object(j.jsx)(o.a,{path:"/",children:Object(j.jsx)(k,{})})]})})}),document.getElementById("root")),S()}},[[74,1,2]]]);
//# sourceMappingURL=main.2f400034.chunk.js.map