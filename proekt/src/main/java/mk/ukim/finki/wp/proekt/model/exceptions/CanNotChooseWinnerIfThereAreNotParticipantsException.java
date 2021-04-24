package mk.ukim.finki.wp.proekt.model.exceptions;

public class CanNotChooseWinnerIfThereAreNotParticipantsException extends RuntimeException{
    public CanNotChooseWinnerIfThereAreNotParticipantsException(){
        super("Can not choose winner if there are not participants");
    }
}
