package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.DBRepo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.Veranstaltung;

@Repository
public interface VeranstaltungsRepo extends CrudRepository<Veranstaltung, Integer> {
    Iterable<Veranstaltung> findByName(String name);
    Iterable<Veranstaltung> findByStudienGangSemester(String studienGangSemester);
    Iterable<Veranstaltung> findByIdIn(Iterable<Integer> Ids);
    Veranstaltung findFirstByNameAndStudienGangSemester(String name, String studienGangSemester);
    void deleteAllByStudienGangSemester(String studienGangSemester);
}
