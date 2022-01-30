package student.aschm22s.hbrsiCalGenerator.veranstaltung.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import student.aschm22s.hbrsiCalGenerator.stundenplan.domain.StundenplanEintrag;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "veranstaltung")
public class Veranstaltung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String prof;
    private Integer semester;
    @ManyToOne
    @JoinColumn(name = "studiengang_id", nullable = false)
    private Studiengang studiengang;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "veranstaltung")
    private Collection<StundenplanEintrag> stundenplanEintrags;
}
