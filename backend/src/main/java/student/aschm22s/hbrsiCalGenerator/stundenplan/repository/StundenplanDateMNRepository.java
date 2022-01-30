package student.aschm22s.hbrsiCalGenerator.stundenplan.repository;

import org.springframework.data.repository.CrudRepository;
import student.aschm22s.hbrsiCalGenerator.stundenplan.domain.Appointment;
import student.aschm22s.hbrsiCalGenerator.stundenplan.domain.StundenplanEintrag;

public interface StundenplanDateMNRepository extends CrudRepository<Appointment, Integer> {
    Iterable<Appointment> findByStundenplanEintrag(StundenplanEintrag stundenplanEintrag);

    Iterable<Appointment> findAllByStundenplanEintragOrderByDateAsc(StundenplanEintrag stundenplanEintrag);
}