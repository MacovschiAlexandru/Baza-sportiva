package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.*;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import registration.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {
    @BeforeClass
    public static void setupClass() {
        FileSystemService.APPLICATION_FOLDER = ".test-registration-example";
        FileSystemService.initApplicationHomeDirIfNeeded();
    }

    @Before
    public void setUp() throws IOException {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
    }

    @Test
    public void testCopyDefaultFileIfNotExists() throws Exception {
        UserService.loadUsersFromFile();
        assertTrue(Files.exists(UserService.USERS_PATH));
    }

    @Test
    public void testCreateJsonNamedAfterClient() throws IOException {
        UserService.createJson("Cristi");
        final Path USERS_PATH = FileSystemService.getPathToFile("config", "Cristi.json");
        assertTrue(Files.exists(USERS_PATH));
    }

    @Test(expected = InvalidUsername.class)
    public void testInvalidUsername() throws IOException, UsernameAlreadyExists, NoUserName, NoPassword, InvalidPassword, InvalidUsername {
        UserService.loadUsersFromFile();
        UserService.addUser("test","testPass");
        UserService.checkUser("ceva","testPass");
    }

    @Test(expected = InvalidPassword.class)
    public void testInvalidPassword() throws IOException, UsernameAlreadyExists, NoUserName, NoPassword, InvalidPassword, InvalidUsername {
        UserService.loadUsersFromFile();
        UserService.addUser("test","testPass");
        UserService.checkUser("test","testPass2");
    }

    @Test(expected = NoPassword.class)
    public void testNoPassword() throws InvalidUsername, NoPassword, NoUserName, InvalidPassword {
        UserService.checkUser("test","");
    }

    @Test(expected = NoUserName.class)
    public void testNoUsername() throws InvalidUsername, NoPassword, NoUserName, InvalidPassword {
        UserService.checkUser("","test");
    }

    @Test
    public void testLoadUsersFromFile() throws Exception {
        UserService.loadUsersFromFile();
        assertNotNull(UserService.users);
        assertEquals(1, UserService.users.size());
    }

    @Test
    public void testAddInstructorInUsersJson() throws UsernameAlreadyExists, NoUserName, NoPassword, IOException {
        UserService.loadUsersFromFile();
        UserService.addInstructor("test","testPass");
        assertEquals(2, UserService.users.size());
    }
    @Test
    public void testDeleteInstructorFromUsersJson() throws IOException, UsernameAlreadyExists, NoUserName, NoPassword, InstructorNotFound {
        UserService.loadUsersFromFile();
        UserService.addInstructor("test","testPass");
        UserService.deleteInstructor("test");
        assertEquals(1, UserService.users.size());
    }

    @Test(expected = NoUserName.class)
    public void testCheckUserIsNotEmpty() throws NoUserName {
        UserService.checkUserIsNotEmpty("");
    }

    @Test(expected = NoPassword.class)
    public void testCheckPasswordNotEmpty() throws NoUserName, NoPassword {
        UserService.checkPassIsNotEmpty("");
    }

    @Test
    public void testAddOneUser() throws Exception {
        UserService.loadUsersFromFile();
        UserService.addUser("test","testPass");
        assertNotNull(UserService.users);
        assertEquals(2, UserService.users.size());
    }

    @Test
    public void testAddTwoUsers() throws Exception {
        UserService.loadUsersFromFile();
        UserService.addUser("test1", "testPass1");
        UserService.addUser("test2", "testPass2");
        assertNotNull(UserService.users);
        assertEquals(3, UserService.users.size());
    }

    @Test(expected = UsernameAlreadyExists.class)
    public void testAddUserAlreadyExists() throws Exception {
        UserService.loadUsersFromFile();
        UserService.addUser("test1", "testPass1");
        assertNotNull(UserService.users);
        UserService.checkUserDoesNotAlreadyExist("test1");
    }

    @Test
    public void testAddOneUserIsPersisted() throws Exception {
        UserService.loadUsersFromFile();
        UserService.addUser("test", "testPass");
        List<User> users = new ObjectMapper().readValue(UserService.USERS_PATH.toFile(), new TypeReference<List<User>>() {
        });
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    public void testAddTwoUserArePersisted() throws Exception {
        UserService.loadUsersFromFile();
        UserService.addUser("test1", "testPass1");
        UserService.addUser("test2", "testPass2");
        List<User> users = new ObjectMapper().readValue(UserService.USERS_PATH.toFile(), new TypeReference<List<User>>() {
        });
        assertNotNull(users);
        assertEquals(3, users.size());
    }

    @Test
    public void testPasswordEncoding() {
        assertNotEquals("testPass1", UserService.encodePassword("username1", "testPass1"));
    }
}
