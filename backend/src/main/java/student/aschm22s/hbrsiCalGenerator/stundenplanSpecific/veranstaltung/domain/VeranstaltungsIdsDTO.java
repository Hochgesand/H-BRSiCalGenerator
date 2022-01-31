package student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.List;

@JsonAutoDetect
@Data
public class VeranstaltungsIdsDTO {
    List<Long> veranstaltungsIds;
    boolean notrack = false;
}
