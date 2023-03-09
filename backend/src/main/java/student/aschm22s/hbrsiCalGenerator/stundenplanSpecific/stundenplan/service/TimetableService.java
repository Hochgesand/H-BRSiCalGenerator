package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.service;

import org.springframework.stereotype.Service;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.CourseEntry;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.repository.TimetableRepository;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Meeting;

import java.util.List;

@Service
public class TimetableService {
    private final TimetableRepository timetableRepository;

    public TimetableService(TimetableRepository timetableRepository) {
        this.timetableRepository = timetableRepository;
    }

    public List<CourseEntry> findByVeranstaltung(Meeting x) {
        return timetableRepository.findByMeeting(x);
    }

    public List<CourseEntry> findAllByVeranstaltung(Meeting meeting) {
        return timetableRepository.findAllByMeeting(meeting);
    }

    public void deleteAll(List<CourseEntry> stundenplanEintraege) {
        timetableRepository.deleteAll(stundenplanEintraege);
    }

    public List<CourseEntry> findAll() {
        return (List<CourseEntry>) timetableRepository.findAll();
    }

    public CourseEntry save(CourseEntry newCourseEntry) {
        return timetableRepository.save(newCourseEntry);
    }

    public Iterable<CourseEntry> saveAll(Iterable<CourseEntry> stundenplanEintragList) {
        return timetableRepository.saveAll(stundenplanEintragList);
    }

    public List<CourseEntry> findAllByMeetingOrderByStart(Meeting meeting){
        return timetableRepository.findAllByMeetingOrderByStart(meeting);
    }
}
