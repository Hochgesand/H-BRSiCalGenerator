package student.aschm22s.hbrsiCalGenerator.configuration;

import org.springframework.data.repository.CrudRepository;

public interface AbusingIPAddressRepository extends CrudRepository<AbusingIPAddress, Integer> {
    int countAbusingIPAddressByIpAddress(String ip);
}
