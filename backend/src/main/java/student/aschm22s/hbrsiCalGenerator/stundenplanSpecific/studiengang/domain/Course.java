package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Meeting;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "course")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    String name;
    @JsonBackReference
    @OneToMany(mappedBy = "course")
    Collection<Meeting> meetings;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Course that = (Course) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
