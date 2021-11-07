package student.aschm22s.hbrsiCalGenerator.exelImport.domain;

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
import student.aschm22s.hbrsiCalGenerator.stundenplan.domain.StundenplanDatumMN;
import student.aschm22s.hbrsiCalGenerator.stundenplan.domain.StundenplanEintrag;
import student.aschm22s.hbrsiCalGenerator.stundenplan.repository.StundenplanDateMNRepository;
import student.aschm22s.hbrsiCalGenerator.stundenplan.repository.StundenplanRepository;
import student.aschm22s.hbrsiCalGenerator.veranstaltung.domain.Veranstaltung;
import student.aschm22s.hbrsiCalGenerator.veranstaltung.repository.VeranstaltungsRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class HbrsExcelParser {

    private final VeranstaltungsRepository veranstaltungsRepo;
    private final StundenplanRepository stundenplanRepo;
    private final StundenplanDateMNRepository stundenplanDateMNRepo;

    public HbrsExcelParser(VeranstaltungsRepository veranstaltungsRepo, StundenplanRepository stundenplanRepo, StundenplanDateMNRepository stundenplanDateMNRepo) {
        this.veranstaltungsRepo = veranstaltungsRepo;
        this.stundenplanRepo = stundenplanRepo;
        this.stundenplanDateMNRepo = stundenplanDateMNRepo;
    }

    public boolean veranstaltungAlreadyExists(Veranstaltung veranstaltung, Iterable<Veranstaltung> veranstaltungen) {
        for (Veranstaltung x : veranstaltungen) {
            if (veranstaltung.getName().equals(x.getName()) && veranstaltung.getProf().equals(x.getProf()))
                return true;
        }
        return false;
    }

    @Transactional
    public ArrayList<Veranstaltung> parseVeranstaltungen(InputStream excelFile) throws IOException {
        Workbook workbook = new HSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        Row row = sheet.getRow(0);

        ArrayList<Veranstaltung> veranstaltungsListe = new ArrayList<>();
        String studienGangSemester = (row.getCell(0) + "").substring(16);

        veranstaltungsRepo.deleteAllByStudienGangSemester(studienGangSemester);

        for (int i = 5; i < rows - 1; ++i) {
            String veranstaltungsname;
            row = sheet.getRow(i);
            veranstaltungsname = (row.getCell(4) + "");

            Veranstaltung neueVeranstaltung = new Veranstaltung();
            neueVeranstaltung.setName(veranstaltungsname);
            neueVeranstaltung.setProf(row.getCell(6) + "");
            neueVeranstaltung.setStudienGangSemester(studienGangSemester);

            if (veranstaltungAlreadyExists(neueVeranstaltung, veranstaltungsListe))
                continue;

            veranstaltungsListe.add(neueVeranstaltung);
        }
        veranstaltungsRepo.saveAll(veranstaltungsListe);
        return veranstaltungsListe;
    }

    public Iterable<StundenplanEintrag> parseStundenplan(InputStream excelFile) throws IOException {
        Workbook workbook = new HSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        Row row = sheet.getRow(0);

        String aktuellerTag = "";
        String studienGangSemester = (row.getCell(0) + "").substring(16);

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
            Veranstaltung temp = veranstaltungsRepo.findFirstByNameAndStudienGangSemester(tempModulName, studienGangSemester);
            neuerStundenplanEintrag.setVeranstaltung(temp);
            neuerStundenplanEintrag.setTag(aktuellerTag);
            StundenplanEintrag newObjectInDB = stundenplanRepo.save(neuerStundenplanEintrag);

            for (int j = 0; j < weeksInBetweenAmount; j++) {
                dateVon = dateVon.plusWeeks(1);
                DateTime dateTimeWithCorrectTime = dateVon;
                long tempTimeToAdd = neuerStundenplanEintrag.getVon().getTime();
                dateTimeWithCorrectTime = dateTimeWithCorrectTime.plus(tempTimeToAdd).plusHours(1);

                StundenplanDatumMN stundenplanDatumMN = new StundenplanDatumMN();
                stundenplanDatumMN.setDate(dateTimeWithCorrectTime.getMillis());
                stundenplanDatumMN.setStundenplanEintrag(newObjectInDB);
                stundenplanDateMNRepo.save(stundenplanDatumMN);
            }
        }

        return stundenplanRepo.findAll();
    }

    @Transactional
    public String startParser(ArrayList<InputStream> files) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        AtomicInteger counterPlaene = new AtomicInteger();

        for (InputStream x : files) {
            executorService.submit(() -> {
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Veranstaltung veranstaltung = veranstaltungen.stream().findFirst().orElse(null);
                if (veranstaltung == null) {
                    System.out.println("Stundenplanimport fehler, siehe Stacktrace");
                } else
                    System.out.println("Stundenplan: " + veranstaltung.getStudienGangSemester() + " wurde importiert");
                counterPlaene.getAndIncrement();
            });
        }

        executorService.shutdown();

        System.out.println("Importvorgang wurde gestartet");
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (counterPlaene.get() != files.size()) {
            return "Es gab einen Fehler beim importieren. " + counterPlaene.get() + " von" + files.size() + " wurden importiert";
        }

        return "Alle " + files.size() + " Stundenpläne wurden importiert";
    }
}
