package mk.ukim.finki.wp.proekt.model.exceptions;

public class InvalidCategoryIdException extends RuntimeException{
    public InvalidCategoryIdException(){
        super("Invalid category Id exception");
    }
}
