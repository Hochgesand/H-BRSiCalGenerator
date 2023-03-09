package student.aschm22s.hbrsiCalGenerator.calenderExport.service;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import org.springframework.stereotype.Service;
import student.aschm22s.hbrsiCalGenerator.calenderExport.domain.CustomCalenderBase;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.generatedCals.LoggedGeneration;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.generatedCals.TrackService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.service.CourseService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.CourseEntry;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.service.TimetableService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Meeting;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.MeetingIdsAndEmailDTO;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.MeetingIdsDTO;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.service.MeetingService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class CalenderExportService {
    private final MeetingService meetingService;
    private final TimetableService timetableService;
    private final CourseService courseService;
    private final TrackService trackService;

    public CalenderExportService(MeetingService meetingService, TimetableService timetableService, CourseService courseService, TrackService trackService) {
        this.meetingService = meetingService;
        this.timetableService = timetableService;
        this.courseService = courseService;
        this.trackService = trackService;
    }

    public Calendar createCalenderForMeetingAppointmentRecurring(List<Meeting> veranstaltungen) {
        CustomCalenderBase customCalenderBase = new CustomCalenderBase();
        var stundenplanMap = new HashMap<String, LinkedList<CourseEntry>>();

        for (Meeting x : veranstaltungen) {
            timetableService.findByVeranstaltung(x).stream().forEach(stundenplanEintrag -> {
                var hash = stundenplanEintrag.getHashWithoutId();
                var list = stundenplanMap.get(hash);
                if (list == null) {
                    stundenplanMap.put(hash, new LinkedList<>());
                    list = stundenplanMap.get(hash);
                }

                list.add(stundenplanEintrag);
            });
        }

        stundenplanMap.forEach((hash, liste) -> {
            var firstEvent = liste.get(0);
            customCalenderBase.addRecurringEventToCalendar(
                    firstEvent.getMeeting().getName(),
                    new DateTime(firstEvent.getStart().toEpochSecond(ZoneOffset.ofHours(2)) * 1000),
                    new DateTime(firstEvent.getEnd().toEpochSecond(ZoneOffset.ofHours(2)) * 1000),
                    firstEvent.getMeeting().getName(),
                    firstEvent.getMeeting().getProfessor(),
                    firstEvent.getRoom(),
                    liste.size()
            );
        });

        return customCalenderBase.getiCalender();
    }

    public byte[] createCalender(MeetingIdsDTO meetingIdsDTO) throws IOException, NoSuchAlgorithmException {
        if (meetingIdsDTO.getMeetingIds().size() == 0)
            return new byte[0x00];

        var meetings = meetingService.findByIdsIn(meetingIdsDTO.getMeetingIds());
        var calenderToReturn = createCalenderForMeetingAppointmentRecurring(meetings);

        var byteArrayOutputStream = new ByteArrayOutputStream();

        var outputter = new CalendarOutputter();
        outputter.output(calenderToReturn, byteArrayOutputStream);
        var loggedGeneration = new LoggedGeneration();
        loggedGeneration.setTimestamp(new Timestamp(System.currentTimeMillis()));

        if (!meetingIdsDTO.isNotrack()) {
            loggedGeneration.setMeetings(meetings);
            if (meetingIdsDTO instanceof MeetingIdsAndEmailDTO) {
                MessageDigest digest;
                try {
                    digest = MessageDigest.getInstance("SHA-256");
                } catch (NoSuchAlgorithmException ignored) {
                    return byteArrayOutputStream.toByteArray();
                }
                final byte[] hash = digest.digest(((MeetingIdsAndEmailDTO) meetingIdsDTO).getEmail().getBytes());
                final var hexString = new StringBuilder();
                for (byte b : hash) {
                    final String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1)
                        hexString.append('0');
                    hexString.append(hex);
                }

                loggedGeneration.setHashedemail(hexString.toString());
            }
            if (meetings.stream().findFirst().isPresent()) {
                loggedGeneration.setCourse(meetings.stream().findFirst().get().getCourse().getName());
            }
        }
        trackService.generateLogEntry(loggedGeneration);

        return byteArrayOutputStream.toByteArray();
    }
}
