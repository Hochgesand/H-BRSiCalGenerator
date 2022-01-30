package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Veranstaltung;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "studiengang")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Studiengang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String name;
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studiengang", orphanRemoval = true, fetch = FetchType.EAGER)
    Collection<Veranstaltung> veranstaltungen;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Studiengang that = (Studiengang) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
