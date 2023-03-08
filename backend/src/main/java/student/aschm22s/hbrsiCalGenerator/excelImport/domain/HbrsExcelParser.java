package student.aschm22s.hbrsiCalGenerator.excelImport.domain;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain.Course;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.service.CourseService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.CourseEntry;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.service.TimetableService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Meeting;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.service.MeetingService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

@Service
public class HbrsExcelParser {
    private final MeetingService meetingService;
    private final CourseService courseService;
    private final TimetableService timetableService;

    public HbrsExcelParser(MeetingService meetingService, CourseService courseService, TimetableService timetableService) {
        this.meetingService = meetingService;
        this.courseService = courseService;
        this.timetableService = timetableService;
    }

    public boolean meetingAlreadyExists(Meeting meeting, Iterable<Meeting> meetings) {
        for (Meeting x : meetings) {
            if (meeting.getName().equals(x.getName()) &&
                    meeting.getProfessor().equals(x.getProfessor()) &&
                    meeting.getSemester() == x.getSemester())
                return true;
        }
        return false;
    }

    public ArrayList<Meeting> parseMeetings(InputStream excelFile) throws IOException {
        Workbook workbook = new HSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        Row row = sheet.getRow(0);

        String courseSemesterString = (row.getCell(0) + "").substring(13);
        String courseString = courseSemesterString.substring(0, courseSemesterString.length() - 2);
        Integer semester;
        try {
            semester = Integer.parseInt(courseSemesterString.substring(courseSemesterString.length() - 1));
        } catch (NumberFormatException e) {
            courseString = courseSemesterString;
            semester = 0;
        }

        Course selectedCourse = courseService.findByNameIfNotExistCreate(courseString);
        ArrayList<Meeting> veranstaltungsListe = new ArrayList<>(meetingService.findAllByCourseAndSemester(selectedCourse, semester));

        for (int i = 5; i < rows - 1; ++i) {
            String veranstaltungsname;
            row = sheet.getRow(i);
            veranstaltungsname = (row.getCell(4) + "");

            Meeting newCourse = new Meeting();
            newCourse.setName(veranstaltungsname);
            newCourse.setProfessor(row.getCell(6) + "");
            newCourse.setCourse(selectedCourse);
            newCourse.setSemester(semester);

            if (meetingAlreadyExists(newCourse, veranstaltungsListe))
                continue;

            veranstaltungsListe.add(newCourse);
        }
        meetingService.saveAll(veranstaltungsListe);
        return veranstaltungsListe;
    }

    @Transactional
    public LinkedList<CourseEntry> parseTimetable(InputStream excelFile) throws IOException, StudiengangNotFoundException {
        Workbook workbook = new HSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        Row row = sheet.getRow(0);

        String aktuellerTag = "";
        String studienGangSemester = (row.getCell(0) + "").substring(13);
        Optional<Course> studiengang = courseService.findAllByNameContaining(studienGangSemester.substring(0, studienGangSemester.length() - 2)).stream().findFirst();

        if (studiengang.isEmpty())
            throw new StudiengangNotFoundException("Studiengang " + studienGangSemester + " wurde nicht gefunden, import für Stundenplan wird abgebrochen");

        var meetings = new LinkedList<CourseEntry>();

        for (int i = 5; i < rows - 1; ++i) {
            row = sheet.getRow(i);
            String parsedDayInExcel = row.getCell(0) + "";
            if (!parsedDayInExcel.equals(""))
                aktuellerTag = parsedDayInExcel;

            var timeHourMinuteStart = row.getCell(1).toString().trim();
            if (timeHourMinuteStart.length() < 5) {
                timeHourMinuteStart = "0" + timeHourMinuteStart;
            }
            var timeHourMinuteEnd = row.getCell(2).toString().trim();
            if (timeHourMinuteEnd.length() < 5) {
                timeHourMinuteEnd = "0" + timeHourMinuteStart;
            }

            LocalDateTime timeOfEventStart = LocalDateTime.of(
                    LocalDate.parse((row.getCell(5).toString()).substring(0, 10), DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    LocalTime.parse(timeHourMinuteStart, DateTimeFormatter.ofPattern("HH:mm"))
            );
            LocalDateTime timeOfEventEnd = LocalDateTime.of(
                    LocalDate.parse((row.getCell(5) + "").substring(11, 21), DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    LocalTime.parse(timeHourMinuteEnd, DateTimeFormatter.ofPattern("HH:mm"))
            );

            var weeksInBetweenAmount = ChronoUnit.WEEKS.between(timeOfEventStart, timeOfEventEnd);

            for (int j = 0; j < weeksInBetweenAmount; j++) {
                var timestampStart = timeOfEventStart.plusWeeks(j);
                var timestampEnd = timeOfEventEnd.plusWeeks(j);

                var newAppointment = new CourseEntry();
                newAppointment.setRoom(row.getCell(3) + "");
                newAppointment.setStart(timestampStart);
                newAppointment.setEnd(timestampEnd);
                String tempModulName = row.getCell(4) + "";
                Meeting meeting = meetingService.findFirstByNameAndCourse(tempModulName, studiengang.get());
                newAppointment.setMeeting(meeting);
                newAppointment.setDayString(aktuellerTag);
                meetings.add(newAppointment);
            }
        }

        return meetings;
    }

    @Transactional
    public String startParser(ArrayList<InputStream> files) {
        int counterPlaene = 0;
        for (InputStream x : files) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                x.transferTo(baos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<Meeting> veranstaltungen = new ArrayList<>();
            try {
                veranstaltungen = parseMeetings(new ByteArrayInputStream(baos.toByteArray()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                timetableService.saveAll(parseTimetable(new ByteArrayInputStream(baos.toByteArray())));
            } catch (IOException | StudiengangNotFoundException e) {
                e.printStackTrace();
            }
            Meeting meeting = veranstaltungen.stream().findFirst().orElse(null);
            if (meeting == null) {
                System.out.println("Stundenplanimport fehler, siehe Stacktrace");
            } else
                System.out.println("Stundenplan: " + meeting.getCourse().getName() + " wurde importiert");
            counterPlaene++;
        }

        System.out.println("Importvorgang wurde gestartet");

        if (counterPlaene != files.size()) {
            return "Es gab einen Fehler beim importieren. " + counterPlaene + " von " + files.size() + " wurden importiert";
        }

        return "Alle " + files.size() + " Stundenpläne wurden importiert";
    }
}
