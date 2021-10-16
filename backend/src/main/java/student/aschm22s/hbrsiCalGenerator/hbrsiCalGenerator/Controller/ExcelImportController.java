package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Service.HbrsExcelParser;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.Veranstaltung;

import java.io.IOException;
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

    @Value("${upload.key}")
    private String userBucketPath;

    @RequestMapping(value = "/uploadFile", method = POST)
    public String submitExcelFileToImport(@RequestParam("files") MultipartFile[] files, @RequestParam("key") String key, ModelMap modelMap) throws IOException {
        if (!key.equals(userBucketPath))
            return "Could not verify uploadkey";

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        modelMap.addAttribute("file", files);

        AtomicInteger counterPlaene = new AtomicInteger();

        for (MultipartFile x : files) {
            executorService.submit(() -> {
                ArrayList<Veranstaltung> veranstaltungen = new ArrayList<>();
                try {
                    veranstaltungen = hbrsExcelParser.parseVeranstaltungen(x.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    hbrsExcelParser.parseStundenplan(x.getInputStream());
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

        if(counterPlaene.get() != files.length){
            return "Es gab einen Fehler beim importieren. " + counterPlaene.get() + " von" + files.length + " wurden importiert";
        }

        return "Alle " + files.length + " Stundenpläne wurden importiert";
    }
}
