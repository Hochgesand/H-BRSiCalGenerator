package student.aschm22s.hbrsiCalGenerator.exelImport.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import student.aschm22s.hbrsiCalGenerator.exelImport.domain.HbrsExcelParser;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.service.VeranstaltungsService;

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
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=13;14;15;16;17;18;19;20;21;22;23;24;25;26&days=1-7&mode=xls&identifier_semester=%23SPLUSD4951B&show_semester=&identifier_dozent=&identifier_raum=&term=53cecb86b8f62b6c1a8d4272c39f8766",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=13;14;15;16;17;18;19;20;21;22;23;24;25;26&days=1-7&mode=xls&identifier_semester=BCS2&show_semester=&identifier_dozent=&identifier_raum=&term=53cecb86b8f62b6c1a8d4272c39f8766",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=13;14;15;16;17;18;19;20;21;22;23;24;25;26&days=1-7&mode=xls&identifier_semester=BCS4&show_semester=&identifier_dozent=&identifier_raum=&term=53cecb86b8f62b6c1a8d4272c39f8766",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=13;14;15;16;17;18;19;20;21;22;23;24;25;26&days=1-7&mode=xls&identifier_semester=BBIS2&show_semester=&identifier_dozent=&identifier_raum=&term=53cecb86b8f62b6c1a8d4272c39f8766",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=13;14;15;16;17;18;19;20;21;22;23;24;25;26&days=1-7&mode=xls&identifier_semester=BBIS4&show_semester=&identifier_dozent=&identifier_raum=&term=53cecb86b8f62b6c1a8d4272c39f8766",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=13;14;15;16;17;18;19;20;21;22;23;24;25;26&days=1-7&mode=xls&identifier_semester=MAS1&show_semester=&identifier_dozent=&identifier_raum=&term=53cecb86b8f62b6c1a8d4272c39f8766",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=13;14;15;16;17;18;19;20;21;22;23;24;25;26&days=1-7&mode=xls&identifier_semester=MAS2&show_semester=&identifier_dozent=&identifier_raum=&term=53cecb86b8f62b6c1a8d4272c39f8766",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=13;14;15;16;17;18;19;20;21;22;23;24;25;26&days=1-7&mode=xls&identifier_semester=MAS3&show_semester=&identifier_dozent=&identifier_raum=&term=53cecb86b8f62b6c1a8d4272c39f8766",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=13;14;15;16;17;18;19;20;21;22;23;24;25;26&days=1-7&mode=xls&identifier_semester=%23SPLUSE4D31E&show_semester=&identifier_dozent=&identifier_raum=&term=53cecb86b8f62b6c1a8d4272c39f8766",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=13;14;15;16;17;18;19;20;21;22;23;24;25;26&days=1-7&mode=xls&identifier_semester=%23SPLUS4222DA&show_semester=&identifier_dozent=&identifier_raum=&term=53cecb86b8f62b6c1a8d4272c39f8766",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=13;14;15;16;17;18;19;20;21;22;23;24;25;26&days=1-7&mode=xls&identifier_semester=MCS1&show_semester=&identifier_dozent=&identifier_raum=&term=53cecb86b8f62b6c1a8d4272c39f8766",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=13;14;15;16;17;18;19;20;21;22;23;24;25;26&days=1-7&mode=xls&identifier_semester=MCS2&show_semester=&identifier_dozent=&identifier_raum=&term=53cecb86b8f62b6c1a8d4272c39f8766",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=13;14;15;16;17;18;19;20;21;22;23;24;25;26&days=1-7&mode=xls&identifier_semester=MCS3&show_semester=&identifier_dozent=&identifier_raum=&term=53cecb86b8f62b6c1a8d4272c39f8766",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=13;14;15;16;17;18;19;20;21;22;23;24;25;26&days=1-7&mode=xls&identifier_semester=%23SPLUS157A20&show_semester=&identifier_dozent=&identifier_raum=&term=53cecb86b8f62b6c1a8d4272c39f8766",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=13;14;15;16;17;18;19;20;21;22;23;24;25;26&days=1-7&mode=xls&identifier_semester=%23SPLUSAC7DD0&show_semester=&identifier_dozent=&identifier_raum=&term=53cecb86b8f62b6c1a8d4272c39f8766",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=13;14;15;16;17;18;19;20;21;22;23;24;25;26&days=1-7&mode=xls&identifier_semester=%23SPLUSAC7DD1&show_semester=&identifier_dozent=&identifier_raum=&term=53cecb86b8f62b6c1a8d4272c39f8766",
            "https://eva2.inf.h-brs.de/stundenplan/anzeigen/?weeks=13;14;15;16;17;18;19;20;21;22;23;24;25;26&days=1-7&mode=xls&identifier_semester=%23SPLUS382187&show_semester=&identifier_dozent=&identifier_raum=&term=53cecb86b8f62b6c1a8d4272c39f8766",
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
        ArrayList<InputStream> inputstreams = new ArrayList<>();
        for (String string : evaLinks) {
            ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(string).openStream());
            inputstreams.add(Channels.newInputStream(readableByteChannel));
        }

        return hbrsExcelParser.startParser(inputstreams);
    }
}
