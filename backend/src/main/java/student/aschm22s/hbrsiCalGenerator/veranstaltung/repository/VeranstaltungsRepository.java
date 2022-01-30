package student.aschm22s.hbrsiCalGenerator.veranstaltung.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import student.aschm22s.hbrsiCalGenerator.veranstaltung.domain.Studiengang;
import student.aschm22s.hbrsiCalGenerator.veranstaltung.domain.Veranstaltung;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeranstaltungsRepository extends JpaRepository<Veranstaltung, Integer> {
    Iterable<Veranstaltung> findByIdIn(List<Long> id);

    List<Veranstaltung> findAllByStudiengang(Studiengang studiengang);

    Veranstaltung findFirstByNameAndStudiengang(String name, Studiengang studiengang);
    void deleteAllByStudiengangAndSemester(Studiengang studiengang, Integer semester);

    void deleteAllByStudiengang(Studiengang studiengang);
}
