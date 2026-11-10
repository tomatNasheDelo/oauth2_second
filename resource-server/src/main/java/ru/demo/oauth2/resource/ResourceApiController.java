package ru.demo.oauth2.resource;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ResourceApiController {
    @GetMapping("/api/public")
    public Map<String, String> publicEndpoint() {
        return Map.of("message", "This endpoint is public; no access token required.");
    }

    @GetMapping("/api/user/me")
    public Map<String, Object> userEndpoint(@AuthenticationPrincipal Jwt jwt) {
        return Map.of(
                "message", "JWT accepted by the Resource Server",
                "subject", jwt.getSubject(),
                "issuer", jwt.getIssuer().toString(),
                "roles", jwt.getClaimAsMap("realm_access").get("roles"));
    }

    @GetMapping("/api/admin/hello")
    public Map<String, String> adminEndpoint() {
        return Map.of("message", "You have the admin role.");
    }
}
