package student.aschm22s.hbrsiCalGenerator.veranstaltung.service;

import org.springframework.stereotype.Service;
import student.aschm22s.hbrsiCalGenerator.stundenplan.domain.StundenplanEintrag;
import student.aschm22s.hbrsiCalGenerator.stundenplan.service.StundenplanService;
import student.aschm22s.hbrsiCalGenerator.veranstaltung.domain.Studiengang;
import student.aschm22s.hbrsiCalGenerator.veranstaltung.domain.Veranstaltung;
import student.aschm22s.hbrsiCalGenerator.veranstaltung.repository.StudiengangsRepository;
import student.aschm22s.hbrsiCalGenerator.veranstaltung.repository.VeranstaltungsRepository;

import java.util.Collection;
import java.util.List;

@Service
public class VeranstaltungsService {
    private final VeranstaltungsRepository veranstaltungsRepository;
    private final StundenplanService stundenplanService;
    private final StudiengangsRepository studiengangsRepository;

    public VeranstaltungsService(VeranstaltungsRepository veranstaltungsRepository, StundenplanService stundenplanService, StudiengangsRepository studiengangsRepository) {
        this.veranstaltungsRepository = veranstaltungsRepository;
        this.stundenplanService = stundenplanService;
        this.studiengangsRepository = studiengangsRepository;
    }

    public List<Veranstaltung> findAll() {
        return veranstaltungsRepository.findAll();
    }

    public List<Veranstaltung> findAllByStudiengang(String studiengang) {
        studiengang = studiengang.substring(0, studiengang.length() - 2);
        Studiengang solidStudiengang = studiengangsRepository.findFirstByName(studiengang);
        return veranstaltungsRepository.findAllByStudiengang(solidStudiengang);
    }

    public List<Studiengang> findAllStudiengaenge() {
        return studiengangsRepository.findAll();
    }

    public void deleteAll() {
        veranstaltungsRepository.deleteAll();
    }

    public Object findByIdIn(List<Long> ids) {
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
