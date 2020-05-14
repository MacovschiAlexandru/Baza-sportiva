package exceptions;


public class NoEntryHour extends Exception{
private String entryHour;
public NoEntryHour(String entryHour){
    super(String.format("please enter an entry hour!"));
}
}
