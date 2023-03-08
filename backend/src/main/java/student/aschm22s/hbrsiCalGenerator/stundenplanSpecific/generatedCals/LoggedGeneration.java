package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.generatedCals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain.Studiengang;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoggedGeneration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String hashedemail;
    @Lob
    private String veranstaltungen;
    private String studiengang;
    private Timestamp timestamp;
}
