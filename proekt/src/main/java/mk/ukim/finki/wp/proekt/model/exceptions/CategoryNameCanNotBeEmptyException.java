package mk.ukim.finki.wp.proekt.model.exceptions;

public class CategoryNameCanNotBeEmptyException extends RuntimeException{
    public CategoryNameCanNotBeEmptyException(){
        super("Category name can not be empty exception");
    }
}
