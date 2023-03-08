package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain.Course;

import java.util.List;

public interface StudiengangsRepository extends JpaRepository<Course, Long> {
    Course findFirstByNameContaining(String name);

    List<Course> findAllByNameContaining(String name);
}
