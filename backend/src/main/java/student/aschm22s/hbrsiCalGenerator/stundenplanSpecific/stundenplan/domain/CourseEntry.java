package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.hash.Hashing;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Meeting;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "courseEntry")
public class CourseEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private LocalDateTime start;
    private LocalDateTime end;
    private String room;
    private String dayString;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CourseEntry that = (CourseEntry) o;
        return Id != null && Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public String getHashWithoutId() {
        String stringBuilder = String.valueOf(start.getHour() + start.getMinute()) +
                (end.getHour() + end.getMinute()) +
                room +
                dayString;
        return Hashing.sha256().hashString(stringBuilder, StandardCharsets.UTF_8).toString();
    }
}
