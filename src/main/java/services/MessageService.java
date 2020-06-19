package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.LoginController;
import controllers.ViewRequestsController;
import exceptions.CouldNotWriteUsersException;
import org.apache.commons.io.FileUtils;
import registration.Client;
import registration.Message;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessageService {
    public static List<Message> messages;
    private static List <Message> delMessage;
    private static List<Message> afterRemoval = new ArrayList<Message>();
    private static Path USERS_PATH;
    public static void loadMessagesFromFile(String c) throws IOException {
        USERS_PATH=FileSystemService.getPathToFile("config", c +".json");
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

    public static void deleteMessage(String m, String name) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        delMessage = objectMapper.readValue(USERS_PATH.toFile(),
                new TypeReference<List<Message>>() {
                });
        for(Message message : delMessage){
            if(Objects.equals(name, message.getInstructor())&& Objects.equals(m,message.getMessage())){
                continue;

            }
            else
                afterRemoval.add(message);}
            FileUtils.copyURLToFile(UserService.class.getClassLoader().getResource("users.json"), USERS_PATH.toFile());

            ObjectMapper objMapper = new ObjectMapper();
            objMapper.writerWithDefaultPrettyPrinter().writeValue(USERS_PATH.toFile(),afterRemoval);



        afterRemoval.clear();
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
