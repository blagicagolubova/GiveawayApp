package mk.ukim.finki.wp.proekt.model.exceptions;

public class CountryNameAndCodeCanNotBeEmptyException extends RuntimeException{
    public CountryNameAndCodeCanNotBeEmptyException(){
        super("Invalid argument exception");
    }
}
