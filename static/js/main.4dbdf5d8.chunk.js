(this["webpackJsonphbrs-ical-generator-frontend"]=this["webpackJsonphbrs-ical-generator-frontend"]||[]).push([[0],{21:function(e,t,n){},41:function(e,t,n){},70:function(e,t,n){"use strict";n.r(t);var a=n(1),r=n.n(a),i=n(17),s=n.n(i),c=(n(21),n(41),n(9)),l=n.n(c),d=n(13),u=n(11),h=n(2),o="https://moin.meister.ovh:8443";var b=n(0);function j(){return Object(b.jsx)("button",{className:"btn btn-lg loading",children:"loading"})}function m(e){return Object(b.jsx)("div",{className:"alert alert-error mb-4",children:Object(b.jsxs)("div",{className:"flex-1",children:[Object(b.jsx)("svg",{xmlns:"http://www.w3.org/2000/svg",fill:"none",viewBox:"0 0 24 24",className:"w-6 h-6 mx-2 stroke-current",children:Object(b.jsx)("path",{"stroke-linecap":"round","stroke-linejoin":"round","stroke-width":"2",d:"M18.364 18.364A9 9 0 005.636 5.636m12.728 12.728A9 9 0 015.636 5.636m12.728 12.728L5.636 5.636"})}),Object(b.jsx)("label",{children:e.msg})]})})}var g=n(12);n(31),n(32);var p=n(34);function x(e){var t=Object(a.useState)(""),n=Object(u.a)(t,2),r=n[0],i=n[1],s=Object(a.useState)(""),c=Object(u.a)(s,2),h=c[0],j=c[1],x=Object(a.useState)(!1),f=Object(u.a)(x,2),O=f[0],w=f[1],v=Object(a.useRef)(null),k=function(e){var t=e.path,n=e.veranstaltungsIds;return{getCalendar:function(){return fetch(t,{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({veranstaltungsIds:n})}).then(function(){var e=Object(d.a)(l.a.mark((function e(t){return l.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(t.ok){e.next=2;break}throw Error("Could not fetch data");case 2:return e.abrupt("return",t.blob());case 3:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}())}}}({path:"".concat(o,"/sememesteriCal"),veranstaltungsIds:e.veranstaltungsIds}),S=k.getCalendar,N=function(e){var t=e.path,n=e.veranstaltungsIds,a=e.email;return{getCalendarEmailResponse:function(){return fetch(t,{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({veranstaltungsIds:n,email:a})}).then(function(){var e=Object(d.a)(l.a.mark((function e(t){return l.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(t.ok){e.next=2;break}throw Error("Could not send E-Mail");case 2:return e.abrupt("return",t);case 3:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}())}}}({path:"".concat(o,"/sememesteriCalEmail"),veranstaltungsIds:e.veranstaltungsIds,email:r}),C=N.getCalendarEmailResponse;return Object(b.jsx)(b.Fragment,{children:e.showKalendarModal?Object(b.jsx)("div",{className:"container flex mx-auto z-10 absolute inset-0 justify-center rounded-box h-screen",children:Object(b.jsxs)("div",{className:"m-auto rounded-box bg-base-300 w-3/4 xl:w-2/3 2xl:1 h-3/4 flex-none",children:[Object(b.jsxs)("div",{className:"h-30 p-4",children:[h.length>0?Object(b.jsx)(m,{msg:h}):null,Object(b.jsx)("button",{className:"btn btn-lg w-full mb-4 ".concat(O?"loading":null),type:"submit",disabled:0===e.veranstaltungsIds.length||O,onClick:function(){w(!0);S().then((function(t){w(!1),Object(p.saveAs)(t,"calendar.ics"),e.setShowKalendarModal(!1)})).catch((function(e){j(e.message),w(!1)}))},children:"Download calendar.ics"}),Object(b.jsxs)("div",{className:"rounded-box bg-base-300 grid grid-cols-2 gap-4 mb-4 w-auto",children:[Object(b.jsx)("input",{className:"appearance-none w-full bg-base-200 text-white border border-white rounded py-4 px-4 leading-tight focus:outline-none focus:bg-base-400 text-2xl",id:"grid-first-email",type:"text",placeholder:"E-Mail",onChange:function(e){return i(e.target.value)}}),Object(b.jsx)("button",{className:"btn btn-lg w-full ".concat(O?"loading":null),type:"submit",disabled:0===e.veranstaltungsIds.length||O||0===r.length,onClick:function(){w(!0);C().then((function(t){w(!1),alert("Deine Email wurde rausgeschickt^^ Wenn du keine E-Mail bekommen hast, versuche es erneut. Vergiss aber nicht das ich dich nach zu vielen Versuchen f\xfcr eine gewisse Zeit blockieren werde!"),e.setShowKalendarModal(!1)})).catch((function(e){j(e.message),w(!1)}))},children:"Schick's per E-Mail"})]}),Object(b.jsx)("button",{className:"btn btn-lg w-full mb-4",onClick:function(){return e.setShowKalendarModal(!1)},disabled:O,children:"Abbrechen"})]}),Object(b.jsx)("p",{className:"text-3xl ml-4",children:"Ausgew\xe4hlte Module/Veranstaltungen"}),Object(b.jsx)("div",{className:"ag-theme-alpine-dark rounded-box p-4 bg-base-300",style:{height:700},children:Object(b.jsxs)(g.AgGridReact,{ref:v,rowData:e.selectedData,onFirstDataRendered:function(){return v.current.api.sizeColumnsToFit()},children:[Object(b.jsx)(g.AgGridColumn,{field:"name",width:380,headerName:"Veranstaltung"}),Object(b.jsx)(g.AgGridColumn,{field:"studienGangSemester",width:400,headerName:"Fachbereich / Semester"})]})})]})}):null})}function f(){var e=Object(h.f)(),t=Object(a.useState)([]),n=Object(u.a)(t,2),r=n[0],i=n[1],s=Object(a.useState)([]),c=Object(u.a)(s,2),p=c[0],f=c[1],O=Object(a.useState)([]),w=Object(u.a)(O,2),v=w[0],k=w[1],S=Object(a.useState)(!0),N=Object(u.a)(S,2),C=N[0],A=N[1],B=Object(a.useState)(""),F=Object(u.a)(B,2),y=F[0],E=F[1],z=Object(a.useState)(!1),M=Object(u.a)(z,2),R=M[0],G=M[1],I=function(e){var t=e.path;return{getData:function(){return fetch(t,{method:"get",headers:void 0}).then(function(){var e=Object(d.a)(l.a.mark((function e(t){return l.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(t.ok){e.next=2;break}throw Error("Could not fetch data");case 2:return e.abrupt("return",t.json());case 3:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}())}}}({path:"".concat(o,"/getVeranstaltungen")}),D=I.getData,H=Object(a.useRef)(null);Object(a.useEffect)((function(){function e(){return(e=Object(d.a)(l.a.mark((function e(){return l.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,D().then((function(e){i(e),A(!1)})).catch((function(e){E(e.message),A(!1)}));case 2:case"end":return e.stop()}}),e)})))).apply(this,arguments)}!function(){e.apply(this,arguments)}()}),[]);return C?Object(b.jsx)(j,{}):y.length>0?Object(b.jsx)(m,{msg:y}):Object(b.jsxs)(b.Fragment,{children:[Object(b.jsx)(x,{showKalendarModal:R,setShowKalendarModal:G,selectedData:p,veranstaltungsIds:v}),Object(b.jsxs)("div",{className:R?"filter blur-lg":"",children:[Object(b.jsxs)("div",{className:"grid grid-rows-3 grid-rows-none gap-4 2xl:w-10/12 mb-4 xl:w-11/12",children:[Object(b.jsxs)("div",{className:"rounded-box p-4 bg-base-300 ",children:[Object(b.jsx)("h2",{className:"text-4xl mb-2",children:"H-BRS iCal Kalendergenerator"}),Object(b.jsx)("p",{children:"Die Seite befindet sich noch in der Beta Phase, Fehler bitte melden! (a@andrevr.de)"}),Object(b.jsx)("p",{children:"Ich \xfcbernehme keine Haftung f\xfcr die Richtigkeit der generierten Daten, alles nach bestem Wissen und Gewissen."}),Object(b.jsx)("p",{children:"Stundenplanstand 17.10.2021 / 20:32"})]}),Object(b.jsxs)("div",{className:"grid grid-cols-3 gap-4 rounded-box p-3 bg-base-300",children:[Object(b.jsx)("button",{onClick:function(){var e=[],t=H.current.api.getSelectedNodes().map((function(e){return e.data}));t.map((function(t){return e.push(t.id)})),f(t),k(e),G(!0)},className:"btn btn-lg w-full",children:"Hol dir deinen Kalender!"}),Object(b.jsx)("a",{href:"https://github.com/Hochgesand/H-BRSiCalGenerator",target:"_blank",rel:"noopener noreferrer",children:Object(b.jsx)("button",{className:"btn btn-lg w-full",children:"Gib mir einen Stern auf Github \u2764"})}),Object(b.jsx)("button",{className:"btn btn-lg w-full",onClick:function(){e.push("/FAQ")},children:"HILFE!"})]})]}),Object(b.jsx)("div",{className:"ag-theme-alpine-dark rounded-box p-3 bg-base-300",style:{height:800},children:Object(b.jsxs)(g.AgGridReact,{rowData:r,enableRangeSelection:!0,rowSelection:"multiple",ref:H,rowMultiSelectWithClick:!0,onFirstDataRendered:function(){return H.current.api.sizeColumnsToFit()},children:[Object(b.jsx)(g.AgGridColumn,{field:"name",width:380,sortable:!0,filter:!0,checkboxSelection:!0,resizable:!0,headerName:"Veranstaltung",floatingFilter:!0}),Object(b.jsx)(g.AgGridColumn,{field:"studienGangSemester",width:400,sortable:!0,filter:!0,resizable:!0,floatingFilter:!0,headerName:"Fachbereich / Semester"}),Object(b.jsx)(g.AgGridColumn,{field:"prof",width:150,sortable:!0,filter:!0,resizable:!0,floatingFilter:!0})]})})]})]})}function O(){return Object(b.jsx)("div",{children:Object(b.jsx)(f,{})})}var w=function(){return Object(b.jsx)("div",{className:"App",children:Object(b.jsx)(O,{})})},v=function(e){e&&e instanceof Function&&n.e(3).then(n.bind(null,71)).then((function(t){var n=t.getCLS,a=t.getFID,r=t.getFCP,i=t.getLCP,s=t.getTTFB;n(e),a(e),r(e),i(e),s(e)}))},k=n(19);function S(){var e=Object(h.f)();return Object(b.jsxs)("div",{className:"p-5 flex-col gap-3",children:[Object(b.jsx)("button",{className:"btn btn-lg mb-4",onClick:function(){return e.goBack()},children:"Back"}),Object(b.jsxs)("div",{className:"rounded-box p-4 bg-base-300 flex-grow-0 w-1/2",children:[Object(b.jsx)("p",{className:"text-3xl",children:"Anleitung:"}),Object(b.jsx)("p",{className:"text-2xl",children:"Schritt 1:"}),Object(b.jsx)("p",{children:'W\xe4hle dir einfach alle Veranstaltungen aus die du besuchen m\xf6chtest. Du kannst die Tabelle nach belieben sortieren und mit dem Burgerbutton kannst du auch gezielt nach Keywords suchen. Wenn du alle Veranstaltungen ausgew\xe4hlt hast klicke auf "Hol dir deinen Kalender" ... '}),Object(b.jsx)("p",{className:"text-2xl",children:"Schritt 2: "}),Object(b.jsx)("p",{children:'Jetzt hast du zwei M\xf6glichkeiten: 1.: Klicke auf "Download calendar.ics" und importiere es wie in Beispiel 3 einfach selber am Rechner. 2.: Tippe deine E-Mail Adresse ein und lasse es dir bequem per E-Mail Schicken. Klicke daf\xfcr einfach nachdem du deine E-Mail Adresse eingetragen hast auf "Schick\'s per E-Mail". Damit kriegste dann die ics auf\'s Handy und kannst sie ggf. sogar noch einfacher importieren.'}),Object(b.jsx)("p",{className:"text-2xl",children:"Misc:"}),Object(b.jsx)("p",{children:"Packages und Technologien die ich benutzt habe:"}),Object(b.jsx)("p",{children:"Spring Boot (Java):"}),Object(b.jsxs)("ul",{children:[Object(b.jsx)("li",{children:" - Apache POI um Exceltabellen zu parsen"}),Object(b.jsx)("li",{children:" - ical4j um den Calender zu generieren"})]}),Object(b.jsx)("p",{children:"React:"}),Object(b.jsxs)("ul",{children:[Object(b.jsx)("li",{children:" - AGGrid f\xfcr die Tabelle, kein Bock gehabt selber Sortieralgorithmen zu implementieren"}),Object(b.jsx)("li",{children:" - Locker noch ca. 2000 Packages mehr die mit React gekommen sind, npm macht npm sachen"})]}),Object(b.jsx)("p",{className:"text-2xl",children:"FAQ:"}),Object(b.jsx)("p",{children:"Q: Warum machste die App nicht responsive damit ich das auch bequem auf dem Handy nutzen kann?"}),Object(b.jsx)("p",{children:"A: Warum nutzt du die App nicht einfach auf dem Rechner?"}),Object(b.jsx)("p",{children:"Q: Warum werden manche Veranstaltungen doppelt angezeigt?"}),Object(b.jsx)("p",{children:"A: Manche Veranstaltungen gibt es Fachbereich/Semester \xfcbergreifend und ich habe noch kein Ablauf im Backend implementiert die schaut ob die Eventuhrzeiten/R\xe4ume \xfcbereinstimmen damit ich gew\xe4hrleisten kann das die auch tats\xe4chlich die gleichen Veranstaltungen sind. Beispielsweise k\xf6nnte ja eine Veranstaltung f\xfcr Nachschreiber existieren und damit z.B. in einem anderen Raum stattfinden. W\xe4hlt daher immer die Veranstaltung f\xfcr euren Fachbereich/Semester."}),Object(b.jsx)("p",{children:"Q: Wie kriegst du die Stundenpl\xe4ne in die Anwendung importiert?"}),Object(b.jsx)("p",{children:"A: Aktuell importiere ich die Stundenpl\xe4ne noch per Hand. Ich arbeite daran die Stundenpl\xe4ne von Eva zu webscrapen. Hei\xdft wenn sich die Stundenpl\xe4ne aktualisieren kann es passieren das die hier noch nicht uptodate sind. Wenn ihr da mehr wisst, sagt mir gerne bescheid und ich aktualisiere alle Stundenpl\xe4ne. Sobald das Webscraping implementiert ist wird dieser FAQ Eintrag verschwinden."})]}),Object(b.jsxs)("div",{className:"mt-5",children:[Object(b.jsx)("p",{className:"text-3xl",children:'Beispiel 1: "Ich bin bei Herr Berrendorf in EidiP und geh\xf6re der Gruppe D an.'}),Object(b.jsx)("img",{src:"/H-BRSiCalGenerator/FUiTCuS2BJ.gif",alt:""})]}),Object(b.jsxs)("div",{className:"mt-5",children:[Object(b.jsx)("p",{className:"text-3xl",children:'Beispiel 2: "Hilfe ich sehe nicht den ganzen Text!'}),Object(b.jsx)("img",{src:"/H-BRSiCalGenerator/aTkfaQusHi.gif",alt:""})]}),Object(b.jsxs)("div",{className:"mt-5",children:[Object(b.jsx)("p",{className:"text-3xl",children:'Beispiel 3: "Is ja alles sch\xf6n und toll, aber was mache ich jetzt mit dieser komischen calendar.ics Datei (Beispiel an Google Kalender)"'}),Object(b.jsx)("img",{src:"/H-BRSiCalGenerator/Z795yqtZO4.gif",alt:""})]})]})}s.a.render(Object(b.jsx)(r.a.StrictMode,{children:Object(b.jsx)(k.a,{children:Object(b.jsxs)(h.c,{children:[Object(b.jsx)(h.a,{exact:!0,path:"/FAQ",children:Object(b.jsx)(S,{})}),Object(b.jsx)(h.a,{path:"/",children:Object(b.jsx)(w,{})})]})})}),document.getElementById("root")),v()}},[[70,1,2]]]);
//# sourceMappingURL=main.4dbdf5d8.chunk.js.map