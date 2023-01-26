package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Veranstaltung;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Studiengang {
    @Id
    private String id;
    String name;
    @JsonBackReference
    Collection<Veranstaltung> veranstaltungen;
}
