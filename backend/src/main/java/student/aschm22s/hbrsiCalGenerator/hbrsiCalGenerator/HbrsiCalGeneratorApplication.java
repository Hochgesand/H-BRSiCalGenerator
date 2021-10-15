package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Service.HbrsExcelParser;

import java.io.IOException;

@SpringBootApplication
public class HbrsiCalGeneratorApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(HbrsiCalGeneratorApplication.class, args);
    }
}
