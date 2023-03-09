package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.generatedCals;

import org.springframework.stereotype.Service;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.Meeting;

import java.util.List;

@Service
public class TrackService {
    private final LoggedGenerationRepository loggedGenerationRepository;

    public TrackService(LoggedGenerationRepository loggedGenerationRepository) {
        this.loggedGenerationRepository = loggedGenerationRepository;
    }

    public LoggedGeneration generateLogEntry(LoggedGeneration loggedGeneration) {
        loggedGenerationRepository.save(loggedGeneration);
        return loggedGeneration;
    }
}
