package student.aschm22s.hbrsiCalGenerator.calenderExport.controller;

import org.springframework.web.bind.annotation.*;
import student.aschm22s.hbrsiCalGenerator.calenderExport.service.CalenderExportService;
import student.aschm22s.hbrsiCalGenerator.email.service.EmailSendingService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.VeranstaltungsIdsAndEmailDTO;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.VeranstaltungsIdsDTO;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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
    public String getCalenderOverEmail(@RequestBody VeranstaltungsIdsAndEmailDTO veranstaltungsIdsAndEmailDTO) {
        if (Arrays.stream(blacklistedEMails).anyMatch(x -> veranstaltungsIdsAndEmailDTO.getEmail().equals(x))) {
            return "ne";
        }

        return emailSendingService.getCalenderOverEmail(veranstaltungsIdsAndEmailDTO);
    }

    @RequestMapping(
            value = "/sememesteriCal",
            method = {RequestMethod.POST},
            produces = "text/calender"
    )
    public byte[] getCalenderForSemester(@RequestBody VeranstaltungsIdsDTO veranstaltungsIdsDTO) throws IOException, NoSuchAlgorithmException {
        return calenderExportService.createCalender(veranstaltungsIdsDTO);
    }

    @RequestMapping(
            value = "/sememesteriCalAsCSV",
            method = {RequestMethod.POST},
            produces = "text/csv"
    )
    public String getCalenderForSemesterAsCSV(@RequestBody VeranstaltungsIdsDTO veranstaltungsIdsDTO) throws IOException {
        return calenderExportService.createCalenderAsCSV(veranstaltungsIdsDTO);
    }
}