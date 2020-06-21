package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.LoginController;
import exceptions.*;
import org.apache.commons.io.FileUtils;
import registration.Instructor;
import registration.Message;
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
    public static List<Instructor> instructors;
    private static List<Instructor> afterChange = new ArrayList<Instructor>();
    private static List<Instructor> changeInstructor;
    public static List<Client> delReq;
    public static List<Instructor> delInstructor;
    private static List<Client> afterRemoval = new ArrayList<Client>();
    private static List<Instructor> afterInstructorRemoval=new ArrayList<Instructor>();
    public static final Path INSTRUCTORS_PATH = FileSystemService.getPathToFile("config", "instructors.json");
    public static  Path USERS_PATH = FileSystemService.getPathToFile("config", LoginController.getCurrectUsername()+".json");
    public static  Path REQUESTS_PATH = FileSystemService.getPathToFile("config", LoginController.getCurrectUsername()+"_requests.json");
    public static void loadInstructorsFromFile() throws IOException {
        if (!Files.exists(INSTRUCTORS_PATH)) {
            FileUtils.copyURLToFile(UserService.class.getClassLoader().getResource("users.json"), INSTRUCTORS_PATH.toFile());
            ObjectMapper objectMapper = new ObjectMapper();

            instructors = objectMapper.readValue(INSTRUCTORS_PATH.toFile(),
                    new TypeReference<List<Instructor>>() {
                    });
        }
        else{
            ObjectMapper objectMapper = new ObjectMapper();

            instructors = objectMapper.readValue(INSTRUCTORS_PATH.toFile(),
                    new TypeReference<List<Instructor>>() {
                    });}
    }
    public static List <Instructor> getInstructors()
    {
        return instructors;
    }
    public static void addInstructor(String name, int clients)  {
        instructors.add(new Instructor(name,clients));
        persistInstructors();
    }

    public static void deleteInstructor(String name) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        delInstructor = objectMapper.readValue(INSTRUCTORS_PATH.toFile(),
                new TypeReference<List<Instructor>>() {
                });
        for(Instructor instructor : delInstructor){
            if(Objects.equals(name, instructor.getName())){
                continue;

            }
            else
                afterInstructorRemoval.add(instructor);}
        FileUtils.copyURLToFile(UserService.class.getClassLoader().getResource("users.json"), INSTRUCTORS_PATH.toFile());

        ObjectMapper objMapper = new ObjectMapper();
        objMapper.writerWithDefaultPrettyPrinter().writeValue(INSTRUCTORS_PATH.toFile(),afterInstructorRemoval);



        afterInstructorRemoval.clear();
    }

    private static void persistInstructors() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(INSTRUCTORS_PATH.toFile(), instructors);
        } catch (IOException e) {
            throw new CouldNotWriteUsersException();
        }
    }

    public static void loadUsersFromFile(String i) throws IOException {
        USERS_PATH = FileSystemService.getPathToFile("config", i+".json");
        ObjectMapper objectMapper = new ObjectMapper();

        clients = objectMapper.readValue(USERS_PATH.toFile(),
                new TypeReference<List<Client>>() {
                });
    }

    public static void loadRequestsFromFile(String i) throws IOException {
        REQUESTS_PATH = FileSystemService.getPathToFile("config", i+"_requests.json");
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

    public static void changeNumberOfClients(String name,String clientName)throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        changeInstructor = objectMapper.readValue(INSTRUCTORS_PATH.toFile(),
                new TypeReference<List<Instructor>>() {
                });
        for(Instructor instructor : changeInstructor){
            if(!(Objects.equals(name, instructor.getName()))){
                afterChange.add(instructor);

            }
            else
            {
                instructor.addNewClient(clientName);
                afterChange.add(instructor);
            }
            FileUtils.copyURLToFile(UserService.class.getClassLoader().getResource("users.json"), INSTRUCTORS_PATH.toFile());

            ObjectMapper objMapper = new ObjectMapper();
            objMapper.writerWithDefaultPrettyPrinter().writeValue(INSTRUCTORS_PATH.toFile(),afterChange);

        }
        afterChange.clear();
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
