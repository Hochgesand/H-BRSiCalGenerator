package student.aschm22s.hbrsiCalGenerator.excelImport.domain;

import org.junit.jupiter.api.Test;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain.Course;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.service.CourseService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Meeting;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.service.MeetingService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;


class HbrsExcelParserTest {
    @Test
    void parseMeetingNoExistingMeeting() throws IOException {
        var mockedCourseService = mock(CourseService.class);
        var mockedMeetingService = mock(MeetingService.class);
        when(mockedCourseService.findByNameIfNotExistCreate(anyString())).thenReturn(new Course());
        when(mockedMeetingService.findAllByCourseAndSemester(isA(Course.class), isA(Integer.class))).thenReturn(new ArrayList<>());
        doNothing().when(mockedMeetingService).deleteAll(isA(List.class));
        doNothing().when(mockedMeetingService).saveAll(isA(ArrayList.class));

        var parser = new HbrsExcelParser(mockedMeetingService, mockedCourseService, null);
        // prepare Test
        var file = new FileInputStream("src/test/resources/testFiles/Timetable-Bachelor_Informatik_2.xls");
        var parsedVeranstaltungen = parser.parseMeetings(file);
        for (var x : parsedVeranstaltungen) {
            assertEquals(2, x.getSemester());
        }
        var expectedVeranstaltung = parsedVeranstaltungen.stream().filter(n -> n.getName().equals("E. d. Analysis Gr. BI-1 (Ü)")).findFirst();
        assertFalse(expectedVeranstaltung.isEmpty());
    }

    @Test
    void parseMeetings_ExistingMeeting() throws IOException {
        var mockedStudiengangService = mock(CourseService.class);
        var mockedVeranstaltungsService = mock(MeetingService.class);
        when(mockedStudiengangService.findByNameIfNotExistCreate(anyString())).thenReturn(new Course());
        var veranstaltungen = new ArrayList<Meeting>();
        veranstaltungen.add(new Meeting(0L, "E. d. Analysis Gr. BI-2 (Ü)", "Becker", 2, new Course(), null));
        veranstaltungen.add(new Meeting(0L, "E. d. Analysis Gr. BI-1 (Ü)", "Becker", 2, new Course(), null));
        when(mockedVeranstaltungsService.findAllByCourseAndSemester(isA(Course.class), isA(Integer.class))).thenReturn(veranstaltungen);
        doNothing().when(mockedVeranstaltungsService).deleteAll(isA(List.class));
        doNothing().when(mockedVeranstaltungsService).saveAll(isA(ArrayList.class));

        var parser = new HbrsExcelParser(mockedVeranstaltungsService, mockedStudiengangService, null);
        // prepare Test
        var file = new FileInputStream("src/test/resources/testFiles/Timetable-Bachelor_Informatik_2.xls");
        var parsedVeranstaltungen = parser.parseMeetings(file);
        for (var x : parsedVeranstaltungen) {
            assertEquals(2, x.getSemester());
        }
        var expectedVeranstaltung = parsedVeranstaltungen.stream().filter(n -> n.getName().equals("E. d. Analysis Gr. BI-1 (Ü)")).toList();
        var expectedVeranstaltung2 = parsedVeranstaltungen.stream().filter(n -> n.getName().equals("E. d. Analysis Gr. BI-2 (Ü)")).findFirst();
        assertFalse(expectedVeranstaltung2.isEmpty());
        assertEquals(1, expectedVeranstaltung.size());
    }

    @Test
    void parseStundenplan() {

    }
}
