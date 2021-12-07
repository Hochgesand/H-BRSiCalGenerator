(this["webpackJsonphbrs-ical-generator-frontend"]=this["webpackJsonphbrs-ical-generator-frontend"]||[]).push([[0],{21:function(e,n,t){},41:function(e,n,t){},70:function(e,n,t){"use strict";t.r(n);var a=t(1),r=t.n(a),s=t(17),i=t.n(s),c=(t(21),t(41),t(6)),l=t.n(c),d=t(12),o=t(11),u=t(2),h="https://moin.meister.ovh:8443";var b=t(0);function j(){return Object(b.jsx)("button",{className:"btn btn-lg loading",children:"loading"})}function m(e){return Object(b.jsx)("div",{className:"alert alert-error mb-4",children:Object(b.jsxs)("div",{className:"flex-1",children:[Object(b.jsx)("svg",{xmlns:"http://www.w3.org/2000/svg",fill:"none",viewBox:"0 0 24 24",className:"w-6 h-6 mx-2 stroke-current",children:Object(b.jsx)("path",{"stroke-linecap":"round","stroke-linejoin":"round","stroke-width":"2",d:"M18.364 18.364A9 9 0 005.636 5.636m12.728 12.728A9 9 0 015.636 5.636m12.728 12.728L5.636 5.636"})}),Object(b.jsx)("label",{children:e.msg})]})})}var g=t(13);t(32),t(33);function f(e){var n=e.path,t=e.veranstaltungsIds;return{getCalendar:function(){return fetch(n,{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({veranstaltungsIds:t})}).then(function(){var e=Object(d.a)(l.a.mark((function e(n){return l.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(n.ok){e.next=2;break}return e.abrupt("return",n.text().then((function(e){throw Error(e)})));case 2:return e.abrupt("return",n.blob());case 3:case"end":return e.stop()}}),e)})));return function(n){return e.apply(this,arguments)}}())}}}var p=t(27);function x(e){var n=Object(a.useState)(""),t=Object(o.a)(n,2),r=t[0],s=t[1],i=Object(a.useState)(""),c=Object(o.a)(i,2),u=c[0],j=c[1],x="",O=Object(a.useState)(!1),w=Object(o.a)(O,2),v=w[0],k=w[1],S=Object(a.useRef)(null),N=f({path:"".concat(h,"/sememesteriCal"),veranstaltungsIds:e.veranstaltungsIds}),A=f({path:"".concat(h,"/sememesteriCalAsCSV"),veranstaltungsIds:e.veranstaltungsIds}),C=function(e){var n=e.path,t=e.body,a=t.veranstaltungsIds,r=t.email;return{getCalendarEmailResponse:function(){return fetch(n,{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({veranstaltungsIds:a,email:r})}).then(function(){var e=Object(d.a)(l.a.mark((function e(n){return l.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(n.ok){e.next=2;break}return e.abrupt("return",n.text().then((function(e){throw Error(e+"soos")})));case 2:return e.abrupt("return",n);case 3:case"end":return e.stop()}}),e)})));return function(n){return e.apply(this,arguments)}}())}}}({path:"".concat(h,"/sememesteriCalEmail"),body:{veranstaltungsIds:e.veranstaltungsIds,email:r}}),z=C.getCalendarEmailResponse;return Object(b.jsx)(b.Fragment,{children:e.showKalendarModal?Object(b.jsx)("div",{className:"container flex mx-auto z-10 absolute inset-0 justify-center rounded-box h-screen",children:Object(b.jsxs)("div",{className:"m-auto rounded-box bg-base-300 w-3/4 xl:w-2/3 2xl:1 h-screen flex-none z-20",children:[Object(b.jsxs)("div",{className:"h-30 p-4",children:[u.length>0?Object(b.jsx)(m,{msg:u}):null,Object(b.jsxs)("div",{className:"grid grid-cols-2 grid-rows-1 gap-4",children:[Object(b.jsx)("button",{className:"btn btn-lg w-full mb-4 ".concat(v?"loading":null),type:"submit",disabled:0===e.veranstaltungsIds.length||v,onClick:function(){x="".concat(h,"/sememesteriCal"),console.log(x),k(!0),N.getCalendar().then((function(n){k(!1),Object(p.saveAs)(n,"calendar.ics"),e.setShowKalendarModal(!1),j("")})).catch((function(e){j(e.message),console.log(u),k(!1)}))},children:"Download calendar.ics"}),Object(b.jsx)("button",{className:"btn btn-lg w-full mb-4 ".concat(v?"loading":null),type:"submit",disabled:0===e.veranstaltungsIds.length||v,onClick:function(){x="".concat(h,"/sememesteriCalAsCSV"),k(!0),A.getCalendar().then((function(n){k(!1),Object(p.saveAs)(n,"calendar.csv"),e.setShowKalendarModal(!1),j("")})).catch((function(e){j(e.message),console.log(u),k(!1)}))},children:"Download Kalender as CSV (BETA)"})]}),Object(b.jsx)("div",{className:"rounded-box bg-base-200 h-6 w-full mb-4",children:Object(b.jsx)("p",{className:"w-full text-center",children:"ODER"})}),Object(b.jsxs)("div",{className:"rounded-box bg-base-300 grid grid-cols-2 gap-4 mb-4 w-auto",children:[Object(b.jsx)("input",{className:"appearance-none w-full bg-base-200 border border-white rounded py-4 px-4 leading-tight focus:outline-none focus:bg-base-400 text-2xl",id:"grid-first-email",type:"email",placeholder:"E-Mail",onChange:function(e){return s(e.target.value)}}),Object(b.jsx)("button",{className:"btn btn-lg w-full ".concat(v?"loading":null),type:"submit",disabled:0===e.veranstaltungsIds.length||v||0===r.length,onClick:function(){k(!0);z().then(function(){var n=Object(d.a)(l.a.mark((function n(t){return l.a.wrap((function(n){for(;;)switch(n.prev=n.next){case 0:return k(!1),n.t0=alert,n.next=4,t.text();case 4:n.t1=n.sent,(0,n.t0)(n.t1),j(""),e.setShowKalendarModal(!1);case 8:case"end":return n.stop()}}),n)})));return function(e){return n.apply(this,arguments)}}()).catch((function(e){j(e.message),console.log(u),k(!1)}))},children:"Schick's per E-Mail"})]}),Object(b.jsx)("button",{className:"btn btn-md w-full mb-4",onClick:function(){e.setShowKalendarModal(!1),j("")},disabled:v,children:"Abbrechen"})]}),Object(b.jsx)("p",{className:"text-3xl ml-4",children:"Ausgew\xe4hlte Module/Veranstaltungen"}),Object(b.jsx)("div",{className:"ag-theme-alpine-dark rounded-box p-4 bg-base-300 h-screen",children:Object(b.jsxs)(g.AgGridReact,{ref:S,rowData:e.selectedData,onFirstDataRendered:function(){return S.current.api.sizeColumnsToFit()},children:[Object(b.jsx)(g.AgGridColumn,{field:"name",width:380,headerName:"Veranstaltung"}),Object(b.jsx)(g.AgGridColumn,{field:"studienGangSemester",width:400,headerName:"Fachbereich / Semester"})]})})]})}):null})}function O(){var e=Object(u.f)(),n=Object(a.useState)([]),t=Object(o.a)(n,2),r=t[0],s=t[1],i=Object(a.useState)([]),c=Object(o.a)(i,2),f=c[0],p=c[1],O=Object(a.useState)([]),w=Object(o.a)(O,2),v=w[0],k=w[1],S=Object(a.useState)(!0),N=Object(o.a)(S,2),A=N[0],C=N[1],z=Object(a.useState)(""),y=Object(o.a)(z,2),I=y[0],M=y[1],B=Object(a.useState)(!1),E=Object(o.a)(B,2),F=E[0],D=E[1],R=function(e){var n=e.path;return{getData:function(){return fetch(n,{method:"get",headers:void 0}).then(function(){var e=Object(d.a)(l.a.mark((function e(n){return l.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(n.ok){e.next=2;break}return e.abrupt("return",n.text().then((function(e){throw Error(n.status+": "+e)})));case 2:return e.abrupt("return",n.json());case 3:case"end":return e.stop()}}),e)})));return function(n){return e.apply(this,arguments)}}())}}}({path:"".concat(h,"/getVeranstaltungen")}),G=R.getData,T=Object(a.useRef)(null);Object(a.useEffect)((function(){function e(){return(e=Object(d.a)(l.a.mark((function e(){return l.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,G().then((function(e){s(e),C(!1),M("")})).catch((function(e){console.log(e.message),M(e.message),C(!1)}));case 2:case"end":return e.stop()}}),e)})))).apply(this,arguments)}!function(){e.apply(this,arguments)}()}),[]);return A?Object(b.jsx)(j,{}):I.length>0?Object(b.jsx)(m,{msg:I}):Object(b.jsxs)(b.Fragment,{children:[Object(b.jsx)(x,{showKalendarModal:F,setShowKalendarModal:D,selectedData:f,veranstaltungsIds:v}),Object(b.jsxs)("div",{className:F?"filter blur-lg":"",onClick:function(){return D(!1)},children:[Object(b.jsxs)("div",{className:"grid grid-rows-3 grid-rows-none gap-4 2xl:w-10/12 mb-4 xl:w-11/12",children:[Object(b.jsxs)("div",{className:"rounded-box p-4 bg-base-300 ",children:[Object(b.jsx)("h2",{className:"text-4xl mb-2",children:"H-BRS Kalendergenerator v1.0"}),Object(b.jsx)("p",{children:"Ich \xfcbernehme keine Haftung f\xfcr die Richtigkeit der generierten Daten, alles nach bestem Wissen und Gewissen. Fehler bitte an a@andrevr.de melden."}),Object(b.jsx)("p",{children:"Wenn's euch gef\xe4llt, empfehlt es euren Kommilitonen! \ud83d\ude01"})]}),Object(b.jsxs)("div",{className:"grid grid-cols-3 gap-4 rounded-box p-3 bg-base-300",children:[Object(b.jsx)("button",{onClick:function(e){return function(e){var n=[],t=T.current.api.getSelectedNodes().map((function(e){return e.data}));t.map((function(e){return n.push(e.id)})),p(t),k(n),e.stopPropagation(),M(""),D(!0)}(e)},className:"btn btn-lg w-full",children:"Hol dir deinen Kalender!"}),Object(b.jsx)("a",{href:"https://github.com/Hochgesand/H-BRSiCalGenerator",target:"_blank",rel:"noopener noreferrer",children:Object(b.jsx)("button",{className:"btn btn-lg w-full",children:"Gib mir einen Stern auf Github \u2764"})}),Object(b.jsx)("button",{className:"btn btn-lg w-full",onClick:function(){e.push("/FAQ")},children:"FAQ / HILFE!"})]})]}),Object(b.jsx)("div",{className:"ag-theme-alpine-dark rounded-box p-3 bg-base-300",style:{height:800},children:Object(b.jsxs)(g.AgGridReact,{rowData:r,enableRangeSelection:!0,rowSelection:"multiple",ref:T,rowMultiSelectWithClick:!0,onFirstDataRendered:function(){return T.current.api.sizeColumnsToFit()},children:[Object(b.jsx)(g.AgGridColumn,{field:"name",width:380,sortable:!0,filter:!0,checkboxSelection:!0,resizable:!0,headerName:"Veranstaltung",floatingFilter:!0}),Object(b.jsx)(g.AgGridColumn,{field:"studienGangSemester",width:400,sortable:!0,filter:!0,resizable:!0,floatingFilter:!0,headerName:"Fachbereich / Semester"}),Object(b.jsx)(g.AgGridColumn,{field:"prof",width:150,sortable:!0,filter:!0,resizable:!0,floatingFilter:!0})]})})]})]})}function w(){return Object(b.jsx)("div",{children:Object(b.jsx)(O,{})})}var v=function(){return Object(b.jsx)("div",{className:"App",children:Object(b.jsx)(w,{})})},k=function(e){e&&e instanceof Function&&t.e(3).then(t.bind(null,71)).then((function(n){var t=n.getCLS,a=n.getFID,r=n.getFCP,s=n.getLCP,i=n.getTTFB;t(e),a(e),r(e),s(e),i(e)}))},S=t(19);function N(){var e=Object(u.f)();return Object(b.jsxs)("div",{className:"p-5 flex-col gap-3",children:[Object(b.jsx)("button",{className:"btn btn-lg mb-4",onClick:function(){return e.goBack()},children:"Back"}),Object(b.jsxs)("div",{className:"rounded-box p-4 bg-base-300 flex-grow-0 w-3/4",children:[Object(b.jsx)("p",{className:"text-3xl",children:"Anleitung:"}),Object(b.jsx)("p",{className:"text-2xl",children:"Schritt 1:"}),Object(b.jsx)("p",{children:'W\xe4hle dir einfach alle Veranstaltungen aus die du besuchen m\xf6chtest. Du kannst die Tabelle nach belieben sortieren und mit dem Burgerbutton kannst du auch gezielt nach Keywords suchen. Wenn du alle Veranstaltungen ausgew\xe4hlt hast klicke auf "Hol dir deinen Kalender" ... '}),Object(b.jsx)("p",{className:"text-2xl",children:"Schritt 2: "}),Object(b.jsx)("p",{children:'Jetzt hast du zwei M\xf6glichkeiten: 1.: Klicke auf "Download calendar.ics" und importiere es wie in Beispiel 3 einfach selber am Rechner. 2.: Tippe deine E-Mail Adresse ein und lasse es dir bequem per E-Mail Schicken. Klicke daf\xfcr einfach nachdem du deine E-Mail Adresse eingetragen hast auf "Schick\'s per E-Mail". Damit kriegste dann die ics auf\'s Handy und kannst sie ggf. sogar noch einfacher importieren.'}),Object(b.jsx)("p",{className:"text-2xl",children:"Misc:"}),Object(b.jsx)("p",{children:"Packages und Technologien die ich benutzt habe:"}),Object(b.jsx)("p",{children:"Spring Boot (Java):"}),Object(b.jsxs)("ul",{children:[Object(b.jsx)("li",{children:" - Apache POI um Exceltabellen zu parsen"}),Object(b.jsx)("li",{children:" - ical4j um den Calender zu generieren"})]}),Object(b.jsx)("p",{children:"React:"}),Object(b.jsxs)("ul",{children:[Object(b.jsx)("li",{children:" - AGGrid f\xfcr die Tabelle, kein Bock gehabt selber Sortieralgorithmen zu implementieren"}),Object(b.jsx)("li",{children:" - Locker noch ca. 2000 Packages mehr die mit React gekommen sind, npm macht npm sachen"}),Object(b.jsx)("li",{children:" - TailwindCSS f\xfcr's CSS"})]}),Object(b.jsx)("p",{className:"text-2xl",children:"FAQ:"}),Object(b.jsx)("p",{children:"Q: Warum machste die App nicht responsive damit ich das auch bequem auf dem Handy nutzen kann?"}),Object(b.jsx)("p",{children:"A: Warum nutzt du die App nicht einfach auf dem Rechner und schickst dir den Stundenplan per E-Mail?"}),Object(b.jsx)("p",{children:"Q: Warum werden manche Veranstaltungen doppelt angezeigt?"}),Object(b.jsx)("p",{children:"A: Manche Veranstaltungen gibt es Fachbereich/Semester \xfcbergreifend und ich habe noch kein Ablauf im Backend implementiert die schaut ob die Eventuhrzeiten/R\xe4ume \xfcbereinstimmen damit ich gew\xe4hrleisten kann das die auch tats\xe4chlich die gleichen Veranstaltungen sind. Beispielsweise k\xf6nnte ja eine Veranstaltung f\xfcr Nachschreiber existieren und damit z.B. in einem anderen Raum stattfinden. W\xe4hlt daher immer die Veranstaltung f\xfcr euren Fachbereich/Semester."}),Object(b.jsx)("p",{children:"Q: Wie kriegst du die Stundenpl\xe4ne in die Anwendung importiert?"}),Object(b.jsx)("p",{children:"A: Ich zieh mir einfach die Stundenpl\xe4ne von Eva und aktualisiere Sie jeden Tag damit alle \xc4nderungen frisch auf meinem Backend liegen."}),Object(b.jsx)("p",{children:"Q: Welche Daten erhebst du?"}),Object(b.jsx)("p",{children:"A: Fast keine. Ich logge nichts und m\xf6chte das auch nicht. Die einzigen Dinge die ich speichere sind IP Adressen die die Api missbrauchen. Wie ich die erfasse erkl\xe4re ich weiter unten. Ich habe \xfcberlegt ob ich vielleicht Metriken dar\xfcber erfasse und vielleicht jedes Semesterende anonymisierte Statistiken bereitstelle (das erstellen eines Kalenders ist sowieso anonym solange man es ohne E-Mail macht). Das w\xe4re vielleicht interessant. Allerdings bin ich mir da noch nicht ganz so sicher ob ich das mache. Wenn ich daran arbeite, werde ich das hier vorher bekannt geben. Ich nutze keine Daten ohne vorher zu fragen."}),Object(b.jsx)("p",{children:"Q: Und wann werde ich jetzt genau geloggt?"}),Object(b.jsx)("p",{children:"A: Sobald ihr ein 429er Error bekommt (Too Many Requests) wird eure IP Adresse gespeichert. Wenn diese IP Adresse weiterhin versucht Requests zu schicken wird sie dauerhaft gebannt. Wenn das Problem \xf6fter auftritt werde ich die IP Adresse dem entsprechenden Anbieter melden. Daf\xfcr m\xfcsst ihr aber schon sehr aggressiv vorgehen. Wenn ihr blockiert werdet weil ihr spa\xdf dran habt meine Anwendung zu testen oder eure Hackerskills beweisen wollt, d\xfcrft ihr das gerne tun. Meldet euch bei mir ich hab damit keine Probleme, ganz im gegenteil, ich w\xfcrde mich sogar freuen :) Schickt mir einfach ne E-Mail an a@andrevr.de."})]}),Object(b.jsxs)("div",{className:"mt-5",children:[Object(b.jsx)("p",{className:"text-3xl",children:'Beispiel 1: "Ich bin bei Herr Berrendorf in EidiP und geh\xf6re der Gruppe D an.'}),Object(b.jsx)("img",{src:"/H-BRSiCalGenerator/FUiTCuS2BJ.gif",alt:""})]}),Object(b.jsxs)("div",{className:"mt-5",children:[Object(b.jsx)("p",{className:"text-3xl",children:'Beispiel 2: "Hilfe ich sehe nicht den ganzen Text!'}),Object(b.jsx)("img",{src:"/H-BRSiCalGenerator/aTkfaQusHi.gif",alt:""})]}),Object(b.jsxs)("div",{className:"mt-5",children:[Object(b.jsx)("p",{className:"text-3xl",children:'Beispiel 3: "Is ja alles sch\xf6n und toll, aber was mache ich jetzt mit dieser komischen calendar.ics Datei (Beispiel an Google Kalender)"'}),Object(b.jsx)("img",{src:"/H-BRSiCalGenerator/Z795yqtZO4.gif",alt:""})]})]})}i.a.render(Object(b.jsx)(r.a.StrictMode,{children:Object(b.jsx)(S.a,{children:Object(b.jsxs)(u.c,{children:[Object(b.jsx)(u.a,{exact:!0,path:"/FAQ",children:Object(b.jsx)(N,{})}),Object(b.jsx)(u.a,{path:"/",children:Object(b.jsx)(v,{})})]})})}),document.getElementById("root")),k()}},[[70,1,2]]]);
//# sourceMappingURL=main.5334c94f.chunk.js.map