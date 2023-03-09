package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.CourseEntry;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Meeting;

import java.util.List;

@Repository
public interface TimetableRepository extends CrudRepository<CourseEntry, Integer> {
    List<CourseEntry> findByMeeting(Meeting meeting);
    List<CourseEntry> findAllByMeetingOrderByStart(Meeting meeting);

    List<CourseEntry> findAllByMeeting(Meeting meeting);
}
