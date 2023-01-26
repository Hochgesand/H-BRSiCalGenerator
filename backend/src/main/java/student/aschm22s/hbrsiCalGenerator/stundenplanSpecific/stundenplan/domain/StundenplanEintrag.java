package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.Id;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.appointment.domain.Appointment;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Veranstaltung;

import java.util.Collection;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StundenplanEintrag {
    @Id
    private String Id;
    private Date von;
    private Date bis;
    private String raum;
    private String tag;
    @JsonBackReference
    private Collection<Appointment> stundenplanDatumMNS;
    @JsonManagedReference
    private Veranstaltung veranstaltung;

}
