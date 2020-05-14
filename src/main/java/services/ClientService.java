package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.LoginController;
import exceptions.CouldNotWriteUsersException;
import exceptions.NoUserName;
import registration.Client;
import registration.Instructor;
import controllers.RequestInstructorController;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class ClientService {
    public static String username;
    public static List<Client> messageReq;
    private static final Path INSTRUCTORREQ_PATH = FileSystemService.getPathToFile("config",RequestInstructorController.getUser()+"_requests.json");

    private static void checkUserIsNotEmpty(String username)throws NoUserName {
        if(Objects.equals(username, ""))
            throw new NoUserName(username);
    }
    public static void addRequest(String username, int enH, int exH) throws NoUserName, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        messageReq = objectMapper.readValue(INSTRUCTORREQ_PATH.toFile(),
                new TypeReference<List<Client>>() {
                });
          messageReq.add(new Client(username, enH, exH));
        try {
            ObjectMapper objectMapper2 = new ObjectMapper();
            objectMapper2.writerWithDefaultPrettyPrinter().writeValue(INSTRUCTORREQ_PATH.toFile(), messageReq);
        } catch (IOException e) {
            throw new CouldNotWriteUsersException();
        }
    }

}
