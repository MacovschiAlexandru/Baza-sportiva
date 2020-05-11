package registration;

import java.util.Objects;

public class Client {
    private String client;
    private int hour;

    public Client(String client, int hour) {
        this.client = client;
        this.hour=hour;
    }

    public Client() {
    }

    public String getClient() {
        return client;
    }

    public int getHour() {
        return hour;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client1 = (Client) o;

        if (hour != client1.hour) return false;
        return client.equals(client1.client);
    }

    @Override
    public int hashCode() {
        int result = client.hashCode();
        result = 31 * result + hour;
        return result;
    }
}