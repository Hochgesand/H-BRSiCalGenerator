package student.aschm22s.hbrsiCalGenerator.excelImport.domain;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.DateTime;
import org.joda.time.Weeks;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain.Studiengang;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.service.StudiengangService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.appointment.domain.Appointment;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.StundenplanEintrag;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.appointment.service.AppointmentService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.service.StundenplanService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Veranstaltung;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.service.VeranstaltungsService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HbrsExcelParser {
    private final VeranstaltungsService veranstaltungsService;
    private final StudiengangService studiengangService;
    private final StundenplanService stundenplanService;
    private final AppointmentService appointmentService;

    public HbrsExcelParser(VeranstaltungsService veranstaltungsService, StudiengangService studiengangService, StundenplanService stundenplanService, AppointmentService appointmentService) {
        this.veranstaltungsService = veranstaltungsService;
        this.studiengangService = studiengangService;
        this.stundenplanService = stundenplanService;
        this.appointmentService = appointmentService;
    }

    public boolean veranstaltungAlreadyExists(Veranstaltung veranstaltung, Iterable<Veranstaltung> veranstaltungen) {
        for (Veranstaltung x : veranstaltungen) {
            if (veranstaltung.getName().equals(x.getName()) &&
                veranstaltung.getProf().equals(x.getProf()) &&
                veranstaltung.getSemester() == x.getSemester())
                return true;
        }
        return false;
    }

    public ArrayList<Veranstaltung> parseVeranstaltungen(InputStream excelFile) throws IOException {
        Workbook workbook = new HSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        Row row = sheet.getRow(0);

        String studiengangSemesterString = (row.getCell(0) + "").substring(13);
        String studiengangString = studiengangSemesterString.substring(0, studiengangSemesterString.length() - 2);
        Integer semester;
        try {
            semester = Integer.parseInt(studiengangSemesterString.substring(studiengangSemesterString.length() - 1));
        } catch (NumberFormatException e) {
            studiengangString = studiengangSemesterString;
            semester = 0;
        }

        Studiengang selectedStudiengang = studiengangService.findByNameIfNotExistCreate(studiengangString);
        ArrayList<Veranstaltung> veranstaltungsListe = new ArrayList<>(veranstaltungsService.findAllByStudiengangAndSemester(selectedStudiengang, semester));

        for (int i = 5; i < rows - 1; ++i) {
            String veranstaltungsname;
            row = sheet.getRow(i);
            veranstaltungsname = (row.getCell(4) + "");

            Veranstaltung neueVeranstaltung = new Veranstaltung();
            neueVeranstaltung.setName(veranstaltungsname);
            neueVeranstaltung.setProf(row.getCell(6) + "");
            neueVeranstaltung.setStudiengang(selectedStudiengang);
            neueVeranstaltung.setSemester(semester);

            if (veranstaltungAlreadyExists(neueVeranstaltung, veranstaltungsListe))
                continue;

            veranstaltungsListe.add(neueVeranstaltung);
        }
        veranstaltungsService.saveAll(veranstaltungsListe);
        return veranstaltungsListe;
    }

    @Transactional
    public Iterable<StundenplanEintrag> parseStundenplan(InputStream excelFile) throws IOException, StudiengangNotFoundException {
        Workbook workbook = new HSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        Row row = sheet.getRow(0);

        String aktuellerTag = "";
        String studienGangSemester = (row.getCell(0) + "").substring(13);
        Optional<Studiengang> studiengang = studiengangService.findAllByNameContaining(studienGangSemester.substring(0, studienGangSemester.length() - 2)).stream().findFirst();

        if (studiengang.isEmpty())
            throw new StudiengangNotFoundException("Studiengang " + studienGangSemester + " wurde nicht gefunden, import für Stundenplan wird abgebrochen");

        for (int i = 5; i < rows - 1; ++i) {
            row = sheet.getRow(i);
            String tempDay = row.getCell(0) + "";
            if (!tempDay.equals(""))
                aktuellerTag = tempDay;

            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy");
            DateTime dateVon = DateTime.parse((row.getCell(5) + "").substring(0, 10), dateTimeFormatter);
            DateTime dateBis = DateTime.parse((row.getCell(5) + "").substring(11, 21), dateTimeFormatter);

            int weeksInBetweenAmount = Weeks.weeksBetween(dateVon, dateBis).getWeeks();

            StundenplanEintrag neuerStundenplanEintrag = new StundenplanEintrag();
            neuerStundenplanEintrag.setVon(Time.valueOf(row.getCell(1) + ":00"));
            neuerStundenplanEintrag.setBis(Time.valueOf(row.getCell(2) + ":00"));
            neuerStundenplanEintrag.setRaum(row.getCell(3) + "");
            String tempModulName = row.getCell(4) + "";
            Veranstaltung veranstaltung = veranstaltungsService.findFirstByNameAndStudiengang(tempModulName, studiengang.get());
            neuerStundenplanEintrag.setVeranstaltung(veranstaltung);
            neuerStundenplanEintrag.setTag(aktuellerTag);
            StundenplanEintrag newObjectInDB = stundenplanService.save(neuerStundenplanEintrag);

            for (int j = 0; j < weeksInBetweenAmount; j++) {
                DateTime dateTimeWithCorrectTime = dateVon;
                long tempTimeToAdd = neuerStundenplanEintrag.getVon().getTime();
                dateTimeWithCorrectTime = dateTimeWithCorrectTime.plus(tempTimeToAdd).plusHours(1);

                Appointment stundenplanDatumMN = new Appointment();
                stundenplanDatumMN.setDate(dateTimeWithCorrectTime.getMillis());
                stundenplanDatumMN.setStundenplanEintrag(newObjectInDB);
                appointmentService.save(stundenplanDatumMN);
                dateVon = dateVon.plusWeeks(1);
            }
        }

        return stundenplanService.findAll();
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
            ArrayList<Veranstaltung> veranstaltungen = new ArrayList<>();
            try {
                veranstaltungen = parseVeranstaltungen(new ByteArrayInputStream(baos.toByteArray()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                parseStundenplan(new ByteArrayInputStream(baos.toByteArray()));
            } catch (IOException | StudiengangNotFoundException e) {
                e.printStackTrace();
            }
            Veranstaltung veranstaltung = veranstaltungen.stream().findFirst().orElse(null);
            if (veranstaltung == null) {
                System.out.println("Stundenplanimport fehler, siehe Stacktrace");
            } else
                System.out.println("Stundenplan: " + veranstaltung.getStudiengang().getName() + " wurde importiert");
            counterPlaene++;
        }

        System.out.println("Importvorgang wurde gestartet");

        if (counterPlaene != files.size()) {
            return "Es gab einen Fehler beim importieren. " + counterPlaene + " von " + files.size() + " wurden importiert";
        }

        return "Alle " + files.size() + " Stundenpläne wurden importiert";
    }
}
