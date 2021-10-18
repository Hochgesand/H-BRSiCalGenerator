package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Controller;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
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

    @Autowired
    private final RateLimiter rateLimiter;

    @Autowired
    CalenderGeneratorService calenderGeneratorService;
    @Autowired
    VeranstaltungsRepo veranstaltungsRepo;
    @Autowired
    EmailSendingService emailSendingService;

    public CalenderExportController(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

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
    public ResponseEntity getCalenderForSemester(@RequestBody VeranstaltungsIds veranstaltungsIds) throws IOException {
        boolean acc = rateLimiter.tryAcquire();
        if (acc){
            return new ResponseEntity(
                calenderGeneratorService.createCalender(veranstaltungsIds),
                HttpStatus.OK
            );
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
    }

    @RequestMapping(value = "/getVeranstaltungen", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getVeranstaltungen(){
        boolean acc = rateLimiter.tryAcquire();
        if (acc){
            List<Veranstaltung> veranstaltungen = (List<Veranstaltung>) veranstaltungsRepo.findAll();
            return new ResponseEntity<List<Veranstaltung>>(
                veranstaltungen,
                HttpStatus.OK
            );
        }

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
    }

    @RequestMapping(
            value = "/sememesteriCalEmail",
            method = {RequestMethod.POST},
            produces = "text/calender"
    )
    public ResponseEntity getCalenderOverEmail(@RequestBody VeranstaltungsIdsAndEmail veranstaltungsIdsAndEmail) throws MessagingException, IOException {
        boolean acc = rateLimiter.tryAcquire(3);
        if (acc){
            emailSendingService.getCalenderOverEmail(veranstaltungsIdsAndEmail);
            return new ResponseEntity<String>(
                    "E-Mail will be sent",
                    HttpStatus.OK
            );
        }

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
    }

}