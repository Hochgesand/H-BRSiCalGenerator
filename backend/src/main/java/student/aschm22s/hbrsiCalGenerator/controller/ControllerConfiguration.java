package student.aschm22s.hbrsiCalGenerator.controller;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ControllerConfiguration {

    @Bean
    public RateLimiter rateLimiter() {
        return RateLimiter.create(0.6, Duration.ofSeconds(30));
    }
}
