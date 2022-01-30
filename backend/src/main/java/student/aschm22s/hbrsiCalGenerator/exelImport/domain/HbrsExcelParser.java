package student.aschm22s.hbrsiCalGenerator.exelImport.domain;

import javassist.NotFoundException;
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
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.Appointment;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.StundenplanEintrag;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.repository.StundenplanDateMNRepository;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.repository.StundenplanRepository;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Studiengang;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Veranstaltung;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.repository.StudiengangsRepository;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.repository.VeranstaltungsRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

//TODO: Refactoring: Parser has only one responsibility. It has not to write into repository.
@Service
public class HbrsExcelParser {

    private final VeranstaltungsRepository veranstaltungsRepo;
    private final StundenplanRepository stundenplanRepo;
    private final StundenplanDateMNRepository stundenplanDateMNRepo;
    private final StudiengangsRepository studiengangsRepository;

    public HbrsExcelParser(VeranstaltungsRepository veranstaltungsRepo, StundenplanRepository stundenplanRepo, StundenplanDateMNRepository stundenplanDateMNRepo, StudiengangsRepository studiengangsRepository) {
        this.veranstaltungsRepo = veranstaltungsRepo;
        this.stundenplanRepo = stundenplanRepo;
        this.stundenplanDateMNRepo = stundenplanDateMNRepo;
        this.studiengangsRepository = studiengangsRepository;
    }

    public boolean veranstaltungAlreadyExists(Veranstaltung veranstaltung, Iterable<Veranstaltung> veranstaltungen) {
        for (Veranstaltung x : veranstaltungen) {
            if (veranstaltung.getName().equals(x.getName()) && veranstaltung.getProf().equals(x.getProf()))
                return true;
        }
        return false;
    }

    public ArrayList<Veranstaltung> parseVeranstaltungen(InputStream excelFile) throws IOException, NotFoundException {
        Workbook workbook = new HSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        Row row = sheet.getRow(0);

        ArrayList<Veranstaltung> veranstaltungsListe = new ArrayList<>();
        String studiengangSemesterString = (row.getCell(0) + "").substring(16);
        String studiengangString = studiengangSemesterString.substring(0, studiengangSemesterString.length() - 2);

        Studiengang selectedStudiengang = studiengangsRepository.findFirstByNameContaining(studiengangString);
        Integer semester;
        try {
            semester = Integer.parseInt(studiengangSemesterString.substring(studiengangSemesterString.length() - 1));
        } catch (NumberFormatException e) {
            studiengangString = studiengangSemesterString;
            semester = 0;
        }
        if (selectedStudiengang == null) {
            Studiengang studiengang = new Studiengang();
            studiengang.setName(studiengangString);
            studiengangsRepository.save(studiengang);
            selectedStudiengang = studiengangsRepository.findFirstByNameContaining(studiengang.getName());
        }

        {
            List<Veranstaltung> veranstaltungen = veranstaltungsRepo.findAllByStudiengangAndSemester(selectedStudiengang, semester);
            for (Veranstaltung veranstaltung : veranstaltungen) {
                Collection<StundenplanEintrag> stundenplanEintraege = veranstaltung.getStundenplanEintrags();
                stundenplanRepo.deleteAll(stundenplanEintraege);
            }
            veranstaltungsRepo.deleteAll(veranstaltungen);
        }
        for (int i = 5; i < rows - 1; ++i) {
            String veranstaltungsname;
            row = sheet.getRow(i);
            veranstaltungsname = (row.getCell(4) + "");

            Veranstaltung neueVeranstaltung = new Veranstaltung();
            neueVeranstaltung.setName(veranstaltungsname);
            neueVeranstaltung.setProf(row.getCell(6) + "");
            neueVeranstaltung.setStudiengang(new Studiengang());
            neueVeranstaltung.setStudiengang(selectedStudiengang);
            neueVeranstaltung.setSemester(semester);

            if (veranstaltungAlreadyExists(neueVeranstaltung, veranstaltungsListe))
                continue;

            veranstaltungsListe.add(neueVeranstaltung);
        }
        veranstaltungsRepo.saveAll(veranstaltungsListe);
        return veranstaltungsListe;
    }

    @Transactional
    public Iterable<StundenplanEintrag> parseStundenplan(InputStream excelFile) throws IOException, NotFoundException {
        Workbook workbook = new HSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        Row row = sheet.getRow(0);

        String aktuellerTag = "";
        String studienGangSemester = (row.getCell(0) + "").substring(16);
        Optional<Studiengang> studiengang = studiengangsRepository.findAllByNameContaining(studienGangSemester.substring(0, studienGangSemester.length() - 2)).stream().findFirst();

        if (studiengang.isEmpty())
            throw new NotFoundException("Studiengang " + studienGangSemester + " wurde nicht gefunden, import für Stundenplan wird abgebrochen");

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
            Veranstaltung temp = veranstaltungsRepo.findFirstByNameAndStudiengang(tempModulName, studiengang.get());
            neuerStundenplanEintrag.setVeranstaltung(temp);
            neuerStundenplanEintrag.setTag(aktuellerTag);
            StundenplanEintrag newObjectInDB = stundenplanRepo.save(neuerStundenplanEintrag);

            for (int j = 0; j < weeksInBetweenAmount; j++) {
                dateVon = dateVon.plusWeeks(1);
                DateTime dateTimeWithCorrectTime = dateVon;
                long tempTimeToAdd = neuerStundenplanEintrag.getVon().getTime();
                dateTimeWithCorrectTime = dateTimeWithCorrectTime.plus(tempTimeToAdd).plusHours(1);

                Appointment stundenplanDatumMN = new Appointment();
                stundenplanDatumMN.setDate(dateTimeWithCorrectTime.getMillis());
                stundenplanDatumMN.setStundenplanEintrag(newObjectInDB);
                stundenplanDateMNRepo.save(stundenplanDatumMN);
            }
        }

        return stundenplanRepo.findAll();
    }

    @Transactional
    public String startParser(ArrayList<InputStream> files) {
        Integer counterPlaene = 0;
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
            } catch (IOException | NotFoundException e) {
                e.printStackTrace();
            }

            try {
                parseStundenplan(new ByteArrayInputStream(baos.toByteArray()));
            } catch (IOException | NotFoundException e) {
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
