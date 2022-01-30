package student.aschm22s.hbrsiCalGenerator.veranstaltung.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import student.aschm22s.hbrsiCalGenerator.veranstaltung.domain.Studiengang;
import student.aschm22s.hbrsiCalGenerator.veranstaltung.domain.Veranstaltung;
import student.aschm22s.hbrsiCalGenerator.veranstaltung.service.VeranstaltungsService;

import java.util.List;

@RestController
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
            value = "/getStudiengaenge",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Studiengang> getStudiengaenge() {
        return veranstaltungsService.findAllStudiengaenge();
    }
}
