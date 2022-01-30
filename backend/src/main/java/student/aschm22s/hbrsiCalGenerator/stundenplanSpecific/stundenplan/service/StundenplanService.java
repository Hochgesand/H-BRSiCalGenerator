package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.service;

import org.springframework.stereotype.Service;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.StundenplanEintrag;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.repository.StundenplanDateMNRepository;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.repository.StundenplanRepository;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Veranstaltung;

@Service
public class StundenplanService {
    private final StundenplanRepository stundenplanRepository;
    private final StundenplanDateMNRepository stundenplanDateMNRepository;

    public StundenplanService(StundenplanRepository stundenplanRepository, StundenplanDateMNRepository stundenplanDateMNRepository) {
        this.stundenplanRepository = stundenplanRepository;
        this.stundenplanDateMNRepository = stundenplanDateMNRepository;
    }

    public Object findByVeranstaltung(Veranstaltung x) {
        return stundenplanRepository.findByVeranstaltung(x);
    }

    public Object findByStundenplanEintrag(StundenplanEintrag x) {
        return stundenplanDateMNRepository.findByStundenplanEintrag(x);
    }

    public Object findAllByStundenplanEintragOrderByDateAsc(StundenplanEintrag x) {
        return stundenplanDateMNRepository.findAllByStundenplanEintragOrderByDateAsc(x);
    }
}
