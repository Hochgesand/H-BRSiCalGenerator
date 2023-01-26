package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain;

import java.util.List;

public class VeranstaltungsIdsAndEmailDTO extends VeranstaltungsIdsDTO {
    String email;

    public String getEmail() {
        return email;
    }

    public List<String> getVeranstaltungsIds() {
        return veranstaltungsIds;
    }
}
