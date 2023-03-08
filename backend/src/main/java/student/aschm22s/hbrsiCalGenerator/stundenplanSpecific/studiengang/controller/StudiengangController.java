package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain.Course;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/api/studiengang")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StudiengangController {
    private final CourseService courseService;

    public StudiengangController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping(
            value = "/getStudiengaenge",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Course> getStudiengaenge() {
        return courseService.findAllStudiengaenge();
    }
}
