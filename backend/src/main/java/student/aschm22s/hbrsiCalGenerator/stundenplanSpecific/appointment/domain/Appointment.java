package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.appointment.domain;

import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.StundenplanEintrag;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Appointment {
    @Id
    private String Id;
    @JsonManagedReference
    private StundenplanEintrag stundenplanEintrag;
    private Date date;

    public void setDate(long date) {
        this.date = new Timestamp(date);
    }

}
