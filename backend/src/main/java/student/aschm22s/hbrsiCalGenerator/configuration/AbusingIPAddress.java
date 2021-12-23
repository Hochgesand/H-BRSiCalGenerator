package student.aschm22s.hbrsiCalGenerator.configuration;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "AbusingIPAdresses")
public class AbusingIPAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private Timestamp timestamp;
    private String ipAddress;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
