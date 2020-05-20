package registration;

public class Message {
    String instructor;
    String message;

    public Message(){

    }
    public Message(String instructor, String message) {
        this.instructor = instructor;
        this.message = message;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (!instructor.equals(message1.instructor)) return false;
        return message.equals(message1.message);
    }

    @Override
    public int hashCode() {
        int result = instructor.hashCode();
        result = 31 * result + message.hashCode();
        return result;
    }
}
