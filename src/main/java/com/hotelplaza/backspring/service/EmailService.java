package com.hotelplaza.backspring.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    
    private final JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    @Value("${app.frontend-url}")
    private String frontendUrl;
    
    public void sendPasswordResetEmail(String toEmail, String token, String firstName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Recuperar contraseña - Hotel Plaza Trujillo");
            
            String resetLink = frontendUrl + "/restablecer-contrasena?token=" + token;
            
            String htmlContent = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                        .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                        .header { background: linear-gradient(to right, #591117, #F26A4B); padding: 20px; text-align: center; border-radius: 10px 10px 0 0; }
                        .header h1 { color: white; margin: 0; font-size: 24px; }
                        .content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }
                        .button { display: inline-block; background: linear-gradient(to right, #591117, #F26A4B); color: white; padding: 15px 30px; text-decoration: none; border-radius: 8px; margin: 20px 0; }
                        .footer { text-align: center; margin-top: 20px; color: #666; font-size: 12px; }
                        .warning { background: #FFF7F7; border: 1px solid #F26A4B; padding: 15px; border-radius: 8px; margin-top: 20px; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>🏨 Hotel Plaza Trujillo</h1>
                        </div>
                        <div class="content">
                            <h2>Hola %s,</h2>
                            <p>Hemos recibido una solicitud para restablecer la contraseña de tu cuenta.</p>
                            <p>Haz clic en el siguiente botón para crear una nueva contraseña:</p>
                            <center>
                                <a href="%s" class="button">Restablecer Contraseña</a>
                            </center>
                            <div class="warning">
                                <strong>⚠️ Importante:</strong>
                                <ul>
                                    <li>Este enlace expirará en <strong>1 hora</strong>.</li>
                                    <li>Si no solicitaste este cambio, ignora este correo.</li>
                                    <li>Nunca compartas este enlace con nadie.</li>
                                </ul>
                            </div>
                            <p>Si el botón no funciona, copia y pega el siguiente enlace en tu navegador:</p>
                            <p style="word-break: break-all; color: #591117;">%s</p>
                        </div>
                        <div class="footer">
                            <p>© 2025 Hotel Plaza Trujillo. Todos los derechos reservados.</p>
                            <p>Este es un correo automático, por favor no respondas a este mensaje.</p>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(firstName != null ? firstName : "Usuario", resetLink, resetLink);
            
            helper.setText(htmlContent, true);
            mailSender.send(message);
            
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage());
        }
    }
}
