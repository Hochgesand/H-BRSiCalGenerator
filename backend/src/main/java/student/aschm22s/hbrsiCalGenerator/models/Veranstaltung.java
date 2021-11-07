package student.aschm22s.hbrsiCalGenerator.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "veranstaltung")
public class Veranstaltung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String prof;
    private String studienGangSemester;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "veranstaltung")
    private Collection<StundenplanEintrag> stundenplanEintrags;

    public Veranstaltung(String name, String prof, String studienGangSemester) {
        this.name = name;
        this.prof = prof;
        this.studienGangSemester = studienGangSemester;
    }

    public Veranstaltung() {

    }

    public String getStudienGangSemester() {
        return studienGangSemester;
    }

    public void setStudienGangSemester(String studienGangSemester) {
        this.studienGangSemester = studienGangSemester;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }
}
