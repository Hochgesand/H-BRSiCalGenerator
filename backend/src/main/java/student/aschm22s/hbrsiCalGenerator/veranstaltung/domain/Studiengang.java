package student.aschm22s.hbrsiCalGenerator.veranstaltung.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "studiengang")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Studiengang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studiengang", orphanRemoval = true, fetch = FetchType.EAGER)
    Collection<Veranstaltung> veranstaltungen;
}
