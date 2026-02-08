package ee.svekko.eventmgr.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.security.user")
public class SecurityUserConfig {
    private String name;

    private String password;

    private String[] roles;
}
