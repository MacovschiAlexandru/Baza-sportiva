package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.LoginController;
import controllers.ViewRequestsController;
import exceptions.CouldNotWriteUsersException;
import registration.Client;
import registration.Message;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class MessageService {
    public static List<Message> messages;
    private static Path USERS_PATH;
    public static void loadMessagesFromFile() throws IOException {
        USERS_PATH=FileSystemService.getPathToFile("config", ViewRequestsController.getClientToSend() +".json");
        ObjectMapper objectMapper = new ObjectMapper();

        messages = objectMapper.readValue(USERS_PATH.toFile(),
                new TypeReference<List<Message>>() {
                });
    }
    public static void addMessage(String message)
    {
        messages.add(new Message(LoginController.getCurrectUsername(),message));
        persistMessages();

    }
    private static void persistMessages() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(USERS_PATH.toFile(), messages);
        } catch (IOException e) {
            throw new CouldNotWriteUsersException();
        }
    }
}
