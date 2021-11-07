package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.models.DAOObjects.VeranstaltungsIds;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.models.DAOObjects.VeranstaltungsIdsAndEmail;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.models.Veranstaltung;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.repository.VeranstaltungsRepository;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.service.CalenderGeneratorService;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.service.EmailSendingService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CalenderExportController {

    private final CalenderGeneratorService calenderGeneratorService;
    private final VeranstaltungsRepository veranstaltungsRepo;
    private final EmailSendingService emailSendingService;

    String[] blacklistedEMails = new String[]{"a@andrevr.de", "moin@meister.ovh"};

    public CalenderExportController(
            CalenderGeneratorService calenderGeneratorService,
            VeranstaltungsRepository veranstaltungsRepo,
            EmailSendingService emailSendingService) {
        this.calenderGeneratorService = calenderGeneratorService;
        this.veranstaltungsRepo = veranstaltungsRepo;
        this.emailSendingService = emailSendingService;
    }

    @RequestMapping(value = "/getVeranstaltungen", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Veranstaltung> getVeranstaltungen() {
        return veranstaltungsRepo.findAll();
    }

    @RequestMapping(
            value = "/sememesteriCalEmail",
            method = {RequestMethod.POST},
            produces = "text/calender"
    )
    public String getCalenderOverEmail(@RequestBody VeranstaltungsIdsAndEmail veranstaltungsIdsAndEmail) {
        if (Arrays.stream(blacklistedEMails).anyMatch(x -> veranstaltungsIdsAndEmail.getEmail().equals(x))) {
            return "Fühlst dich wohl witzig du Penner.";
        }

        return emailSendingService.getCalenderOverEmail(veranstaltungsIdsAndEmail);
    }

    @RequestMapping(
            value = "/sememesteriCal",
            method = {RequestMethod.POST},
            produces = "text/calender"
    )
    public byte[] getCalenderForSemester(@RequestBody VeranstaltungsIds veranstaltungsIds) throws IOException {
        return calenderGeneratorService.createCalender(veranstaltungsIds);
    }

    @RequestMapping(
            value = "/sememesteriCalAsCSV",
            method = {RequestMethod.POST},
            produces = "text/csv"
    )
    public String getCalenderForSemesterAsCSV(@RequestBody VeranstaltungsIds veranstaltungsIds) throws IOException {
        return calenderGeneratorService.createCalenderAsCSV(veranstaltungsIds);
    }

    class veranstaltungsIds {
        List<Integer> veranstaltungsIds;

        public veranstaltungsIds(List<Integer> veranstaltungsIds) {
            this.veranstaltungsIds = veranstaltungsIds;
        }

        public List<Integer> getVeranstaltungsIds() {
            return veranstaltungsIds;
        }

        public void setVeranstaltungsIds(List<Integer> veranstaltungsIds) {
            this.veranstaltungsIds = veranstaltungsIds;
        }
    }
}