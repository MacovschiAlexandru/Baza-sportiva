package registration;

import java.util.Objects;

public class Instructor {
    private String instructor;

    public Instructor(String instructor) {
        this.instructor = instructor;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instructor)) return false;
        Instructor that = (Instructor) o;
        return instructor.equals(that.instructor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instructor);
    }
}
