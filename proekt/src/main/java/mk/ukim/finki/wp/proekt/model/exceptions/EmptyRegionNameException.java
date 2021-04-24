package mk.ukim.finki.wp.proekt.model.exceptions;

public class EmptyRegionNameException extends RuntimeException{
    public EmptyRegionNameException(){
        super("Empty Region Name exception");
    }
}
