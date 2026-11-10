package ru.demo.oauth2.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfiguration {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/error").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2.successHandler((request, response, authentication) -> {
                    response.setStatus(200);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"status\":\"logged_in\",\"next\":\"/api/me\"}");
                }))
                .logout(Customizer.withDefaults())
                .build();
    }

    @Bean
    RestClient resourceServerClient(@Value("${app.resource-server-url}") String baseUrl) {
        return RestClient.builder().baseUrl(baseUrl).build();
    }
}
