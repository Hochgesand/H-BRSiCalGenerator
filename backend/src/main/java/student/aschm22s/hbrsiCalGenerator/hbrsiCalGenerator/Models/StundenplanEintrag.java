package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models;

import javax.persistence.*;
import java.sql.Time;
import java.util.Collection;

@Entity
@Table(name = "stundenplan")
public class StundenplanEintrag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private Time von;
    private Time bis;
    private String raum;
    private String tag;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "stundenplanEintrag")
    private Collection<StundenplanDatumMN> stundenplanDatumMNS;
    @ManyToOne
    @JoinColumn(name = "veranstaltungs_id", nullable = false)
    private Veranstaltung veranstaltung;

    public StundenplanEintrag() {
    }

    public StundenplanEintrag(Time von, Time bis, String raum, Veranstaltung veranstaltung, String tag) {
        this.von = von;
        this.bis = bis;
        this.raum = raum;
        this.tag = tag;
        this.veranstaltung = veranstaltung;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getId() {
        return Id;
    }

    public Time getVon() {
        return von;
    }

    public void setVon(Time von) {
        this.von = von;
    }

    public Time getBis() {
        return bis;
    }

    public void setBis(Time bis) {
        this.bis = bis;
    }

    public String getRaum() {
        return raum;
    }

    public void setRaum(String raum) {
        this.raum = raum;
    }

    public Veranstaltung getVeranstaltung() {
        return veranstaltung;
    }

    public void setVeranstaltung(Veranstaltung veranstaltung) {
        this.veranstaltung = veranstaltung;
    }
}
