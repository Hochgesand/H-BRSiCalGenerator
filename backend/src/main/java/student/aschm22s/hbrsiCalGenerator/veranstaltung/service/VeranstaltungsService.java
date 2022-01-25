package student.aschm22s.hbrsiCalGenerator.veranstaltung.service;

import org.springframework.stereotype.Service;
import student.aschm22s.hbrsiCalGenerator.stundenplan.domain.StundenplanEintrag;
import student.aschm22s.hbrsiCalGenerator.stundenplan.service.StundenplanService;
import student.aschm22s.hbrsiCalGenerator.veranstaltung.domain.Veranstaltung;
import student.aschm22s.hbrsiCalGenerator.veranstaltung.repository.VeranstaltungsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class VeranstaltungsService {
    private final VeranstaltungsRepository veranstaltungsRepository;
    private final StundenplanService stundenplanService;
    List<String> studiengaenge = new ArrayList<>();

    public VeranstaltungsService(VeranstaltungsRepository veranstaltungsRepository, StundenplanService stundenplanService) {
        this.veranstaltungsRepository = veranstaltungsRepository;
        this.stundenplanService = stundenplanService;
    }

    public List<Veranstaltung> findAll() {
        return veranstaltungsRepository.findAll();
    }

    public List<Veranstaltung> findAllByStudiengang(String studiengang) {
        studiengang = studiengang.substring(0, studiengang.length()-2);
        return veranstaltungsRepository.findAllByStudienGangSemesterContaining(studiengang);
    }

    public List<String> findAllStudiengaenge() {
        if (studiengaenge.size() != 0)
            return studiengaenge;

        Iterable<Veranstaltung> veranstaltungen = veranstaltungsRepository.findAll();

        for (Veranstaltung x :
                veranstaltungen) {
            x.setStudienGangSemester(x.getStudienGangSemester().substring(0, x.getStudienGangSemester().length() - 2));

            if (!studiengaenge.contains(x.getStudienGangSemester())){
                studiengaenge.add(x.getStudienGangSemester());
            }
        }

        return studiengaenge;
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
