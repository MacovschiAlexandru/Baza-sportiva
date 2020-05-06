package exceptions;


public class NoUserName extends Exception{
private String username;
    public NoUserName(String username){
     super(String.format("Please enter an username!",username));

    }

}