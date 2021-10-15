package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Controller;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.DBRepo.VeranstaltungsRepo;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.Veranstaltung;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Service.CalenderGeneratorService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class CalenderExportController {

    @Autowired
    CalenderGeneratorService calenderGeneratorService;
    @Autowired
    VeranstaltungsRepo veranstaltungsRepo;

    @RequestMapping(
            value = "/sememesteriCal",
            method = {RequestMethod.POST},
            produces = "text/calender"
    )
    public byte[] getCalenderForSemester(@RequestParam(value = "veranstaltungsIds") List<Integer> veranstaltungsIds) throws IOException {
        Calendar calenderToReturn = calenderGeneratorService
                .createCalenderForVeranstaltungen(veranstaltungsIds);

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