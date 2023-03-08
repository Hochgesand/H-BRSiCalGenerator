package student.aschm22s.hbrsiCalGenerator.excelImport.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import student.aschm22s.hbrsiCalGenerator.excelImport.domain.HbrsExcelParser;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.service.MeetingService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

@Service
public class ExcelImportService {

    private final HbrsExcelParser hbrsExcelParser;
    private final MeetingService meetingService;

    private final String[] evaLinks = new String[]{"Ah mimimi Evas links verfallen lol."};

    public ExcelImportService(HbrsExcelParser hbrsExcelParser, MeetingService meetingService) {
        this.hbrsExcelParser = hbrsExcelParser;
        this.meetingService = meetingService;
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
