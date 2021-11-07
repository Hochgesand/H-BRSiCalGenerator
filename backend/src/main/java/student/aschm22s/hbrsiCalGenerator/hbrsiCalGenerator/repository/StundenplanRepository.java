package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.models.StundenplanEintrag;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.models.Veranstaltung;

@Repository
public interface StundenplanRepository extends CrudRepository<StundenplanEintrag, Integer> {
    Iterable<StundenplanEintrag> findByVeranstaltung(Veranstaltung veranstaltung);
}
