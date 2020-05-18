package exceptions;

public class NoExitHour extends Exception{
    public String exH;
            public NoExitHour(String exH){
        super(String.format("please enter an exit hour!"));
            }
}
