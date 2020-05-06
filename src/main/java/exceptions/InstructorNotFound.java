package exceptions;

public class InstructorNotFound extends Exception {

  public InstructorNotFound(){
      super(String.format("Username not found!"));
  }
}
