package controllers;

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
import sample.RegistrationController;
import services.ClientService;
import services.FileSystemService;
import services.UserService;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RequestInstructorControllerTest extends ApplicationTest {
    public static final String TEST_INSTRUCTOR="testInstructor";
    public static final String TEST_PASSWORD = "testPassword";
    public static final String TEST_ENTRYHOUR = "12";
    public static final String TEST_EXITHOUR = "13";
    private RequestInstructorController controller;
    private CreateInstructorController instructorController;

    @BeforeClass
    public static void setupClass() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration-example";
        FileSystemService.initApplicationHomeDirIfNeeded();
        UserService.loadUsersFromFile();
    }

    @Before
    public void setUp() throws Exception {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
        UserService.loadUsersFromFile();
        controller = new RequestInstructorController();
        instructorController=new CreateInstructorController();
        controller.entryHour=new TextField();
        controller.exitHour = new TextField();
        controller.usernameField=new TextField();
        instructorController.passwordField=new PasswordField();
        instructorController.usernameField=new TextField();
        instructorController.creationMessage=new Text();
        instructorController.usernameField.setText(TEST_INSTRUCTOR);
        instructorController.passwordField.setText(TEST_PASSWORD);
        controller.usernameField.setText(TEST_INSTRUCTOR);
        controller.entryHour.setText(TEST_ENTRYHOUR);
        controller.exitHour.setText(TEST_EXITHOUR);
        controller.requestMessage=new Text();
    }

    @Test
    public void testRequest() throws NoExitHour, NoEntryHour, IOException, UnacceptedExitHour, UnacceptedEntryHour, NoUserName {
        instructorController.handleCreationButton(new ActionEvent());
        controller.requestInstructor(new ActionEvent());
        assertEquals(1, ClientService.messageReq.size());
        assertEquals("your request has been sent!", controller.requestMessage.getText());
    }
    @Test
    public void testInstructorNotFound() throws NoExitHour, NoEntryHour, IOException, UnacceptedExitHour, UnacceptedEntryHour, NoUserName {
        instructorController.handleCreationButton(new ActionEvent());
        controller.usernameField.setText("gigel");
        controller.requestInstructor(new ActionEvent());
        assertEquals("Username not found!", controller.requestMessage.getText());
    }

    @Test
    public void noUsername() throws NoExitHour, NoEntryHour, IOException, UnacceptedExitHour, UnacceptedEntryHour, NoUserName {
        instructorController.handleCreationButton(new ActionEvent());
        controller.usernameField.setText("");
        controller.requestInstructor(new ActionEvent());
        assertEquals("Please enter an username!", controller.requestMessage.getText());
    }

    @Test
    public void noEntryHour() throws NoExitHour, NoEntryHour, IOException, UnacceptedExitHour, UnacceptedEntryHour, NoUserName {
        instructorController.handleCreationButton(new ActionEvent());
        controller.entryHour.setText("");
        controller.requestInstructor(new ActionEvent());
        assertEquals("please enter an entry hour!", controller.requestMessage.getText());
    }

    @Test
    public void noExitHour() throws NoExitHour, NoEntryHour, IOException, UnacceptedExitHour, UnacceptedEntryHour, NoUserName {
        instructorController.handleCreationButton(new ActionEvent());
        controller.exitHour.setText("");
        controller.requestInstructor(new ActionEvent());
        assertEquals("please enter an exit hour!", controller.requestMessage.getText());
    }

    @Test
    public void unacceptedEntryHour() throws NoExitHour, NoEntryHour, IOException, UnacceptedExitHour, UnacceptedEntryHour, NoUserName {
        instructorController.handleCreationButton(new ActionEvent());
        controller.requestInstructor(new ActionEvent());
        controller.exitHour.setText("20");
        controller.requestInstructor(new ActionEvent());
        assertEquals("Can't send the request, you already have an ongoing meeting at that moment!", controller.requestMessage.getText());
    }
    @Test
    public void unacceptedExitHour() throws NoExitHour, NoEntryHour, IOException, UnacceptedExitHour, UnacceptedEntryHour, NoUserName {
        instructorController.handleCreationButton(new ActionEvent());
        controller.requestInstructor(new ActionEvent());
        controller.entryHour.setText("10");
        controller.requestInstructor(new ActionEvent());
        assertEquals("Can't send the request, you already have an ongoing meeting at that moment!", controller.requestMessage.getText());
    }

}
