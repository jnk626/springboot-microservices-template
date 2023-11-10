package it.contrader.authenticationservice.customException;

public class CustomFeignException extends RuntimeException{
    public CustomFeignException(String message, Exception e){
        super(message + e.getMessage());
    }
}
