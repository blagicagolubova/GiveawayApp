package mk.ukim.finki.wp.proekt.model.exceptions;

public class InvalidUserCredentialsException extends RuntimeException {
    public InvalidUserCredentialsException(){
        super("Invalid user credentials exception");
    }
}
