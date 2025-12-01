package com.hotelplaza.backspring.service;

import com.hotelplaza.backspring.dto.AuthResponse;
import com.hotelplaza.backspring.dto.ForgotPasswordRequest;
import com.hotelplaza.backspring.dto.ResetPasswordRequest;
import com.hotelplaza.backspring.entity.Cliente;
import com.hotelplaza.backspring.entity.PasswordResetToken;
import com.hotelplaza.backspring.repository.ClienteRepository;
import com.hotelplaza.backspring.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {
    
    private final ClienteRepository clienteRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public AuthResponse forgotPassword(ForgotPasswordRequest request) {
        Optional<Cliente> clienteOpt = clienteRepository.findByEmail(request.getEmail());
        
        // Siempre retornar éxito para no revelar si el email existe
        if (clienteOpt.isEmpty()) {
            return AuthResponse.builder()
                    .success(true)
                    .message("Si el correo está registrado, recibirás un enlace de recuperación")
                    .build();
        }
        
        Cliente cliente = clienteOpt.get();
        
        // Eliminar tokens anteriores del usuario
        tokenRepository.deleteByClienteId(cliente.getId());
        
        // Generar nuevo token
        String token = UUID.randomUUID().toString();
        
        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .cliente(cliente)
                .expiryDate(LocalDateTime.now().plusHours(1)) // Expira en 1 hora
                .used(false)
                .build();
        
        tokenRepository.save(resetToken);
        
        // Enviar email
        try {
            emailService.sendPasswordResetEmail(cliente.getEmail(), token, cliente.getFirstName());
        } catch (Exception e) {
            return AuthResponse.builder()
                    .success(false)
                    .message("Error al enviar el correo. Intenta nuevamente.")
                    .build();
        }
        
        return AuthResponse.builder()
                .success(true)
                .message("Si el correo está registrado, recibirás un enlace de recuperación")
                .build();
    }
    
    @Transactional
    public AuthResponse resetPassword(ResetPasswordRequest request) {
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(request.getToken());
        
        if (tokenOpt.isEmpty()) {
            return AuthResponse.builder()
                    .success(false)
                    .message("Token inválido o expirado")
                    .build();
        }
        
        PasswordResetToken resetToken = tokenOpt.get();
        
        if (resetToken.isExpired()) {
            return AuthResponse.builder()
                    .success(false)
                    .message("El enlace ha expirado. Solicita uno nuevo.")
                    .build();
        }
        
        if (resetToken.getUsed()) {
            return AuthResponse.builder()
                    .success(false)
                    .message("Este enlace ya fue utilizado")
                    .build();
        }
        
        // Actualizar contraseña
        Cliente cliente = resetToken.getCliente();
        cliente.setPassword(passwordEncoder.encode(request.getNewPassword()));
        clienteRepository.save(cliente);
        
        // Marcar token como usado
        resetToken.setUsed(true);
        tokenRepository.save(resetToken);
        
        return AuthResponse.builder()
                .success(true)
                .message("Contraseña actualizada correctamente")
                .build();
    }
    
    public AuthResponse validateToken(String token) {
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(token);
        
        if (tokenOpt.isEmpty()) {
            return AuthResponse.builder()
                    .success(false)
                    .message("Token inválido")
                    .build();
        }
        
        PasswordResetToken resetToken = tokenOpt.get();
        
        if (resetToken.isExpired() || resetToken.getUsed()) {
            return AuthResponse.builder()
                    .success(false)
                    .message("Token expirado o ya utilizado")
                    .build();
        }
        
        return AuthResponse.builder()
                .success(true)
                .message("Token válido")
                .email(resetToken.getCliente().getEmail())
                .build();
    }
}
