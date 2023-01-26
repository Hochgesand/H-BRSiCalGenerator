package student.aschm22s.hbrsiCalGenerator.configuration;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AbusingIPAddressRepository extends MongoRepository<AbusingIPAddress, String> {
    int countAbusingIPAddressByIpAddress(String ip);
}
