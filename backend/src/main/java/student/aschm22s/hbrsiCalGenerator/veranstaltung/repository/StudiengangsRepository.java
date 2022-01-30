package student.aschm22s.hbrsiCalGenerator.veranstaltung.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import student.aschm22s.hbrsiCalGenerator.veranstaltung.domain.Studiengang;

import java.util.Collection;

public interface StudiengangsRepository extends JpaRepository<Studiengang, Long> {
    Studiengang findFirstByName(String name);
    Studiengang findFirstById(Long id);
    Collection<Studiengang> findAllByNameContaining(String name);
}
