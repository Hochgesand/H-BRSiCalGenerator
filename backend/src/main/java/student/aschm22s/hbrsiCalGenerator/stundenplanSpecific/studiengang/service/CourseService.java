package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.service;

import org.springframework.stereotype.Service;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain.Course;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.repository.CourseRepository;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAllStudiengaenge() {
        return courseRepository.findAll();
    }

    public Course findFirstByNameContaining(String name) {
        return courseRepository.findFirstByNameContaining(name);
    }

    public Course findByNameIfNotExistCreate(String name) {
        Course course = courseRepository.findFirstByNameContaining(name);
        if (course == null) {
            course = new Course();
            course.setName(name);
            courseRepository.save(course);
        }
        return course;
    }

    public List<Course> findAllByNameContaining(String substring) {
        return courseRepository.findAllByNameContaining(substring);
    }

    public Course findById(Long studiengang) {
        return courseRepository.findById(studiengang).orElse(new Course());
    }
}
