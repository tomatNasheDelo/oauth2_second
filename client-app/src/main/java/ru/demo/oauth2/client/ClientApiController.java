package ru.demo.oauth2.client;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Map;

@RestController
public class ClientApiController {
    private final RestClient resourceServerClient;

    public ClientApiController(RestClient resourceServerClient) {
        this.resourceServerClient = resourceServerClient;
    }

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
                "purpose", "OAuth2 Client logs a user in and calls the protected Resource Server",
                "startLogin", "/oauth2/authorization/keycloak",
                "afterLogin", "/api/me");
    }

    @GetMapping("/api/me")
    public Map<String, Object> me(@AuthenticationPrincipal OAuth2User user) {
        return Map.of(
                "clientRole", "OAuth2 Client",
                "subject", user.getName(),
                "claims", user.getAttributes());
    }

    @GetMapping("/api/call-resource")
    public Map<?, ?> callResourceServer(
            @RegisteredOAuth2AuthorizedClient("keycloak") OAuth2AuthorizedClient authorizedClient) {
        String accessToken = authorizedClient.getAccessToken().getTokenValue();
        return resourceServerClient.get()
                .uri("/api/user/me")
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .body(Map.class);
    }
}
