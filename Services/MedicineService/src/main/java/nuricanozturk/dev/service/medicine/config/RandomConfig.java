package nuricanozturk.dev.service.medicine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class RandomConfig
{
    @Bean
    public Random provideSecureRandom()
    {
        return new SecureRandom();
    }
}
