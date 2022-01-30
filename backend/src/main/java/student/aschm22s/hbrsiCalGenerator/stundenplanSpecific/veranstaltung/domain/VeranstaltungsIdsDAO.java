package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

@JsonAutoDetect
public class VeranstaltungsIdsDAO {
    List<Long> veranstaltungsIds;

    public List<Long> getVeranstaltungsIds() {
        return veranstaltungsIds;
    }
}
