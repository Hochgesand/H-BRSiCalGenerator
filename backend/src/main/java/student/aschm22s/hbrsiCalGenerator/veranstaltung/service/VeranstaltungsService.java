package student.aschm22s.hbrsiCalGenerator.veranstaltung.service;

import org.springframework.stereotype.Service;
import student.aschm22s.hbrsiCalGenerator.stundenplan.domain.StundenplanEintrag;
import student.aschm22s.hbrsiCalGenerator.stundenplan.service.StundenplanService;
import student.aschm22s.hbrsiCalGenerator.veranstaltung.domain.Veranstaltung;
import student.aschm22s.hbrsiCalGenerator.veranstaltung.repository.VeranstaltungsRepository;

import java.util.List;

@Service
public class VeranstaltungsService {
    private final VeranstaltungsRepository veranstaltungsRepository;
    private final StundenplanService stundenplanService;

    public VeranstaltungsService(VeranstaltungsRepository veranstaltungsRepository, StundenplanService stundenplanService) {
        this.veranstaltungsRepository = veranstaltungsRepository;
        this.stundenplanService = stundenplanService;
    }

    public List<Veranstaltung> findAll() {
        return veranstaltungsRepository.findAll();
    }

    public void deleteAll() {
        veranstaltungsRepository.deleteAll();
    }

    public Object findByIdIn(List<Integer> ids) {
        return veranstaltungsRepository.findByIdIn(ids);
    }

    public Object findByVeranstaltung(Veranstaltung x) {
        return stundenplanService.findByVeranstaltung(x);
    }

    public Object findByStundenplanEintrag(StundenplanEintrag x) {
        return stundenplanService.findByStundenplanEintrag(x);
    }

    public Object findAllByStundenplanEintragOrderByDateAsc(StundenplanEintrag x) {
        return stundenplanService.findAllByStundenplanEintragOrderByDateAsc(x);
    }
}
