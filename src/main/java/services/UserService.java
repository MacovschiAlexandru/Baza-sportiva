package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.*;
import org.apache.commons.io.FileUtils;
import registration.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UserService {

    public static List<User> users;
    private static List<User> delIns;
    private static List<User> afterRemoval = new ArrayList<User>();
    public static final Path USERS_PATH = FileSystemService.getPathToFile("config", "users.json");
    public static String role;
    public static void loadUsersFromFile() throws IOException {

        if (!Files.exists(USERS_PATH)) {
            FileUtils.copyURLToFile(UserService.class.getClassLoader().getResource("users.json"), USERS_PATH.toFile());
            ObjectMapper objectMapper = new ObjectMapper();

            users = objectMapper.readValue(USERS_PATH.toFile(),
                    new TypeReference<List<User>>() {
                    });
            addAdmin();
            persistUsers();
        }
        else{
        ObjectMapper objectMapper = new ObjectMapper();

        users = objectMapper.readValue(USERS_PATH.toFile(),
                new TypeReference<List<User>>() {
                });}
    }

    public static void addUser(String username, String password) throws UsernameAlreadyExists, NoPassword, NoUserName, IOException {
        checkUserDoesNotAlreadyExist(username);
        checkUserIsNotEmpty(username);
        checkPassIsNotEmpty(password);
        users.add(new User(username, encodePassword(username, password), "client"));
        createJson(username);
        persistUsers();
    }

    public static void createJson(String username) throws IOException {
        final Path USERS_PATH = FileSystemService.getPathToFile("config", username+".json");
        FileUtils.copyURLToFile(UserService.class.getClassLoader().getResource("users.json"), USERS_PATH.toFile());
    }

    public static void checkUser(String username,String password) throws NoUserName,NoPassword,InvalidPassword,InvalidUsername{
        checkUserIsNotEmpty(username);
        checkPassIsNotEmpty(password);
        checkUsername(username,password);
    }
    public static void deleteInstructor(String username) throws InstructorNotFound, IOException {
        int ok=0;
        ObjectMapper objectMapper = new ObjectMapper();
        delIns = objectMapper.readValue(USERS_PATH.toFile(),
                new TypeReference<List<User>>() {
                });
        for(User i : delIns)
            if(Objects.equals(username,i.getUser()) && Objects.equals(i.getRole(),"Instructor"))
                ok = 1;
               if(ok==1)
            for(User ins : delIns){
                if(!(Objects.equals(username, ins.getUser()) && Objects.equals(ins.getRole(), "Instructor"))){
                    afterRemoval.add(ins);
                }
                FileUtils.copyURLToFile(UserService.class.getClassLoader().getResource("users.json"), USERS_PATH.toFile());
                try {
                    ObjectMapper objMapper = new ObjectMapper();
                    objMapper.writerWithDefaultPrettyPrinter().writeValue(USERS_PATH.toFile(),afterRemoval);
                    UserService.loadUsersFromFile();
                } catch (IOException e) {
                    throw new CouldNotWriteUsersException();
                }
            }
        else
            throw new InstructorNotFound();
        afterRemoval.clear();
    }
    public static void addInstructor(String username, String password) throws UsernameAlreadyExists, NoPassword, NoUserName{
        checkUserDoesNotAlreadyExist(username);
        checkUserIsNotEmpty(username);
        checkPassIsNotEmpty(password);
        users.add(new User(username, encodePassword(username, password), "Instructor"));
        persistUsers();
    }
    private static void checkUsername(String username, String password) throws InvalidPassword,InvalidUsername{
        int ok=0;
        for (User user : users) {
            if (Objects.equals(username, user.getUser()))
            {ok=1;
            role=user.getRole();

            if(Objects.equals(encodePassword(username,password), user.getPassword()))
                ok=2;
            }
        }
        if(ok==0)
            throw new InvalidUsername();
        if(ok==1)
            throw new InvalidPassword();
    }
 public static void addAdmin(){


        users.add(new User("admin", encodePassword("admin", "admin"), "admin"));
 }


 public static String getRole()
    {
        return role;
    }

    static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExists {
        for (User user : users) {
            if (Objects.equals(username, user.getUser()))
                throw new UsernameAlreadyExists(username);
        }
    }

    public static List<User> getUsers(){return users;}

    public static void checkUserIsNotEmpty(String username)throws NoUserName {
          if(Objects.equals(username, ""))
              throw new NoUserName(username);
        }

    public static void checkPassIsNotEmpty(String password)throws NoPassword {
        if(Objects.equals(password,""))
            throw new NoPassword(password);
    }
    private static void persistUsers() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(USERS_PATH.toFile(), users);
        } catch (IOException e) {
            throw new CouldNotWriteUsersException();
        }
    }

    static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

}
