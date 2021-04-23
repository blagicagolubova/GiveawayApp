package mk.ukim.finki.wp.proekt;

import mk.ukim.finki.wp.proekt.sevice.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

@SpringBootApplication
@ServletComponentScan
@EnableScheduling
public class ProektApplication {

    //@Autowired
    //private EmailService emailService;

    public static void main(String[] args) {
        SpringApplication.run(ProektApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
    /*
    @EventListener(ApplicationReadyEvent.class)
    public void triggerMail(){
        emailService.sendSimpleMessage("golubova.andjela@gmail.com", "spring email", "Spring mail");
    }*/


}
