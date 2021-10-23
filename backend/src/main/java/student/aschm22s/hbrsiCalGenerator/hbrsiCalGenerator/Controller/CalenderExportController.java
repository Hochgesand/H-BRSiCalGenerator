package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.DBRepo.VeranstaltungsRepo;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.DAOObjects.VeranstaltungsIds;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.DAOObjects.VeranstaltungsIdsAndEmail;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.Veranstaltung;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Service.CalenderGeneratorService;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Service.EmailSendingService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CalenderExportController {

    @Autowired
    CalenderGeneratorService calenderGeneratorService;
    @Autowired
    VeranstaltungsRepo veranstaltungsRepo;
    @Autowired
    EmailSendingService emailSendingService;

    String blacklistedEMails[] = new String[]{"a@andrevr.de", "moin@meister.ovh"};

    class veranstaltungsIds {
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

    @RequestMapping(value = "/getVeranstaltungen", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Object> getVeranstaltungen() {
        List<Veranstaltung> veranstaltungen = (List<Veranstaltung>) veranstaltungsRepo.findAll();
        return new ResponseEntity<List<Veranstaltung>>(
                veranstaltungen,
                HttpStatus.OK
        );
    }

    @RequestMapping(
            value = "/sememesteriCalEmail",
            method = {RequestMethod.POST},
            produces = "text/calender"
    )
    public ResponseEntity<? extends Object> getCalenderOverEmail(@RequestBody VeranstaltungsIdsAndEmail veranstaltungsIdsAndEmail) throws MessagingException, IOException {
        if (Arrays.stream(blacklistedEMails).anyMatch(x -> veranstaltungsIdsAndEmail.getEmail().equals(x))) {
            return new ResponseEntity<String>(
                    "Fühlst dich wohl witzig du Penner.",
                    HttpStatus.OK
            );
        }
        emailSendingService.getCalenderOverEmail(veranstaltungsIdsAndEmail);
        return new ResponseEntity<String>(
                "E-Mail will be sent",
                HttpStatus.OK
        );
    }

    @RequestMapping(
            value = "/sememesteriCal",
            method = {RequestMethod.POST},
            produces = "text/calender"
    )
    public ResponseEntity<? extends Object> getCalenderForSemester(@RequestBody VeranstaltungsIds veranstaltungsIds) throws IOException {
        return new ResponseEntity<>(
                calenderGeneratorService.createCalender(veranstaltungsIds),
                HttpStatus.OK
        );

    }


    @RequestMapping(
            value = "/sememesteriCalAsCSV",
            method = {RequestMethod.POST},
            produces = "text/csv"
    )
    public ResponseEntity<? extends Object> getCalenderForSemesterAsCSV(@RequestBody VeranstaltungsIds veranstaltungsIds) throws IOException {
        return new ResponseEntity<>(
                calenderGeneratorService.createCalenderAsCSV(veranstaltungsIds),
                HttpStatus.OK
        );

    }
}