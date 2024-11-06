package roomescape.exception;

public class MissingReservationDataException extends RuntimeException{
    public MissingReservationDataException(String message){
        super(message);
    }
}
