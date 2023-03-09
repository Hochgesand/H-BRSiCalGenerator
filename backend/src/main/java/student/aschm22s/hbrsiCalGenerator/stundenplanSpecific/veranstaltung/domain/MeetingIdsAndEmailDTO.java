package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
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
