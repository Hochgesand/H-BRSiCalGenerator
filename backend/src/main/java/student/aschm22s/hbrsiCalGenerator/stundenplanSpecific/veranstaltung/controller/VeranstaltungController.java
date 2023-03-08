package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Meeting;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.service.MeetingService;

import java.util.List;

@RestController
@RequestMapping("/api/veranstaltung")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VeranstaltungController {

    private final MeetingService meetingService;

    public VeranstaltungController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @RequestMapping(
            value = "/getVeranstaltungen",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Meeting> getVeranstaltungen() {
        return meetingService.findAll();
    }

    @RequestMapping(
            value = "/getVeranstaltungByStudiengang",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Meeting> getVeranstaltungByStudiengang(@RequestParam("studiengang") String studiengang) {
        return meetingService.findAllByStudiengang(studiengang);
    }

    @RequestMapping(
            value = "/getVeranstaltungByStudiengangId",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Meeting> getVeranstaltungByStudiengangId(@RequestParam("studiengang") Long studiengang) {
        return meetingService.findAllByStudiengangId(studiengang);
    }

    @RequestMapping(
            value = "/getVeranstaltungByProfessor",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Meeting> getVeranstaltungByProfessor(@RequestParam("studiengang") String prof) {
        return meetingService.findAllByProfessorName(prof);
    }
}
