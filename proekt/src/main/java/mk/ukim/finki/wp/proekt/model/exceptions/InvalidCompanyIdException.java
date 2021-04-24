package mk.ukim.finki.wp.proekt.model.exceptions;

public class InvalidCompanyIdException extends RuntimeException{
    public InvalidCompanyIdException(){
        super("Invalid company id exception");
    }
}