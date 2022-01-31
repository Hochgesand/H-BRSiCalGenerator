package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.generatedCals;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Veranstaltung;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoggedGeneration {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String email;
    @ManyToMany
    @JoinTable(
            name = "logged_generation_veranstaltung",
            joinColumns = @JoinColumn(name = "logged_generation_id"),
            inverseJoinColumns = @JoinColumn(name = "veranstaltung_id")
    )
    @JsonBackReference
    private List<Veranstaltung> veranstaltungen;

    @Override
    public String toString() {
        return "LoggedGeneration{" +
                "Id=" + Id +
                ", email='" + email + '\'' +
                '}';
    }
}
