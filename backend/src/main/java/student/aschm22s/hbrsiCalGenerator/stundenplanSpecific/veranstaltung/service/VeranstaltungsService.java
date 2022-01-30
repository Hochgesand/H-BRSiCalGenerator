package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.service;

import org.springframework.stereotype.Service;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.service.StudiengangService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.domain.StundenplanEintrag;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.stundenplan.service.StundenplanService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain.Studiengang;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.repository.VeranstaltungsRepository;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Veranstaltung;

import java.util.ArrayList;
import java.util.List;

@Service
public class VeranstaltungsService {
    private final VeranstaltungsRepository veranstaltungsRepository;

    private final StundenplanService stundenplanService;
    private final StudiengangService studiengangService;

    public VeranstaltungsService(VeranstaltungsRepository veranstaltungsRepository, StundenplanService stundenplanService, StudiengangService studiengangService) {
        this.veranstaltungsRepository = veranstaltungsRepository;
        this.stundenplanService = stundenplanService;
        this.studiengangService = studiengangService;
    }

    public List<Veranstaltung> findAll() {
        return veranstaltungsRepository.findAll();
    }

    public List<Veranstaltung> findAllByStudiengang(String studiengang) {
        studiengang = studiengang.substring(0, studiengang.length() - 2);
        Studiengang solidStudiengang = studiengangService.findFirstByNameContaining(studiengang);
        return veranstaltungsRepository.findAllByStudiengang(solidStudiengang);
    }

    public void deleteAll(List<Veranstaltung> veranstaltungen) {
        for (Veranstaltung veranstaltung : veranstaltungen) {
             List<StundenplanEintrag> stundenplanEintraege = stundenplanService.findAllByVeranstaltung(veranstaltung);
             stundenplanService.deleteAll(stundenplanEintraege);
        }
        veranstaltungsRepository.deleteAll(veranstaltungen);
    }

    public void deleteAll() {
        List<Veranstaltung> veranstaltungen = findAll();
        for (Veranstaltung veranstaltung : veranstaltungen) {
             List<StundenplanEintrag> stundenplanEintraege = stundenplanService.findAllByVeranstaltung(veranstaltung);
             stundenplanService.deleteAll(stundenplanEintraege);
        }
        veranstaltungsRepository.deleteAll(veranstaltungen);
    }

    public List<Veranstaltung> findByIdsIn(List<Long> ids) {
        return veranstaltungsRepository.findByIdIn(ids);
    }

    public List<StundenplanEintrag> findByVeranstaltung(Veranstaltung x) {
        return stundenplanService.findByVeranstaltung(x);
    }

    public List<Veranstaltung> findAllByStudiengangAndSemester(Studiengang studiengang, int semester) {
        return veranstaltungsRepository.findAllByStudiengangAndSemester(studiengang, semester);
    }

    public Veranstaltung findFirstByNameAndStudiengang(String tempModulName, Studiengang studiengang) {
        return veranstaltungsRepository.findFirstByNameAndStudiengang(tempModulName, studiengang);
    }

    public void saveAll(ArrayList<Veranstaltung> veranstaltungsListe) {
        veranstaltungsRepository.saveAll(veranstaltungsListe);
    }
}
