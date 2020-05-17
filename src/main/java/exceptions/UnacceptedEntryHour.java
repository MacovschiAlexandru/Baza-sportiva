package exceptions;

public class UnacceptedEntryHour extends Exception {
    public UnacceptedEntryHour(){
        super(String.format("Can't send the request, you already have an ongoing meeting at that moment!"));
    }
}
