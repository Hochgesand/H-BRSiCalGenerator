package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.appointment.service;

import org.springframework.stereotype.Service;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.appointment.domain.Appointment;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.StundenplanEintrag;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.appointment.repository.AppointmentRepository;

import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> findByStundenplanEintrag(StundenplanEintrag x) {
        return appointmentRepository.findByStundenplanEintrag(x);
    }

    public List<Appointment> findAllByStundenplanEintragOrderByDateAsc(StundenplanEintrag x) {
        return appointmentRepository.findAllByStundenplanEintragOrderByDateAsc(x);
    }

    public Appointment save(Appointment stundenplanDatumMN) {
        return appointmentRepository.save(stundenplanDatumMN);
    }
}
