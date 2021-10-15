package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.DBRepo;

import org.springframework.data.repository.CrudRepository;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.StundenplanDatumMN;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.StundenplanEintrag;

public interface StundenplanDateMNRepo extends CrudRepository<StundenplanDatumMN, Integer> {
    Iterable<StundenplanDatumMN> findByStundenplanEintrag(StundenplanEintrag stundenplanEintrag);
}
