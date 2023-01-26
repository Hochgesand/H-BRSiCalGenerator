package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.appointment.repository;

import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.StundenplanEintrag;
import org.springframework.data.mongodb.repository.MongoRepository;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.appointment.domain.Appointment;

import java.util.List;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {
    List<Appointment> findByStundenplanEintrag(StundenplanEintrag stundenplanEintrag);

    List<Appointment> findAllByStundenplanEintragOrderByDateAsc(StundenplanEintrag stundenplanEintrag);
}
