package it.contrader.authenticationservice.customException;

public class UsernameAlreadyInUseException extends Exception{
    public UsernameAlreadyInUseException(String message) {
        super(message);
    }
}
