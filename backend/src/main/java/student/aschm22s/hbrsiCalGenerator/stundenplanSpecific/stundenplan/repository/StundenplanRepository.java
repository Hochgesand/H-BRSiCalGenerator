package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.StundenplanEintrag;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Veranstaltung;

import java.util.List;

@Repository
public interface StundenplanRepository extends CrudRepository<StundenplanEintrag, Integer> {
    List<StundenplanEintrag> findByVeranstaltung(Veranstaltung veranstaltung);

    List<StundenplanEintrag> findAllByVeranstaltung(Veranstaltung veranstaltung);
}
