package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.LoginController;
import exceptions.*;
import registration.Client;
import registration.Instructor;
import controllers.RequestInstructorController;
import registration.User;
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

    public static void checkUserIsNotEmpty(String username)throws NoUserName {
        if(Objects.equals(username, ""))
            throw new NoUserName(username);
    }
    public static void addRequest(String username, int enH, int exH) throws NoUserName, NoExitHour, NoEntryHour, IOException, UnacceptedEntryHour, UnacceptedExitHour, InstructorNotFound {
        String s="", s1="";
        int ok=0;
        INSTRUCTORREQ_PATH = FileSystemService.getPathToFile("config",RequestInstructorController.getUser()+"_requests.json");
        checkUserIsNotEmpty(RequestInstructorController.getUser());
        for(User i:UserService.users)
            if(Objects.equals(i.getUser(),RequestInstructorController.getUser()))
                ok=1;
            if(ok==1) {
                ObjectMapper objectMapper = new ObjectMapper();
                messageReq = objectMapper.readValue(INSTRUCTORREQ_PATH.toFile(),
                        new TypeReference<List<Client>>() {
                        });
                checkUserIsNotEmpty(username);
                s = s + enH;
                s1 = s1 + exH;
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
        else
            throw new InstructorNotFound();
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
