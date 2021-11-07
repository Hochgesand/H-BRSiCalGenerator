package student.aschm22s.hbrsiCalGenerator.exelImport.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import student.aschm22s.hbrsiCalGenerator.exelImport.domain.HbrsExcelParser;
import student.aschm22s.hbrsiCalGenerator.veranstaltung.service.VeranstaltungsService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

@Service
public class ExcelImportService {

    private final HbrsExcelParser hbrsExcelParser;
    private final VeranstaltungsService veranstaltungsService;

    private final String[] evaLinks = new String[]{
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=39;40;41;42;43;44;45;46;47;48;49;50;51;54;55&days=1-7&mode=xls&identifier_semester=%23SPLUS3D9E23&show_semester=&identifier_dozent=&identifier_raum=&term=e4e488484864f3046d4b77c253f3d3a6",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=39;40;41;42;43;44;45;46;47;48;49;50;51;54;55&days=1-7&mode=xls&identifier_semester=%23SPLUS1428E2&show_semester=&identifier_dozent=&identifier_raum=&term=e4e488484864f3046d4b77c253f3d3a6",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=39;40;41;42;43;44;45;46;47;48;49;50;51;54;55&days=1-7&mode=xls&identifier_semester=%23SPLUSEBD352&show_semester=&identifier_dozent=&identifier_raum=&term=e4e488484864f3046d4b77c253f3d3a6",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=39;40;41;42;43;44;45;46;47;48;49;50;51;54;55&days=1-7&mode=xls&identifier_semester=%23SPLUS4E1741&show_semester=&identifier_dozent=&identifier_raum=&term=e4e488484864f3046d4b77c253f3d3a6",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=39;40;41;42;43;44;45;46;47;48;49;50;51;54;55&days=1-7&mode=xls&identifier_semester=%23SPLUSF85D4A&show_semester=&identifier_dozent=&identifier_raum=&term=e4e488484864f3046d4b77c253f3d3a6",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=39;40;41;42;43;44;45;46;47;48;49;50;51;54;55&days=1-7&mode=xls&identifier_semester=%23SPLUSD09928&show_semester=&identifier_dozent=&identifier_raum=&term=e4e488484864f3046d4b77c253f3d3a6",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=39;40;41;42;43;44;45;46;47;48;49;50;51;54;55&days=1-7&mode=xls&identifier_semester=%23SPLUS2117FA&show_semester=&identifier_dozent=&identifier_raum=&term=e4e488484864f3046d4b77c253f3d3a6",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=39;40;41;42;43;44;45;46;47;48;49;50;51;54;55&days=1-7&mode=xls&identifier_semester=MAS1&show_semester=&identifier_dozent=&identifier_raum=&term=e4e488484864f3046d4b77c253f3d3a6",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=39;40;41;42;43;44;45;46;47;48;49;50;51;54;55&days=1-7&mode=xls&identifier_semester=MAS2&show_semester=&identifier_dozent=&identifier_raum=&term=e4e488484864f3046d4b77c253f3d3a6",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=39;40;41;42;43;44;45;46;47;48;49;50;51;54;55&days=1-7&mode=xls&identifier_semester=MAS3&show_semester=&identifier_dozent=&identifier_raum=&term=e4e488484864f3046d4b77c253f3d3a6",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=39;40;41;42;43;44;45;46;47;48;49;50;51;54;55&days=1-7&mode=xls&identifier_semester=%23SPLUS4E0F15&show_semester=&identifier_dozent=&identifier_raum=&term=e4e488484864f3046d4b77c253f3d3a6",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=39;40;41;42;43;44;45;46;47;48;49;50;51;54;55&days=1-7&mode=xls&identifier_semester=%23SPLUSB4D1FD&show_semester=&identifier_dozent=&identifier_raum=&term=e4e488484864f3046d4b77c253f3d3a6",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=39;40;41;42;43;44;45;46;47;48;49;50;51;54;55&days=1-7&mode=xls&identifier_semester=MCS1&show_semester=&identifier_dozent=&identifier_raum=&term=e4e488484864f3046d4b77c253f3d3a6",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=39;40;41;42;43;44;45;46;47;48;49;50;51;54;55&days=1-7&mode=xls&identifier_semester=MCS2&show_semester=&identifier_dozent=&identifier_raum=&term=e4e488484864f3046d4b77c253f3d3a6",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=39;40;41;42;43;44;45;46;47;48;49;50;51;54;55&days=1-7&mode=xls&identifier_semester=MCS3&show_semester=&identifier_dozent=&identifier_raum=&term=e4e488484864f3046d4b77c253f3d3a6",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=39;40;41;42;43;44;45;46;47;48;49;50;51;54;55&days=1-7&mode=xls&identifier_semester=%23SPLUSB95404&show_semester=&identifier_dozent=&identifier_raum=&term=e4e488484864f3046d4b77c253f3d3a6",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=39;40;41;42;43;44;45;46;47;48;49;50;51;54;55&days=1-7&mode=xls&identifier_semester=%23SPLUSB95405&show_semester=&identifier_dozent=&identifier_raum=&term=e4e488484864f3046d4b77c253f3d3a6",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=39;40;41;42;43;44;45;46;47;48;49;50;51;54;55&days=1-7&mode=xls&identifier_semester=%23SPLUS4E1743&show_semester=&identifier_dozent=&identifier_raum=&term=e4e488484864f3046d4b77c253f3d3a6",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=39;40;41;42;43;44;45;46;47;48;49;50;51;54;55&days=1-7&mode=xls&identifier_semester=%23SPLUS689FC6&show_semester=&identifier_dozent=&identifier_raum=&term=e4e488484864f3046d4b77c253f3d3a6"
    };

    public ExcelImportService(HbrsExcelParser hbrsExcelParser, VeranstaltungsService veranstaltungsService) {
        this.hbrsExcelParser = hbrsExcelParser;
        this.veranstaltungsService = veranstaltungsService;
    }

    public String importFiles(MultipartFile[] files) throws IOException {
        ArrayList<InputStream> inputstreams = new ArrayList<>();
        for (MultipartFile y : files) {
            inputstreams.add(y.getInputStream());
        }

        return hbrsExcelParser.startParser(inputstreams);
    }

    public String updateStundenplaene() throws IOException {
        //TODO: Refactor deleteAll after successful parsing
        veranstaltungsService.deleteAll();

        ArrayList<InputStream> inputstreams = new ArrayList<>();
        for (String string : evaLinks) {
            ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(string).openStream());
            inputstreams.add(Channels.newInputStream(readableByteChannel));
        }

        return hbrsExcelParser.startParser(inputstreams);
    }
}
