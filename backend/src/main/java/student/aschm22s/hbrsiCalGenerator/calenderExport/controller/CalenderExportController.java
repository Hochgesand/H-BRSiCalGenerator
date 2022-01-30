package student.aschm22s.hbrsiCalGenerator.calenderExport.controller;

import org.springframework.web.bind.annotation.*;
import student.aschm22s.hbrsiCalGenerator.calenderExport.service.CalenderExportService;
import student.aschm22s.hbrsiCalGenerator.email.service.EmailSendingService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.VeranstaltungsIdsAndEmailDAO;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.VeranstaltungsIdsDAO;

import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/api/calender")
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
    public String getCalenderOverEmail(@RequestBody VeranstaltungsIdsAndEmailDAO veranstaltungsIdsAndEmailDAO) {
        if (Arrays.stream(blacklistedEMails).anyMatch(x -> veranstaltungsIdsAndEmailDAO.getEmail().equals(x))) {
            return "ne";
        }

        return emailSendingService.getCalenderOverEmail(veranstaltungsIdsAndEmailDAO);
    }

    @RequestMapping(
            value = "/sememesteriCal",
            method = {RequestMethod.POST},
            produces = "text/calender"
    )
    public byte[] getCalenderForSemester(@RequestBody VeranstaltungsIdsDAO veranstaltungsIdsDAO) throws IOException {
        return calenderExportService.createCalender(veranstaltungsIdsDAO);
    }

    @RequestMapping(
            value = "/sememesteriCalAsCSV",
            method = {RequestMethod.POST},
            produces = "text/csv"
    )
    public String getCalenderForSemesterAsCSV(@RequestBody VeranstaltungsIdsDAO veranstaltungsIdsDAO) throws IOException {
        return calenderExportService.createCalenderAsCSV(veranstaltungsIdsDAO);
    }
}