package student.aschm22s.hbrsiCalGenerator.calenderExport.controller;

import org.springframework.web.bind.annotation.*;
import student.aschm22s.hbrsiCalGenerator.calenderExport.service.CalenderExportService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.MeetingIdsDTO;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@RestController
public class CalenderAboController {
    private final CalenderExportService calenderExportService;

    public CalenderAboController(CalenderExportService calenderExportService) {
        this.calenderExportService = calenderExportService;
    }

    @RequestMapping(
            value = "a.ical",
            method = {RequestMethod.GET},
            produces = "text/calender"
    )
    public byte[] getCalenderForSemester(@RequestParam Long id) throws IOException, NoSuchAlgorithmException {
        var list = new ArrayList<Long>();
        list.add(id);
        return calenderExportService.createCalender(new MeetingIdsDTO(list, false));
    }
}
