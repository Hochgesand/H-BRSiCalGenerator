package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.hash.Hashing;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Veranstaltung;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "stundenplan")
public class StundenplanEintrag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private LocalDateTime von;
    private LocalDateTime bis;
    private String raum;
    private String tag;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "veranstaltungs_id", nullable = false)
    private Veranstaltung veranstaltung;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StundenplanEintrag that = (StundenplanEintrag) o;
        return Id != null && Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public String getHashWithoutId() {
        var stringBuilder = new StringBuilder();
        stringBuilder.append(von.getHour() + von.getMinute());
        stringBuilder.append(bis.getHour() + bis.getMinute());
        stringBuilder.append(raum);
        stringBuilder.append(tag);
        return Hashing.sha256().hashString(stringBuilder.toString(), StandardCharsets.UTF_8).toString();
    }
}
