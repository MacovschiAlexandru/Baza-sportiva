package registration;

import services.InstructorService;

import java.util.Objects;

public class Instructor {
    private String name;
    private int clients;

    public Instructor(String name, int clients) {
        this.name = name;
        this.clients = clients;
    }
    public Instructor(){

    }
    public void addNewClient(String clientName)
    {
        int ok=1;
        for(Client i: InstructorService.clients) {
            if (Objects.equals(i.getClient(), clientName))
                ok=0;
        }
        if(ok==1)
        clients=clients+1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClients() {
        return clients;
    }

    public void setClients(int clients) {
        this.clients = clients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Instructor that = (Instructor) o;

        if (clients != that.clients) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + clients;
        return result;
    }
}
