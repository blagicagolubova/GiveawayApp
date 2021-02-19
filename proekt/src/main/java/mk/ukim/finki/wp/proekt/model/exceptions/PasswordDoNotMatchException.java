package mk.ukim.finki.wp.proekt.model.exceptions;

public class PasswordDoNotMatchException extends RuntimeException {
    public PasswordDoNotMatchException(){
        super("Passwords do not match exception.");
    }
}
