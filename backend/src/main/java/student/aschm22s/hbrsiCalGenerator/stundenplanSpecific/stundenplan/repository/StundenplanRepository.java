package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.StundenplanEintrag;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Veranstaltung;

import java.util.List;

@Repository
public interface StundenplanRepository extends MongoRepository<StundenplanEintrag, Integer> {
    List<StundenplanEintrag> findByVeranstaltung(Veranstaltung veranstaltung);

    List<StundenplanEintrag> findAllByVeranstaltung(Veranstaltung veranstaltung);
}
