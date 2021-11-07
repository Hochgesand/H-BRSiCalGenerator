package student.aschm22s.hbrsiCalGenerator.stundenplan.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import student.aschm22s.hbrsiCalGenerator.stundenplan.domain.StundenplanEintrag;
import student.aschm22s.hbrsiCalGenerator.veranstaltung.domain.Veranstaltung;

@Repository
public interface StundenplanRepository extends CrudRepository<StundenplanEintrag, Integer> {
    Iterable<StundenplanEintrag> findByVeranstaltung(Veranstaltung veranstaltung);
}
