package student.aschm22s.hbrsiCalGenerator.service;

import org.springframework.stereotype.Service;
import student.aschm22s.hbrsiCalGenerator.models.Veranstaltung;
import student.aschm22s.hbrsiCalGenerator.repository.VeranstaltungsRepository;

import java.util.List;

@Service
public class VeranstaltungsService {
    private final VeranstaltungsRepository veranstaltungsRepository;

    public VeranstaltungsService(VeranstaltungsRepository veranstaltungsRepository) {
        this.veranstaltungsRepository = veranstaltungsRepository;
    }

    public List<Veranstaltung> findAll() {
        return veranstaltungsRepository.findAll();
    }

    public void deleteAll() {
        veranstaltungsRepository.deleteAll();
    }
}
