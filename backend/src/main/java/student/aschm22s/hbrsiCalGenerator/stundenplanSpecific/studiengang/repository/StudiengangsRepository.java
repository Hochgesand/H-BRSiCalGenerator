package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.repository;

import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.studiengang.domain.Studiengang;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StudiengangsRepository extends MongoRepository<Studiengang, String> {
    Studiengang findFirstByNameContaining(String name);

    List<Studiengang> findAllByNameContaining(String name);
}
