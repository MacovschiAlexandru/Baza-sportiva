package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.LoginController;
import org.apache.commons.io.FileUtils;
import registration.User;
import registration.Client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class InstructorService {

    private static List<Client> clients;
    private static final Path USERS_PATH = FileSystemService.getPathToFile("config", LoginController.getCurrectUsername()+".json");
    public static void loadUsersFromFile() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        clients = objectMapper.readValue(USERS_PATH.toFile(),
                new TypeReference<List<Client>>() {
                });
    }

    public static void afisare(){
        for (Client client : clients){
            System.out.println(client.getClient()+" "+client.getEntryHour()+" "+client.getExitHour());
        }
    }


}
