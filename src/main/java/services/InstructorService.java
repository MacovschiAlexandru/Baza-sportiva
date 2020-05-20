package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.LoginController;
import exceptions.CouldNotWriteUsersException;
import exceptions.InstructorNotFound;
import org.apache.commons.io.FileUtils;
import registration.User;
import registration.Client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InstructorService {

    public static List<Client> clients;
    public static List<Client> requests;
    public static List<Client> delReq;
    private static List<Client> afterRemoval = new ArrayList<Client>();
    private static final Path USERS_PATH = FileSystemService.getPathToFile("config", LoginController.getCurrectUsername()+".json");
    private static final Path REQUESTS_PATH = FileSystemService.getPathToFile("config", LoginController.getCurrectUsername()+"_requests.json");
    public static void loadUsersFromFile() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        clients = objectMapper.readValue(USERS_PATH.toFile(),
                new TypeReference<List<Client>>() {
                });
    }

    public static void loadRequestsFromFile() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        requests = objectMapper.readValue(REQUESTS_PATH.toFile(),
                new TypeReference<List<Client>>() {
                });
    }

    public static void addClient(String name,int h1,int h2){
        clients.add(new Client(name,h1,h2));
        persistClients();

    }
    private static void persistClients() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(USERS_PATH.toFile(), clients);
        } catch (IOException e) {
            throw new CouldNotWriteUsersException();
        }
    }

    public static void deleteRequest(String username) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        delReq = objectMapper.readValue(REQUESTS_PATH.toFile(),
                new TypeReference<List<Client>>() {
                });
            for(Client client : delReq){
                if(!(Objects.equals(username, client.getClient()))){
                    afterRemoval.add(client);
                }
                FileUtils.copyURLToFile(UserService.class.getClassLoader().getResource("users.json"), REQUESTS_PATH.toFile());

                    ObjectMapper objMapper = new ObjectMapper();
                    objMapper.writerWithDefaultPrettyPrinter().writeValue(REQUESTS_PATH.toFile(),afterRemoval);

            }
        afterRemoval.clear();
    }



}
