package student.aschm22s.hbrsiCalGenerator.excelImport.domain;

import org.junit.jupiter.api.Test;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain.Studiengang;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.service.StudiengangService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Veranstaltung;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.service.VeranstaltungsService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class HbrsExcelParserTest {
    @Test
    void parseVeranstaltungenNoExistingVeranstaltung() throws IOException {
        var mockedStudiengangService = mock(StudiengangService.class);
        var mockedVeranstaltungsService = mock(VeranstaltungsService.class);
        when(mockedStudiengangService.findByNameIfNotExistCreate(anyString())).thenReturn(new Studiengang());
        when(mockedVeranstaltungsService.findAllByStudiengangAndSemester(isA(Studiengang.class), isA(Integer.class))).thenReturn(new ArrayList<>());
        doNothing().when(mockedVeranstaltungsService).deleteAll(isA(List.class));
        doNothing().when(mockedVeranstaltungsService).saveAll(isA(ArrayList.class));

        var parser = new HbrsExcelParser(mockedVeranstaltungsService, mockedStudiengangService, null, null);
        // prepare Test
        var file = new FileInputStream("src/test/resources/testFiles/Timetable-Bachelor_Informatik_2.xls");
        var parsedVeranstaltungen = parser.parseVeranstaltungen(file);
        for (var x : parsedVeranstaltungen) {
            assertEquals(2, x.getSemester());
        }
        var expectedVeranstaltung = parsedVeranstaltungen.stream().filter(n -> n.getName().equals("E. d. Analysis Gr. BI-1 (Ü)")).findFirst();
        assertTrue(!expectedVeranstaltung.isEmpty());
    }
    @Test
    void parseVeranstaltungenExistingVeranstaltung() throws IOException {
        var mockedStudiengangService = mock(StudiengangService.class);
        var mockedVeranstaltungsService = mock(VeranstaltungsService.class);
        when(mockedStudiengangService.findByNameIfNotExistCreate(anyString())).thenReturn(new Studiengang());
        var veranstaltungen = new ArrayList<Veranstaltung>();
        veranstaltungen.add(new Veranstaltung(0L, "E. d. Analysis Gr. BI-2 (Ü)", "", 2, new Studiengang(), null));
        veranstaltungen.add(new Veranstaltung(0L, "E. d. Analysis Gr. BI-1 (Ü)", "", 2, new Studiengang(), null));
        when(mockedVeranstaltungsService.findAllByStudiengangAndSemester(isA(Studiengang.class), isA(Integer.class))).thenReturn(new ArrayList<>(        ));
        doNothing().when(mockedVeranstaltungsService).deleteAll(isA(List.class));
        doNothing().when(mockedVeranstaltungsService).saveAll(isA(ArrayList.class));

        var parser = new HbrsExcelParser(mockedVeranstaltungsService, mockedStudiengangService, null, null);
        // prepare Test
        var file = new FileInputStream("src/test/resources/testFiles/Timetable-Bachelor_Informatik_2.xls");
        var parsedVeranstaltungen = parser.parseVeranstaltungen(file);
        for (var x : parsedVeranstaltungen) {
            assertEquals(2, x.getSemester());
        }
        var expectedVeranstaltung = parsedVeranstaltungen.stream().filter(n -> n.getName().equals("E. d. Analysis Gr. BI-1 (Ü)")).toList();
        var expectedVeranstaltung2 = parsedVeranstaltungen.stream().filter(n -> n.getName().equals("E. d. Analysis Gr. BI-2 (Ü)")).findFirst();
        assertTrue(!expectedVeranstaltung2.isEmpty());
        assertTrue(expectedVeranstaltung.size() == 1);
    }

    @Test
    void parseStundenplan() {

    }
}
