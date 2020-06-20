package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.CreateInstructorController;
import controllers.LoginController;
import controllers.RequestInstructorController;
import exceptions.*;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import registration.Client;
import registration.Instructor;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

public class InstructorServiceTest extends ApplicationTest {
    public static final String TEST_USER = "testUser";
    public static final String TEST_PASSWORD = "testPassword";
    private CreateInstructorController controller;

    @BeforeClass
    public static void setupClass() throws IOException {
        FileSystemService.APPLICATION_FOLDER = ".test-registration-example";
        FileSystemService.initApplicationHomeDirIfNeeded();
        UserService.loadUsersFromFile();
    }

    @Before
    public void setUp() throws IOException {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
        UserService.loadUsersFromFile();
        controller=new CreateInstructorController();
        controller.passwordField=new PasswordField();
        controller.usernameField=new TextField();
        controller.creationMessage=new Text();
        controller.usernameField.setText(TEST_USER);
        controller.passwordField.setText(TEST_PASSWORD);
    }

    @Test
    public void testCopyDefaultFileIfNotExists() throws Exception {
        InstructorService.loadInstructorsFromFile();
        assertTrue(Files.exists(InstructorService.INSTRUCTORS_PATH));
    }

    @Test
    public void testLoadInstructorsFromFile() throws IOException {
        InstructorService.loadInstructorsFromFile();
        assertNotNull(InstructorService.instructors);
        assertEquals(0, InstructorService.instructors.size());
    }
    @Test
    public void testAddInstructorInInstructorsJson() throws IOException {
        InstructorService.loadInstructorsFromFile();
        InstructorService.addInstructor("instr",0);
        assertNotNull(InstructorService.instructors);
        assertEquals(1, InstructorService.instructors.size());
    }

    @Test
    public void testDeleteInstructorFromInstructorsJson() throws IOException {
        InstructorService.loadInstructorsFromFile();
        InstructorService.addInstructor("instr",0);
        InstructorService.deleteInstructor("instr");
        InstructorService.loadInstructorsFromFile();
        assertEquals(0, InstructorService.instructors.size());
    }

    @Test
    public void testAddOneInstructorIsPersisted() throws Exception {
        InstructorService.loadInstructorsFromFile();
        InstructorService.addInstructor("instr",0);
        List<Instructor> instructors = new ObjectMapper().readValue(InstructorService.INSTRUCTORS_PATH.toFile(), new TypeReference<List<Instructor>>() {
        });
        assertNotNull(instructors);
        assertEquals(1, instructors.size());
    }

    @Test
    public void testLoadClientsOfAnInstructor() throws IOException {
        controller.handleCreationButton(new ActionEvent());
        InstructorService.loadUsersFromFile(TEST_USER);
        assertNotNull(InstructorService.clients);
        assertEquals(0, InstructorService.clients.size());

    }

    @Test
    public void testLoadRequestsFromFile() throws IOException {
        controller.handleCreationButton(new ActionEvent());
        LoginController.text=TEST_USER;
        InstructorService.loadRequestsFromFile();
        assertNotNull(InstructorService.requests);
        assertEquals(0, InstructorService.requests.size());

    }

    @Test
    public void testAddClientInSchedule() throws IOException {
        controller.handleCreationButton(new ActionEvent());
        InstructorService.loadUsersFromFile(TEST_USER);
        InstructorService.addClient("Cristi",11,12);
        assertNotNull(InstructorService.clients);
        assertEquals(1, InstructorService.clients.size());
    }

    @Test
    public void testPersistClients() throws IOException {
        controller.handleCreationButton(new ActionEvent());
        InstructorService.loadUsersFromFile(TEST_USER);
        InstructorService.addClient("Cristi",11,12);
        List<Client> clients = new ObjectMapper().readValue(InstructorService.USERS_PATH.toFile(), new TypeReference<List<Client>>() {
        });
        assertNotNull(clients);
        assertEquals(1, clients.size());
    }

    @Test
    public void testChangeNumberOfClientsWhenANewClientIsAccepted() throws IOException {
        InstructorService.loadInstructorsFromFile();
        controller.handleCreationButton(new ActionEvent());
        InstructorService.loadUsersFromFile(TEST_USER);
        InstructorService.addInstructor(TEST_USER,0);
        InstructorService.changeNumberOfClients(TEST_USER,"Cristi");
        int client = 0;
        InstructorService.loadInstructorsFromFile();
        for(Instructor i:InstructorService.instructors)
            if(Objects.equals(i.getName(),TEST_USER))
                client=i.getClients();
            assertEquals(1,client);
    }

    @Test
    public void testRemoveRequestAfterResponse() throws IOException, InstructorNotFound, NoExitHour, NoEntryHour, UnacceptedEntryHour, UnacceptedExitHour, NoUserName {
        controller.handleCreationButton(new ActionEvent());
        LoginController.text=TEST_USER;
        InstructorService.loadRequestsFromFile();
        RequestInstructorController.text=TEST_USER;
        ClientService.addRequest("Cristi",12,13);
        InstructorService.deleteRequest("Cristi");
        assertEquals(0,InstructorService.requests.size());
    }
}
