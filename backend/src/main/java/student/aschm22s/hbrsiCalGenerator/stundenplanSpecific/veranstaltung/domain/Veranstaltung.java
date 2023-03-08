package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain.Studiengang;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.StundenplanEintrag;

import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "veranstaltung")
@Data
public class Veranstaltung {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String prof;
    private Integer semester;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "studiengang_id", nullable = false)
    private Studiengang studiengang;
    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "veranstaltung")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Veranstaltung that = (Veranstaltung) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
