package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Studiengang;

import java.util.Collection;

public interface StudiengangsRepository extends JpaRepository<Studiengang, Long> {
    Studiengang findFirstByNameContaining(String name);
    Studiengang findFirstById(Long id);
    Collection<Studiengang> findAllByNameContaining(String name);
}
