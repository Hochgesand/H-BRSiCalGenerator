package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain;

import java.util.List;

public class MeetingIdsAndEmailDTO extends MeetingIdsDTO {
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Long> getMeetingIds() {
        return meetingIds;
    }

    public void setMeetingIds(List<Long> meetingIds) {
        this.meetingIds = meetingIds;
    }
}
