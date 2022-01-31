package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.generatedCals;

import org.springframework.stereotype.Service;

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
