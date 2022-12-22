package student.aschm22s.hbrsiCalGenerator.configuration;

import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

public class AbusingIPAddress {
    @Id
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
