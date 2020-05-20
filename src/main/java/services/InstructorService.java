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

    public static List<Client> clients;
    public static List<Client> requests;
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




}
