package controllers;


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
import services.UserService;

import static org.junit.Assert.assertEquals;

public class RegistrationControllerTest extends ApplicationTest  {

    public static final String TEST_USER = "testUser";
    public static final String TEST_PASSWORD = "testPassword";
    private RegistrationController controller;

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
        controller = new RegistrationController();
        controller.registrationMessage=new Text();
        controller.passwordField = new PasswordField();
        controller.usernameField=new TextField();
        controller.passwordField.setText(TEST_PASSWORD);
        controller.usernameField.setText(TEST_USER);
    }

    @Test
    public void testHandleAddUserActionCode() {
        controller.handleRegisterAction();
        assertEquals(2, UserService.getUsers().size());
        assertEquals("Account created successfully!", controller.registrationMessage.getText());
    }
    @Test
    public void testAddSameUserTwice() {
        controller.handleRegisterAction();
        controller.handleRegisterAction();
        assertEquals("An account with the username " + TEST_USER + " already exists!", controller.registrationMessage.getText());
    }
    @Test
    public void NoUserName(){
        controller.usernameField.setText("");
        controller.handleRegisterAction();
        assertEquals("Please enter an username!", controller.registrationMessage.getText());
    }

    @Test
    public void NoPassword(){
        controller.passwordField.setText("");
        controller.handleRegisterAction();
        assertEquals("Please enter a password!", controller.registrationMessage.getText());
    }



}
