package mk.ukim.finki.wp.proekt.model.exceptions;

public class CompanyNameCanNotBeEmptyException extends RuntimeException{
    public CompanyNameCanNotBeEmptyException(){
        super("Company name can not be empty exception");
    }
}
