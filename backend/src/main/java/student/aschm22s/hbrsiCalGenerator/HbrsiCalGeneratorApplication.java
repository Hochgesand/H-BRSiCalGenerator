package student.aschm22s.hbrsiCalGenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableMongoRepositories
public class HbrsiCalGeneratorApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(HbrsiCalGeneratorApplication.class, args);
    }
}
