package controllers;


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
import services.FileSystemService;
import services.InstructorService;
import services.UserService;

import static org.junit.Assert.assertEquals;
public class CreateInstructorControllerTest extends ApplicationTest{
    public static final String TEST_USER = "testUser";
    public static final String TEST_PASSWORD = "testPassword";
    private CreateInstructorController controller;

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
        controller = new CreateInstructorController();
        controller.creationMessage=new Text();
        controller.passwordField = new PasswordField();
        controller.usernameField=new TextField();
        controller.passwordField.setText(TEST_PASSWORD);
        controller.usernameField.setText(TEST_USER);
    }
    @Test
    public void testCreateInstructor() {
        controller.handleCreationButton(new ActionEvent());
        assertEquals(2, UserService.getUsers().size());
        assertEquals(1, InstructorService.getInstructors().size());
        assertEquals("Account created successfully!",controller.creationMessage.getText());
    }
    @Test
    public void testCreateSameInstructorTwice(){
        controller.handleCreationButton(new ActionEvent());
        controller.handleCreationButton(new ActionEvent());
        assertEquals("An account with the username "+TEST_USER+" already exists!",controller.creationMessage.getText());
    }
    @Test
    public void testNoUsername(){
        controller.usernameField.setText("");
        controller.handleCreationButton(new ActionEvent());
        assertEquals("Please enter an username!",controller.creationMessage.getText());
    }
    @Test
    public void testNoPassword(){
        controller.passwordField.setText("");
        controller.handleCreationButton(new ActionEvent());
        assertEquals("Please enter a password!",controller.creationMessage.getText());

    }

}
