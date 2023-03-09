package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonAutoDetect
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingIdsDTO {
    List<Long> meetingIds;
    boolean notrack = false;
}
