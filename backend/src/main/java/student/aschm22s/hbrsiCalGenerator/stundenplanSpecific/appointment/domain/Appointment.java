package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.appointment.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.Id;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.StundenplanEintrag;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Appointment {
    @Id
    private Integer Id;
    @JsonManagedReference
    private StundenplanEintrag stundenplanEintrag;
    private Timestamp date;

    public void setDate(long date) {
        this.date = new Timestamp(date);
    }

}
