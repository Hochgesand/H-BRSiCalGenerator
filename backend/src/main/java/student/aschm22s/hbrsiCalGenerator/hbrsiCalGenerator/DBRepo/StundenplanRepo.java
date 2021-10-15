package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.DBRepo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.StundenplanEintrag;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.Veranstaltung;

@Repository
public interface StundenplanRepo extends CrudRepository<StundenplanEintrag, Integer> {
    Iterable<StundenplanEintrag> findByVeranstaltung(Veranstaltung veranstaltung);
}
