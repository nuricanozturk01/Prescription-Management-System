package nuricanozturk.dev.service.prescription.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig
{
    @Bean
    @Scope("singleton")
    public PasswordEncoder providePasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}