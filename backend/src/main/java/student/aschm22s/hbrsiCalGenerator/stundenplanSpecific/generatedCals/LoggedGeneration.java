package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.generatedCals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoggedGeneration {
    @Id
    private String Id;
    private String hashedemail;
    private String veranstaltungen;
    private String studiengang;
    private Timestamp timestamp;
}
