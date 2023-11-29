package co.edu.unbosque.chibchawebbackend.services;


import co.edu.unbosque.chibchawebbackend.exceptions.AppException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RequiredArgsConstructor
@Component
public class MailService {

    @Value("${spring.mail.username}")
    private String sender;

    private final JavaMailSender javaMailSender;

    public String uploadContent(String nameFile){
        try{
            Resource resource = new ClassPathResource("templates/" + nameFile);
            Path path = Paths.get(resource.getURI());
            byte[] bytes = Files.readAllBytes(path);
            return new String(bytes, StandardCharsets.UTF_8);
        }catch (IOException e){
            throw new AppException("Error compiling file mail message",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public void sendMessage(String subject, String[] info, String nameFile) {

        MimeMessage message = javaMailSender.createMimeMessage();

        String content = uploadContent(nameFile);

        switch (nameFile) {
            case "notificationAccess.html" -> {
                content = setContentFile(content, 0, info[1]);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                content = setContentFile(content, 1, LocalDateTime.now().format(formatter));
                break;
            }
            case "notificationReg.html" -> {
                content = setContentFile(content, 0, info[1]);
                break;
            }
        }

        try{
            message.setSubject(subject);
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(info[0]);
            helper.setText(content, true);
            helper.setFrom(sender);
            javaMailSender.send(message);
        } catch (MessagingException e){
            throw new AppException("Error sending email", HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (MailSendException e) {
            throw new AppException("Error sendind message to: " + info[0],HttpStatus.BAD_GATEWAY);
        }

    }



    public String setContentFile(String message, int index, String info){
        return message.replace("{"+index+"}",info);
    }

}
