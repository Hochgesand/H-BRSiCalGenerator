package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain;

import java.util.List;

public class VeranstaltungsIdsAndEmailDTO extends VeranstaltungsIdsDTO {
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Long> getVeranstaltungsIds() {
        return veranstaltungsIds;
    }

    public void setVeranstaltungsIds(List<Long> veranstaltungsIds) {
        this.veranstaltungsIds = veranstaltungsIds;
    }
}
