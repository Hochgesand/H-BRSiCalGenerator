package student.aschm22s.hbrsiCalGenerator.veranstaltung.domain;

import java.util.List;

public class VeranstaltungsIdsAndEmailDAO extends VeranstaltungsIdsDAO {
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
