package mk.ukim.finki.wp.proekt.sevice;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);
}
