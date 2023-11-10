package it.contrader.authenticationservice.customException;

public class EmailAlreadyInUseException extends Exception{
    public EmailAlreadyInUseException(String message) {
        super(message);
    }
}
