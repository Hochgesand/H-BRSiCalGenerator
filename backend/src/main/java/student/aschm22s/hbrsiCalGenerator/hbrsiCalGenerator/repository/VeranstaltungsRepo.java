package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.models.Veranstaltung;

@Repository
public interface VeranstaltungsRepo extends JpaRepository<Veranstaltung, Integer> {
    Iterable<Veranstaltung> findByName(String name);

    Iterable<Veranstaltung> findByStudienGangSemester(String studienGangSemester);

    Iterable<Veranstaltung> findByIdIn(Iterable<Integer> Ids);

    Veranstaltung findFirstByNameAndStudienGangSemester(String name, String studienGangSemester);

    void deleteAllByStudienGangSemester(String studienGangSemester);
}
