package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain.Course;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Meeting;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    List<Meeting> findByIdIn(List<Long> id);

    List<Meeting> findAllByProfessorContaining(String professor);

    List<Meeting> findAllByCourse(Course course);

    Meeting findFirstByNameAndCourse(String name, Course course);

    List<Meeting> findAllByCourseAndSemester(Course selectedCourse, Integer semester);
}
