package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "stundenplanDatumMN")
public class StundenplanDatumMN {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @ManyToOne
    @JoinColumn(name = "stundenplan_eintrag_id", nullable = false)
    private StundenplanEintrag stundenplanEintrag;
    private Timestamp date;

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

    public StundenplanDatumMN() {
    }

    public StundenplanDatumMN(StundenplanEintrag stundenplanEintrag, Timestamp date) {
        this.stundenplanEintrag = stundenplanEintrag;
        this.date = date;
    }
}
