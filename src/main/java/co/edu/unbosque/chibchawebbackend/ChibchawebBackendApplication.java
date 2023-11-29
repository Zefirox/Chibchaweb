package co.edu.unbosque.chibchawebbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ChibchawebBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChibchawebBackendApplication.class, args);
    }

}
