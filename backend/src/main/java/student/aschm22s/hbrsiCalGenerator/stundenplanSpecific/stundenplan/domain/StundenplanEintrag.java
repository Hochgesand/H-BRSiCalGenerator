package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.Id;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.appointment.domain.Appointment;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Veranstaltung;

import java.sql.Time;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StundenplanEintrag {
    @Id
    private Integer Id;
    private Time von;
    private Time bis;
    private String raum;
    private String tag;
    @JsonBackReference
    private Collection<Appointment> stundenplanDatumMNS;
    @JsonManagedReference
    private Veranstaltung veranstaltung;

}
