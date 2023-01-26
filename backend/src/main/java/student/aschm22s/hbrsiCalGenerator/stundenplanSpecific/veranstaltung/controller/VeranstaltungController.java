package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Veranstaltung;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.service.VeranstaltungsService;

import java.util.List;

@RestController
@RequestMapping("/api/veranstaltung")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VeranstaltungController {

    private final VeranstaltungsService veranstaltungsService;

    public VeranstaltungController(VeranstaltungsService veranstaltungsService) {
        this.veranstaltungsService = veranstaltungsService;
    }

    @RequestMapping(
            value = "/getVeranstaltungen",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Veranstaltung> getVeranstaltungen() {
        return veranstaltungsService.findAll();
    }

    @RequestMapping(
            value = "/getVeranstaltungByStudiengang",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Veranstaltung> getVeranstaltungByStudiengang(@RequestParam("studiengang") String studiengang) {
        return veranstaltungsService.findAllByStudiengang(studiengang);
    }

    @RequestMapping(
            value = "/getVeranstaltungByStudiengangId",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Veranstaltung> getVeranstaltungByStudiengangId(@RequestParam("studiengang") String studiengang) {
        return veranstaltungsService.findAllByStudiengangId(studiengang);
    }

    @RequestMapping(
            value = "/getVeranstaltungByProfessor",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Veranstaltung> getVeranstaltungByProfessor(@RequestParam("studiengang") String prof) {
        return veranstaltungsService.findAllByProfname(prof);
    }
}
