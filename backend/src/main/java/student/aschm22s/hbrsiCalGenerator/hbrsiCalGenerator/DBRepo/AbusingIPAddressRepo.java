package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.DBRepo;

import org.springframework.data.repository.CrudRepository;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.AbusingIPAddress;

public interface AbusingIPAddressRepo extends CrudRepository<AbusingIPAddress, Integer> {
    int countAbusingIPAddressByIpAddress(String ip);
}
