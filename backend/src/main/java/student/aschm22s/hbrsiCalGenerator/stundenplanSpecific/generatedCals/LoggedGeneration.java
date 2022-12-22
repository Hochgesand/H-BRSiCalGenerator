package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.generatedCals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoggedGeneration {
    @org.springframework.data.annotation.Id
    private Long Id;
    private String hashedemail;
    private String veranstaltungen;
    private String studiengang;
    private Timestamp timestamp;
}
