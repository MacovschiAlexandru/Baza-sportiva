package registration;

import java.util.Objects;

public class Client {
    private String client;
    private int entryHour;
    private int exitHour;

    public Client(String client, int hour, int hour2) {
        this.client = client;
        this.entryHour=hour;
        this.exitHour=hour2;
    }

    public Client() {
    }

    public String getClient() {
        return client;
    }

    public int getEntryHour() {
        return entryHour;
    }

    public int getExitHour() {
        return exitHour;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setEntryHour(int entryHour) {
        this.entryHour = entryHour;
    }

    public void setExitHour(int exitHour) {
        this.exitHour = exitHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client1 = (Client) o;

        if (entryHour != client1.entryHour) return false;
        if (exitHour != client1.exitHour) return false;
        return client.equals(client1.client);
    }

    @Override
    public int hashCode() {
        int result = client.hashCode();
        result = 31 * result + entryHour;
        result = 31 * result + exitHour;
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "client='" + client + '\'' +
                ", entryHour=" + entryHour +
                ", exitHour=" + exitHour +
                '}';
    }
}