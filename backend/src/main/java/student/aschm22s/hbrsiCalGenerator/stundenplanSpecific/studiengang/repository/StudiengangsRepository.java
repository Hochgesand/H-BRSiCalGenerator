package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain.Studiengang;

import java.util.List;

public interface StudiengangsRepository extends JpaRepository<Studiengang, Long> {
    Studiengang findFirstByNameContaining(String name);

    List<Studiengang> findAllByNameContaining(String name);
}
