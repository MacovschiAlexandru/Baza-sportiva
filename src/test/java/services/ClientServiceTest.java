package services;

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
import sample.RegistrationController;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ClientServiceTest extends ApplicationTest {
    public static final String TEST_USER = "testUser";
    public static final String TEST_PASSWORD = "testPassword";
    private RegistrationController controller;
    private CreateInstructorController iC;
    private RequestInstructorController RC;

    @BeforeClass
    public static void setupClass() throws IOException {
        FileSystemService.APPLICATION_FOLDER = ".test-registration-example";
        FileSystemService.initApplicationHomeDirIfNeeded();
        UserService.loadUsersFromFile();
    }

    @Before
    public void setup() throws IOException {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
        UserService.loadUsersFromFile();
        controller = new RegistrationController();
        controller.registrationMessage = new Text();
        controller.passwordField = new PasswordField();
        controller.usernameField = new TextField();
        controller.passwordField.setText(TEST_PASSWORD);
        controller.usernameField.setText(TEST_USER);
        iC = new CreateInstructorController();
        iC.passwordField = new PasswordField();
        iC.passwordField.setText(TEST_PASSWORD);
        iC.usernameField = new TextField();
        iC.usernameField.setText(TEST_USER);
        iC.creationMessage = new Text();
        RC = new RequestInstructorController();
        RC.requestMessage = new Text();
        RC.entryHour = new TextField();
        RC.exitHour = new TextField();
        RC.usernameField = new TextField();
        RC.usernameField.setText(TEST_USER);
        iC.handleCreationButton(new ActionEvent());
    }

    @Test(expected = NoUserName.class)
    public void testUserNotEmpty() throws IOException, InstructorNotFound, NoExitHour, NoEntryHour, UnacceptedEntryHour, UnacceptedExitHour, NoUserName {
        RequestInstructorController.text = "";
        ClientService.checkUserIsNotEmpty("");
    }

    @Test
    public void testAddRequests() throws IOException, InstructorNotFound, NoExitHour, NoEntryHour, UnacceptedEntryHour, UnacceptedExitHour, NoUserName {
        iC.handleCreationButton(new ActionEvent());
        LoginController.text = TEST_USER;
        InstructorService.loadRequestsFromFile(TEST_USER);
        RequestInstructorController.text = TEST_USER;
        ClientService.addRequest("Cristi", 12, 13);
        InstructorService.loadRequestsFromFile(TEST_USER);
        assertEquals(1, InstructorService.requests.size());
    }

    @Test(expected = NoEntryHour.class)
    public void testNoEntryHour() throws NoEntryHour {
        RC.entryHour.setText("0");
        ClientService.checkEnHIsNotEmpty("");
    }

    @Test(expected = NoExitHour.class)
    public void testNoExitHour() throws NoExitHour {
        RC.exitHour.setText("0");
        ClientService.checkExHIsNotEmpty("");
    }

    @Test
    public void testValidEnH() throws InstructorNotFound, NoExitHour, NoEntryHour, IOException, UnacceptedExitHour, UnacceptedEntryHour, NoUserName {
        iC.handleCreationButton(new ActionEvent());
        LoginController.text = TEST_USER;
        InstructorService.loadRequestsFromFile(TEST_USER);
        RequestInstructorController.text = TEST_USER;
        ClientService.addRequest("Cristi", 12, 14);
        InstructorService.loadRequestsFromFile(TEST_USER);
        assertEquals(1, InstructorService.requests.size());
    }

    @Test
    public void testValidExH() throws InstructorNotFound, NoExitHour, NoEntryHour, IOException, UnacceptedExitHour, UnacceptedEntryHour, NoUserName {
        iC.handleCreationButton(new ActionEvent());
        LoginController.text = TEST_USER;
        InstructorService.loadRequestsFromFile(TEST_USER);
        RequestInstructorController.text = TEST_USER;
        ClientService.addRequest("Cristi", 10, 13);
        InstructorService.loadRequestsFromFile(TEST_USER);
        assertEquals(1, InstructorService.requests.size());
    }
}
