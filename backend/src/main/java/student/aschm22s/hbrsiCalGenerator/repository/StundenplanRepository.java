package student.aschm22s.hbrsiCalGenerator.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import student.aschm22s.hbrsiCalGenerator.models.StundenplanEintrag;
import student.aschm22s.hbrsiCalGenerator.models.Veranstaltung;

@Repository
public interface StundenplanRepository extends CrudRepository<StundenplanEintrag, Integer> {
    Iterable<StundenplanEintrag> findByVeranstaltung(Veranstaltung veranstaltung);
}
