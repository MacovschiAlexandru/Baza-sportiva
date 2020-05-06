package Exceptions;

public class InvalidUsername extends Exception {

    public InvalidUsername() {
        super(String.format("the user is not valid!"));

    }
}