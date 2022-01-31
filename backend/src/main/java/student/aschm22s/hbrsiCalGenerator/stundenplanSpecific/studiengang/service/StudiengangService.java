package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.service;

import org.springframework.stereotype.Service;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain.Studiengang;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.repository.StudiengangsRepository;

import java.util.List;

@Service
public class StudiengangService {
    private final StudiengangsRepository studiengangsRepository;

    public StudiengangService(StudiengangsRepository studiengangsRepository) {
        this.studiengangsRepository = studiengangsRepository;
    }

    public List<Studiengang> findAllStudiengaenge() {
        return studiengangsRepository.findAll();
    }

    public Studiengang findFirstByNameContaining(String name) {
        return studiengangsRepository.findFirstByNameContaining(name);
    }

    public Studiengang findByNameIfNotExistCreate(String name) {
        Studiengang studiengang = studiengangsRepository.findFirstByNameContaining(name);
        if (studiengang == null) {
            studiengang = new Studiengang();
            studiengang.setName(name);
            studiengangsRepository.save(studiengang);
        }
        return studiengang;
    }

    public List<Studiengang> findAllByNameContaining(String substring) {
        return studiengangsRepository.findAllByNameContaining(substring);
    }

    public Studiengang findById(Long studiengang) {
        return studiengangsRepository.findById(studiengang).orElse(new Studiengang());
    }
}
