package student.aschm22s.hbrsiCalGenerator.exelImport.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import student.aschm22s.hbrsiCalGenerator.exelImport.service.ExcelImportService;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/excelImport")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExcelImportController {

    private final ExcelImportService excelImportService;
    private final String userBucketPath;

    public ExcelImportController(ExcelImportService excelImportService,
                                 @Value("${upload.key}") String userBucketPath) {
        this.excelImportService = excelImportService;
        this.userBucketPath = userBucketPath;
    }

    @RequestMapping(value = "/force-update", method = POST)
    public String updateStundenplaene(@RequestParam("key") String key) throws IOException {
        if (!key.equals(userBucketPath)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Could not verify uploadkey, now fuck off!");
        }

        return excelImportService.updateStundenplaene();
    }
}
