package student.aschm22s.hbrsiCalGenerator.stundenplan.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "termin")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @ManyToOne
    @JoinColumn(name = "stundenplan_eintrag_id", nullable = false)
    private StundenplanEintrag stundenplanEintrag;
    private Timestamp date;

    public Appointment() {
    }

    public Appointment(StundenplanEintrag stundenplanEintrag, Timestamp date) {
        this.stundenplanEintrag = stundenplanEintrag;
        this.date = date;
    }

    public StundenplanEintrag getStundenplanEintrag() {
        return stundenplanEintrag;
    }

    public void setStundenplanEintrag(StundenplanEintrag stundenplanEintrag) {
        this.stundenplanEintrag = stundenplanEintrag;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setDate(long date) {
        this.date = new Timestamp(date);
    }
}
