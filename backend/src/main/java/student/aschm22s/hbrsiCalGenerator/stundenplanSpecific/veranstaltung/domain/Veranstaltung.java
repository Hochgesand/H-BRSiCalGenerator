package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain.Studiengang;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.StundenplanEintrag;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("Veranstaltung")
public class Veranstaltung {
    @Id
    private String id;
    private String name;
    private String prof;
    private Integer semester;
    @JsonManagedReference
    private Studiengang studiengang;
    @JsonBackReference
    private Collection<StundenplanEintrag> stundenplanEintrags;

    @Override
    public String toString() {
        return "Veranstaltung{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", prof='" + prof + '\'' +
                ", semester=" + semester +
                '}';
    }
}
