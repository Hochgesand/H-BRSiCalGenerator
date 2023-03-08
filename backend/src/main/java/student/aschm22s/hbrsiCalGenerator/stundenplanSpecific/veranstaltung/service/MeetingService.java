package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.service;

import org.springframework.stereotype.Service;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain.Course;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.service.CourseService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.CourseEntry;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.service.TimetableService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Meeting;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.repository.MeetingRepository;

import java.util.List;

@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;

    private final TimetableService timetableService;
    private final CourseService courseService;

    public MeetingService(MeetingRepository meetingRepository, TimetableService timetableService, CourseService courseService) {
        this.meetingRepository = meetingRepository;
        this.timetableService = timetableService;
        this.courseService = courseService;
    }

    public List<Meeting> findAll() {
        return meetingRepository.findAll();
    }

    public List<Meeting> findAllByStudiengang(String studiengang) {
        studiengang = studiengang.substring(0, studiengang.length() - 2);
        Course solidCourse = courseService.findFirstByNameContaining(studiengang);
        return meetingRepository.findAllByCourse(solidCourse);
    }

    public List<Meeting> findAllByStudiengangId(Long studiengang) {
        Course courseTemp = courseService.findById(studiengang);
        return meetingRepository.findAllByCourse(courseTemp);
    }

    public void deleteAll(List<Meeting> veranstaltungen) {
        for (Meeting meeting : veranstaltungen) {
            List<CourseEntry> stundenplanEintraege = timetableService.findAllByVeranstaltung(meeting);
            timetableService.deleteAll(stundenplanEintraege);
        }
        meetingRepository.deleteAll(veranstaltungen);
    }

    public void deleteAll() {
        List<Meeting> veranstaltungen = findAll();
        for (Meeting meeting : veranstaltungen) {
            List<CourseEntry> stundenplanEintraege = timetableService.findAllByVeranstaltung(meeting);
            timetableService.deleteAll(stundenplanEintraege);
        }
        meetingRepository.deleteAll(veranstaltungen);
    }

    public List<Meeting> findByIdsIn(List<Long> ids) {
        return meetingRepository.findByIdIn(ids);
    }

    public List<Meeting> findAllByCourseAndSemester(Course course, int semester) {
        return meetingRepository.findAllByCourseAndSemester(course, semester);
    }

    public List<Meeting> findAllByProfessorName(String professorName) {
        return meetingRepository.findAllByProfessorContaining(professorName);
    }

    public Meeting findFirstByNameAndCourse(String tempModulName, Course course) {
        return meetingRepository.findFirstByNameAndCourse(tempModulName, course);
    }

    public void saveAll(List<Meeting> meetingList) {
        meetingRepository.saveAll(meetingList);
    }
}
