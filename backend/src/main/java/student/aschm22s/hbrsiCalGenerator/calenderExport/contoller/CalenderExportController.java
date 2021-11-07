package student.aschm22s.hbrsiCalGenerator.calenderExport.contoller;

import org.springframework.web.bind.annotation.*;
import student.aschm22s.hbrsiCalGenerator.calenderExport.service.CalenderExportService;
import student.aschm22s.hbrsiCalGenerator.email.service.EmailSendingService;
import student.aschm22s.hbrsiCalGenerator.veranstaltung.domain.VeranstaltungsIds;
import student.aschm22s.hbrsiCalGenerator.veranstaltung.domain.VeranstaltungsIdsAndEmail;

import java.io.IOException;
import java.util.Arrays;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CalenderExportController {

    private final CalenderExportService calenderExportService;
    private final EmailSendingService emailSendingService;

    String[] blacklistedEMails = new String[]{"a@andrevr.de", "moin@meister.ovh"};

    public CalenderExportController(CalenderExportService calenderExportService, EmailSendingService emailSendingService) {
        this.calenderExportService = calenderExportService;
        this.emailSendingService = emailSendingService;
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
        return calenderExportService.createCalender(veranstaltungsIds);
    }

    @RequestMapping(
            value = "/sememesteriCalAsCSV",
            method = {RequestMethod.POST},
            produces = "text/csv"
    )
    public String getCalenderForSemesterAsCSV(@RequestBody VeranstaltungsIds veranstaltungsIds) throws IOException {
        return calenderExportService.createCalenderAsCSV(veranstaltungsIds);
    }
}