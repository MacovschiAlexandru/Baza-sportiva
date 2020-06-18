package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.LoginController;
import exceptions.*;
import registration.Client;
import registration.Instructor;
import controllers.RequestInstructorController;
import sample.RegistrationController;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class ClientService {
    public static String username;
    public static List<Client> messageReq;
    public static List<Client> requests;
    private static Path INSTRUCTORREQ_PATH;

    private static void checkUserIsNotEmpty(String username)throws NoUserName {
        if(Objects.equals(username, ""))
            throw new NoUserName(username);
    }
    public static void addRequest(String username, int enH, int exH) throws NoUserName, NoExitHour, NoEntryHour, IOException, UnacceptedEntryHour, UnacceptedExitHour {
        String s="", s1="";
        INSTRUCTORREQ_PATH = FileSystemService.getPathToFile("config",RequestInstructorController.getUser()+"_requests.json");
        checkUserIsNotEmpty(RequestInstructorController.getUser());
        ObjectMapper objectMapper = new ObjectMapper();
        messageReq = objectMapper.readValue(INSTRUCTORREQ_PATH.toFile(),
                new TypeReference<List<Client>>() {
                });
        checkUserIsNotEmpty(username);
        s = s+enH;
        s1 = s1+exH;
        checkValidEntryHour(s);
        checkValidExitHour(s1);
          messageReq.add(new Client(username, enH, exH));
        try {
            ObjectMapper objectMapper2 = new ObjectMapper();
            objectMapper2.writerWithDefaultPrettyPrinter().writeValue(INSTRUCTORREQ_PATH.toFile(), messageReq);
        } catch (IOException e) {
            throw new CouldNotWriteUsersException();
        }
    }
   public static void checkEnHIsNotEmpty(String enH) throws NoEntryHour {
        if(Objects.equals(enH, ""))
            throw new NoEntryHour(enH);
   }
   public static void checkExHIsNotEmpty(String exH)throws NoExitHour{
        if(Objects.equals(exH,""))
            throw new NoExitHour(exH);
   }
   public static void checkValidEntryHour(String enH) throws UnacceptedEntryHour, IOException {
        ObjectMapper objmapper = new ObjectMapper();
       INSTRUCTORREQ_PATH = FileSystemService.getPathToFile("config",RequestInstructorController.getUser()+"_requests.json");
        requests = objmapper.readValue(INSTRUCTORREQ_PATH.toFile(),
                new TypeReference<List<Client>>() {
                });
        for(Client cl : requests){
            if(Objects.equals(LoginController.getCurrectUsername(), cl.getClient()) &&
                    ((Integer.parseInt(enH) == cl.getEntryHour())
                            || (Integer.parseInt(enH) > cl.getEntryHour()
                            && Integer.parseInt(enH) <cl.getExitHour()))) {
                throw new UnacceptedEntryHour();
            }
        }
   }
    public static void checkValidExitHour(String exH) throws UnacceptedExitHour, IOException {
        ObjectMapper objmapper2 = new ObjectMapper();
        INSTRUCTORREQ_PATH = FileSystemService.getPathToFile("config",RequestInstructorController.getUser()+"_requests.json");
        requests = objmapper2.readValue(INSTRUCTORREQ_PATH.toFile(),
                new TypeReference<List<Client>>() {
                });
        for(Client cl : requests){
            if(Objects.equals(LoginController.getCurrectUsername(), cl.getClient()) &&
                    ((Integer.parseInt(exH) == cl.getExitHour())
                            || (Integer.parseInt(exH) > cl.getEntryHour()
                            && Integer.parseInt(exH) <cl.getExitHour()))) {
                throw new UnacceptedExitHour();
            }
        }
    }
}