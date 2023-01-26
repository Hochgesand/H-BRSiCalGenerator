package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain.Studiengang;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Veranstaltung;

import java.util.List;

@Repository
public interface VeranstaltungsRepository extends MongoRepository<Veranstaltung, String> {
    List<Veranstaltung> findByIdIn(List<String> id);

    List<Veranstaltung> findAllByProfContaining(String prof);

    List<Veranstaltung> findAllByStudiengang(Studiengang studiengang);

    Veranstaltung findFirstByNameAndStudiengang(String name, Studiengang studiengang);

    void deleteAllByStudiengangAndSemester(Studiengang studiengang, Integer semester);

    void deleteAllByStudiengang(Studiengang studiengang);

    List<Veranstaltung> findAllByStudiengangAndSemester(Studiengang selectedStudiengang, Integer semester);
}
