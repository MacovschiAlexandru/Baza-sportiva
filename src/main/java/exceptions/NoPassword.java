package exceptions;

public class NoPassword extends Exception{
    private String password;
    public NoPassword(String password){
        super(String.format("Please enter a password!", password));
    }
}