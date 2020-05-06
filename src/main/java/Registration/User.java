package Registration;

import java.util.Objects;

public class User {
    private String user;
    private String password;
    private String role;

    public User(String user, String password, String role) {
        this.user = user;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user1 = (User) o;
        return user.equals(user1.user) &&
                password.equals(user1.password) &&
                role.equals(user1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, password, role);
    }
    public String toString() {
        return "UserDTO{" +
                "username='" + user + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
