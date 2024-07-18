package ge.bog.hw22.exceptions;

public class UserIdentificationNotUniqueException extends RuntimeException {
    public UserIdentificationNotUniqueException() {
        super("User already exists bro");
    }

    public UserIdentificationNotUniqueException(String s) {
        super(s);
    }

    public UserIdentificationNotUniqueException(String message, Throwable cause) {
        super(message, cause); // if we want to pass a message
    }
}
