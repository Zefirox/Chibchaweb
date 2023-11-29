package co.edu.unbosque.chibchawebbackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfig {

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private final UserAuthenticationProvider userAuthenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)
                .and()
                .addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests((requests) -> requests
                        //Test "/**/auth/**" to manage better endpoints

                        .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/register", "/api/payment/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/basic-data/**", "/api/domains/**", "/api/users/**", "/api/package/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/filter-data/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/register", "/api/payment/**","/api/package/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/basic-data/**", "/api/tickets/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/register", "/api/payments/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/basic-data/**", "/api/domains/**", "/api/users/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/filter-data/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/basic-data/**", "/api/tickets/**","/api/payments/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/service/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/tickets/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/tickets/**","/api/domains/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/filter-data").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/payments/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
        ;
        return http.build();
    }

}
