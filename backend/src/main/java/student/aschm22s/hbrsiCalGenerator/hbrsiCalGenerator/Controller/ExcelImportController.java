package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.DBRepo.VeranstaltungsRepo;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Service.HbrsExcelParser;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.Veranstaltung;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExcelImportController {

    @Autowired
    HbrsExcelParser hbrsExcelParser;
    @Autowired
    VeranstaltungsRepo veranstaltungsRepo;

    @Value("${upload.key}")
    private String userBucketPath;

    private String[] evaLinks = new String[]{
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

    @RequestMapping(value = "/uploadFile", method = POST)
    public String submitExcelFileToImport(@RequestParam("files") MultipartFile[] files, @RequestParam("key") String key, ModelMap modelMap) throws IOException {
        if (!key.equals(userBucketPath))
            return "Could not verify uploadkey";

        modelMap.addAttribute("file", files);
        ArrayList<InputStream> inputstreams = new ArrayList<>();
        for (MultipartFile y :
                files) {
            inputstreams.add(y.getInputStream());
        }

        return hbrsExcelParser.startParser(inputstreams);
    }

    @RequestMapping(value = "/webscrapeEva", method = POST)
    public String updateStundenplaene(@RequestParam("key") String key) throws IOException {
        if (!key.equals(userBucketPath))
            return "Could not verify updateKey";

        veranstaltungsRepo.deleteAll();

        ArrayList<InputStream> inputstreams = new ArrayList<>();
        for (String string :
                evaLinks) {
            ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(string).openStream());
            inputstreams.add(Channels.newInputStream(readableByteChannel));
        }

        return hbrsExcelParser.startParser(inputstreams);
    }
}
