package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Controller;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.DBRepo.VeranstaltungsRepo;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.DAOObjects.VeranstaltungsIds;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.DAOObjects.VeranstaltungsIdsAndEmail;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.Veranstaltung;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Service.CalenderGeneratorService;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Service.EmailSendingService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CalenderExportController {

    RateLimiter rateLimiter = RateLimiter.create(0.2);

    @Autowired
    CalenderGeneratorService calenderGeneratorService;
    @Autowired
    VeranstaltungsRepo veranstaltungsRepo;
    @Autowired
    EmailSendingService emailSendingService;

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
        return calenderGeneratorService.createCalender(veranstaltungsIds);
    }

    @RequestMapping(value = "/getVeranstaltungen", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Veranstaltung> getVeranstaltungen(){
        List<Veranstaltung> veranstaltungen = (List<Veranstaltung>) veranstaltungsRepo.findAll();
        return veranstaltungen;
    }

    @RequestMapping(
            value = "/sememesteriCalEmail",
            method = {RequestMethod.POST},
            produces = "text/calender"
    )
    public String getCalenderOverEmail(@RequestBody VeranstaltungsIdsAndEmail veranstaltungsIdsAndEmail) throws MessagingException, IOException {
        emailSendingService.getCalenderOverEmail(veranstaltungsIdsAndEmail);

        return "E-Mail will be sent";
    }

}