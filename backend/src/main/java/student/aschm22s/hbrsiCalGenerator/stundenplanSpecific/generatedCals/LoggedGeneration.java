package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.generatedCals;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Meeting;

import java.sql.Timestamp;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "logged_generation")
public class LoggedGeneration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String hashedemail;
    private String course;
    private Timestamp timestamp;
    @ManyToMany
    private Collection<Meeting> meetings;
}
