package Exceptions;

public class InvalidPassword extends Exception {

    public InvalidPassword() {
        super(String.format("the password is not correct!"));

    }
}