package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.repository;

import org.springframework.data.repository.CrudRepository;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.models.AbusingIPAddress;

public interface AbusingIPAddressRepository extends CrudRepository<AbusingIPAddress, Integer> {
    int countAbusingIPAddressByIpAddress(String ip);
}
