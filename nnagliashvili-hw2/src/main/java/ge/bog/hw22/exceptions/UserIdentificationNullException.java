package ge.bog.hw22.exceptions;

public class UserIdentificationNullException extends RuntimeException {
    public UserIdentificationNullException() {
        super("User identification can not be null bro");
    }

    public UserIdentificationNullException(String s) {
        super(s);
    }

    public UserIdentificationNullException(String message, Throwable cause) {
        super(message, cause); // if we want to pass a message
    }
}
