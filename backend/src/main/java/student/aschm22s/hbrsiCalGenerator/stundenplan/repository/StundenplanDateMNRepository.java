package student.aschm22s.hbrsiCalGenerator.stundenplan.repository;

import org.springframework.data.repository.CrudRepository;
import student.aschm22s.hbrsiCalGenerator.stundenplan.domain.StundenplanDatumMN;
import student.aschm22s.hbrsiCalGenerator.stundenplan.domain.StundenplanEintrag;

public interface StundenplanDateMNRepository extends CrudRepository<StundenplanDatumMN, Integer> {
    Iterable<StundenplanDatumMN> findByStundenplanEintrag(StundenplanEintrag stundenplanEintrag);

    Iterable<StundenplanDatumMN> findAllByStundenplanEintragOrderByDateAsc(StundenplanEintrag stundenplanEintrag);
}