package com.finder.api.service;

import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.finder.api.exception.MyRuntimeException;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendRecoveryEmail(String to, String token) {

        try {

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom("nao-responda@finder.blog.br");
            helper.setTo(to);
            helper.setSubject(token + " é o seu código de recuperação");

        String html = """
    <div style="background-color: #f9fafb; padding: 40px 10px; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;">
        <div style="max-width: 460px; margin: 0 auto; background-color: #ffffff; border: 1px solid #e5e7eb; border-radius: 16px; overflow: hidden; box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05);">
            
            <!-- Header -->
            <div style="background-color: #0f172a; padding: 28px 24px; text-align: center;">
                <h1 style="color: #ffffff; margin: 0; font-size: 20px; font-weight: 600; letter-spacing: -0.5px;">
                    proj-consulta
                </h1>
            </div>
            
            <!-- Body -->
            <div style="padding: 32px 24px;">
                <h2 style="color: #0f172a; font-size: 18px; font-weight: 600; margin-top: 0; margin-bottom: 8px; text-align: center;">
                    Recuperação de Acesso
                </h2>
                <p style="color: #4b5563; font-size: 14px; line-height: 1.5; text-align: center; margin-top: 0; margin-bottom: 24px;">
                    Recebemos uma solicitação para redefinir a sua senha. Utilize o token de validação de 6 dígitos abaixo no sistema:
                </p>
                
                <!-- Token Box -->
                <div style="background-color: #f1f5f9; border: 1px dashed #cbd5e1; border-radius: 12px; padding: 18px; text-align: center; margin-bottom: 24px;">
                    <span style="font-family: 'Courier New', Courier, monospace; font-size: 34px; font-weight: 700; letter-spacing: 10px; color: #0284c7; display: inline-block; margin-left: 10px;">
                        %s
                    </span>
                </div>
                
                <p style="color: #64748b; font-size: 13px; line-height: 1.4; text-align: center; margin-bottom: 0;">
                    Este código é válido por <strong>15 minutos</strong>. Se você não solicitou a alteração, nenhuma ação é necessária.
                </p>
            </div>
            
            <!-- Footer -->
            <div style="border-top: 1px solid #f1f5f9; padding: 16px 24px; background-color: #fafafa; text-align: center;">
                <p style="color: #94a3b8; font-size: 12px; margin: 0;">
                    &copy; %d proj-consulta. Todos os direitos reservados.
                </p>
            </div>
            
        </div>
    </div>
    """.formatted(token, java.time.Year.now().getValue());

            helper.setText(html, true);

            mailSender.send(mimeMessage);

            System.out.println("Email de recuperação enviado com código para: " + to);

        } catch (Exception e) {
            System.out.println("Erro ao enviar email: " + e.getMessage());
            throw new MyRuntimeException("Falha no envio do e-mail de recuperação", e);
        }
    }
}
