package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.appointment.repository;

import org.springframework.data.repository.CrudRepository;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.appointment.domain.Appointment;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.StundenplanEintrag;

import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
    List<Appointment> findByStundenplanEintrag(StundenplanEintrag stundenplanEintrag);

    List<Appointment> findAllByStundenplanEintragOrderByDateAsc(StundenplanEintrag stundenplanEintrag);
}