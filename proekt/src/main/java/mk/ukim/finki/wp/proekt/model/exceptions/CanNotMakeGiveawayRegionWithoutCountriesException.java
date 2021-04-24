package mk.ukim.finki.wp.proekt.model.exceptions;

public class CanNotMakeGiveawayRegionWithoutCountriesException extends RuntimeException{
    public CanNotMakeGiveawayRegionWithoutCountriesException(){
        super("Can not make giveaway region without countries exception");
    }
}
