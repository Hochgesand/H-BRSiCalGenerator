package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.filter;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.models.AbusingIPAddress;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.repository.AbusingIPAddressRepository;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class RequestThrottleFilter implements Filter {

    private final AbusingIPAddressRepository abusingIPAddressRepo;

    private final int MAX_REQUESTS_PER_MINUTE = 5;

    private final LoadingCache<String, Integer> requestCountsPerIpAddress;

    public RequestThrottleFilter(AbusingIPAddressRepository abusingIPAddressRepo) {
        requestCountsPerIpAddress = CacheBuilder.newBuilder().
                expireAfterWrite(60, TimeUnit.SECONDS).build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
        this.abusingIPAddressRepo = abusingIPAddressRepo;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String clientIpAddress = getClientIP((HttpServletRequest) request);
        if (isMaximumRequestsPerMinuteExceeded(clientIpAddress)) {
            httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpServletResponse.getWriter().write("Too many requests");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private boolean isMaximumRequestsPerMinuteExceeded(String clientIpAddress) {
        int requests = 0;
        try {
            if (abusingIPAddressRepo.countAbusingIPAddressByIpAddress(clientIpAddress) > 10) return true;
            requests = requestCountsPerIpAddress.get(clientIpAddress);
            if (requests > MAX_REQUESTS_PER_MINUTE) {
                requestCountsPerIpAddress.put(clientIpAddress, requests);
                AbusingIPAddress abusingIPAddress = new AbusingIPAddress();
                abusingIPAddress.setTimestamp(new Timestamp(DateTime.now().getMillis()));
                abusingIPAddress.setIpAddress(clientIpAddress);
                abusingIPAddressRepo.save(abusingIPAddress);
                return true;
            }
        } catch (ExecutionException e) {
            requests = 0;
        }
        requests++;
        requestCountsPerIpAddress.put(clientIpAddress, requests);
        return false;
    }

    public String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
