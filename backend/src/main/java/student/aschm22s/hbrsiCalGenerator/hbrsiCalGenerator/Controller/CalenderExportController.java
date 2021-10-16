package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Controller;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.DBRepo.VeranstaltungsRepo;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.DAORecieveObjects.VeranstaltungsIds;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.Veranstaltung;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Service.CalenderGeneratorService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CalenderExportController {

    @Autowired
    CalenderGeneratorService calenderGeneratorService;
    @Autowired
    VeranstaltungsRepo veranstaltungsRepo;

    class veranstaltungsIds{
        List<Integer> veranstaltungsIds;

        public List<Integer> getVeranstaltungsIds() {
            return veranstaltungsIds;
        }

        public void setVeranstaltungsIds(List<Integer> veranstaltungsIds) {
            this.veranstaltungsIds = veranstaltungsIds;
        }

        public veranstaltungsIds(List<Integer> veranstaltungsIds) {
            this.veranstaltungsIds = veranstaltungsIds;
        }
    }

    @RequestMapping(
            value = "/sememesteriCal",
            method = {RequestMethod.POST},
            produces = "text/calender"
    )
    public byte[] getCalenderForSemester(@RequestBody VeranstaltungsIds veranstaltungsIds) throws IOException {
        if (veranstaltungsIds.getVeranstaltungsIds().size() == 0)
            return new byte[0x00];

        Calendar calenderToReturn = calenderGeneratorService
                .createCalenderForVeranstaltungen(veranstaltungsIds.getVeranstaltungsIds());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(calenderToReturn, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

    @RequestMapping(value = "/getVeranstaltungen", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Veranstaltung> getVeranstaltungen(){
        List<Veranstaltung> veranstaltungen = (List<Veranstaltung>) veranstaltungsRepo.findAll();

        return veranstaltungen;
    }

}