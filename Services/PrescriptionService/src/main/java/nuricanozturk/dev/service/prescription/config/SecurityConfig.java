package nuricanozturk.dev.service.prescription.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig
{
    private final JwtAuthFilter m_jwtAuthFilter;
    private final PharmacyAuthenticationProvider m_authenticationProvider;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, PharmacyAuthenticationProvider authenticationProvider)
    {
        m_jwtAuthFilter = jwtAuthFilter;
        m_authenticationProvider = authenticationProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception
    {
        security.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               // .cors(corsCustomizer -> corsCustomizer.configurationSource(this::corsConfigurer))
                .csrf(AbstractHttpConfigurer::disable);

        return security
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(this::authorizationRequests)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(m_authenticationProvider)
                .addFilterBefore(m_jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }


    private void authorizationRequests(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry requests)
    {
        requests.requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("api/v2/**").permitAll()
                .requestMatchers("/api/v1/**").authenticated()  ;
    }

    private CorsConfiguration setCorsConfig(HttpServletRequest httpServletRequest)
    {
        CorsConfiguration config = new CorsConfiguration();
        //config.setAllowedOriginPatterns("*");
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setMaxAge(3600L);
        return config;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowCredentials(true);
                config.addAllowedOrigin("*"); // Tüm kaynaklardan gelen istekleri kabul etmek için "*"
                config.addAllowedHeader("*"); // Tüm HTTP başlıklarını kabul etmek için "*"
                config.addAllowedMethod("*"); // Tüm HTTP metotlarını kabul etmek için "*"
                config.addExposedHeader("Authorization");

                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }
}