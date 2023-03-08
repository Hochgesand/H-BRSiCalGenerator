package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.service;

import org.springframework.stereotype.Service;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain.Course;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.repository.StudiengangsRepository;

import java.util.List;

@Service
public class CourseService {
    private final StudiengangsRepository studiengangsRepository;

    public CourseService(StudiengangsRepository studiengangsRepository) {
        this.studiengangsRepository = studiengangsRepository;
    }

    public List<Course> findAllStudiengaenge() {
        return studiengangsRepository.findAll();
    }

    public Course findFirstByNameContaining(String name) {
        return studiengangsRepository.findFirstByNameContaining(name);
    }

    public Course findByNameIfNotExistCreate(String name) {
        Course course = studiengangsRepository.findFirstByNameContaining(name);
        if (course == null) {
            course = new Course();
            course.setName(name);
            studiengangsRepository.save(course);
        }
        return course;
    }

    public List<Course> findAllByNameContaining(String substring) {
        return studiengangsRepository.findAllByNameContaining(substring);
    }

    public Course findById(Long studiengang) {
        return studiengangsRepository.findById(studiengang).orElse(new Course());
    }
}
