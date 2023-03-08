package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain.Course;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.CourseEntry;

import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "meeting")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String professor;
    private Integer semester;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "meeting")
    private Collection<CourseEntry> courseEntries;

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", prof='" + professor + '\'' +
                ", semester=" + semester +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Meeting that = (Meeting) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
