package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain.Studiengang;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.service.StudiengangService;

import java.util.List;

@RestController
@RequestMapping("/api/studiengang")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StudiengangController {
    private final StudiengangService studiengangService;

    public StudiengangController(StudiengangService studiengangService) {
        this.studiengangService = studiengangService;
    }

    @RequestMapping(
            value = "/getStudiengaenge",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Studiengang> getStudiengaenge() {
        return studiengangService.findAllStudiengaenge();
    }
}
