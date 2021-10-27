package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.repository;

import org.springframework.data.repository.CrudRepository;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.models.StundenplanDatumMN;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.models.StundenplanEintrag;

public interface StundenplanDateMNRepo extends CrudRepository<StundenplanDatumMN, Integer> {
    Iterable<StundenplanDatumMN> findByStundenplanEintrag(StundenplanEintrag stundenplanEintrag);

    Iterable<StundenplanDatumMN> findAllByStundenplanEintragOrderByDateAsc(StundenplanEintrag stundenplanEintrag);
}