package student.aschm22s.hbrsiCalGenerator.veranstaltung.domain;

import java.util.List;

public class VeranstaltungsIdsAndEmail extends VeranstaltungsIds {
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getVeranstaltungsIds() {
        return veranstaltungsIds;
    }

    public void setVeranstaltungsIds(List<Integer> veranstaltungsIds) {
        this.veranstaltungsIds = veranstaltungsIds;
    }
}
