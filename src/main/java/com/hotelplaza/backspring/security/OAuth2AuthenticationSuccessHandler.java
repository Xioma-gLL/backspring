package com.hotelplaza.backspring.security;

import com.hotelplaza.backspring.entity.Cliente;
import com.hotelplaza.backspring.repository.ClienteRepository;
import com.hotelplaza.backspring.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final ClienteRepository clienteRepository;
    private final JwtService jwtService;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oauthToken.getPrincipal();
        String provider = oauthToken.getAuthorizedClientRegistrationId();
        
        Map<String, Object> attributes = oAuth2User.getAttributes();
        
        final String email;
        final String firstName;
        final String lastName;
        final String photoUrl;
        
        if ("google".equals(provider)) {
            email = (String) attributes.get("email");
            firstName = (String) attributes.get("given_name");
            lastName = (String) attributes.get("family_name");
            photoUrl = (String) attributes.get("picture");
        } else {
            email = (String) attributes.get("email");
            String name = (String) attributes.get("name");
            if (name != null) {
                String[] nameParts = name.split(" ", 2);
                firstName = nameParts[0];
                lastName = nameParts.length > 1 ? nameParts[1] : "";
            } else {
                firstName = "";
                lastName = "";
            }
            photoUrl = (String) attributes.get("picture");
        }
        
        // Buscar o crear usuario
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseGet(() -> {
                    Cliente newCliente = Cliente.builder()
                            .email(email)
                            .firstName(firstName)
                            .lastName(lastName)
                            .password("") // No password for OAuth users
                            .photoUrl(photoUrl)
                            .isActive(true)
                            .build();
                    return clienteRepository.save(newCliente);
                });
        
        // Siempre actualizar la foto desde Google (puede haber cambiado)
        if (photoUrl != null && !photoUrl.equals(cliente.getPhotoUrl())) {
            cliente.setPhotoUrl(photoUrl);
            clienteRepository.save(cliente);
        }
        
        // Generar JWT
        String token = jwtService.generateToken(cliente);
        
        // Codificar los parámetros para la URL
        String encodedPhotoUrl = cliente.getPhotoUrl() != null 
                ? URLEncoder.encode(cliente.getPhotoUrl(), StandardCharsets.UTF_8) 
                : "";
        
        // Redirigir al frontend con el token
        String redirectUrl = frontendUrl + "/oauth-callback?token=" + token 
                + "&email=" + URLEncoder.encode(cliente.getEmail(), StandardCharsets.UTF_8)
                + "&firstName=" + URLEncoder.encode(cliente.getFirstName() != null ? cliente.getFirstName() : "", StandardCharsets.UTF_8)
                + "&lastName=" + URLEncoder.encode(cliente.getLastName() != null ? cliente.getLastName() : "", StandardCharsets.UTF_8)
                + "&photoUrl=" + encodedPhotoUrl;
        
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
