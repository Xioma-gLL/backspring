package com.hotelplaza.backspring.service;

import com.hotelplaza.backspring.dto.AuthResponse;
import com.hotelplaza.backspring.dto.LoginRequest;
import com.hotelplaza.backspring.dto.RegisterRequest;
import com.hotelplaza.backspring.entity.Cliente;
import com.hotelplaza.backspring.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {
    
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public AuthResponse register(RegisterRequest request) {
        // Verificar si el email ya existe
        if (clienteRepository.existsByEmail(request.getEmail())) {
            return AuthResponse.builder()
                    .success(false)
                    .message("El email ya está registrado")
                    .build();
        }
        
        // Crear nuevo cliente
        Cliente cliente = Cliente.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phonePrefix(request.getPhonePrefix())
                .phone(request.getPhone())
                .isActive(true)
                .build();
        
        clienteRepository.save(cliente);
        
        // Generar token
        String token = jwtService.generateToken(cliente);
        
        return AuthResponse.builder()
                .success(true)
                .token(token)
                .email(cliente.getEmail())
                .firstName(cliente.getFirstName())
                .lastName(cliente.getLastName())
                .phone(cliente.getPhone())
                .phonePrefix(cliente.getPhonePrefix())
                .photoUrl(cliente.getPhotoUrl())
                .message("Registro exitoso")
                .build();
    }
    
    public AuthResponse login(LoginRequest request) {
        try {
            // Autenticar
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            
            // Buscar cliente
            Cliente cliente = clienteRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            // Generar token
            String token = jwtService.generateToken(cliente);
            
            return AuthResponse.builder()
                    .success(true)
                    .token(token)
                    .email(cliente.getEmail())
                    .firstName(cliente.getFirstName())
                    .lastName(cliente.getLastName())
                    .phone(cliente.getPhone())
                    .phonePrefix(cliente.getPhonePrefix())
                    .photoUrl(cliente.getPhotoUrl())
                    .message("Login exitoso")
                    .build();
        } catch (Exception e) {
            return AuthResponse.builder()
                    .success(false)
                    .message("Credenciales inválidas")
                    .build();
        }
    }
}
