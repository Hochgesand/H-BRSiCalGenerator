package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.service;

import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Veranstaltung;
import org.springframework.stereotype.Service;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.StundenplanEintrag;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.repository.StundenplanRepository;

import java.util.List;

@Service
public class StundenplanService {
    private final StundenplanRepository stundenplanRepository;

    public StundenplanService(StundenplanRepository stundenplanRepository) {
        this.stundenplanRepository = stundenplanRepository;
    }

    public List<StundenplanEintrag> findByVeranstaltung(Veranstaltung x) {
        return stundenplanRepository.findByVeranstaltung(x);
    }

    public List<StundenplanEintrag> findAllByVeranstaltung(Veranstaltung veranstaltung) {
        return stundenplanRepository.findAllByVeranstaltung(veranstaltung);
    }

    public void deleteAll(List<StundenplanEintrag> stundenplanEintraege) {
        stundenplanRepository.deleteAll(stundenplanEintraege);
    }

    public List<StundenplanEintrag> findAll() {
        return (List<StundenplanEintrag>) stundenplanRepository.findAll();
    }

    public StundenplanEintrag save(StundenplanEintrag neuerStundenplanEintrag) {
        return stundenplanRepository.save(neuerStundenplanEintrag);
    }
}
