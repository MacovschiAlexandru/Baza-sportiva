package controllers;

import exceptions.InvalidPassword;
import exceptions.InvalidUsername;
import exceptions.NoPassword;
import exceptions.NoUserName;
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
import services.UserService;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;





public class LoginControllerTest extends ApplicationTest {
    public static final String TEST_USER = "testUser";
    public static  String TEST_PASSWORD = "testPassword";
    private LoginController controller;
    private RegistrationController regController;

    @BeforeClass
    public static void setupClass() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration-example";
        FileSystemService.initApplicationHomeDirIfNeeded();
    }
    @Before
    public void setUp() throws Exception {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
        UserService.loadUsersFromFile();
        controller=new LoginController();
        regController=new RegistrationController();
        controller.usernameField=new TextField();
        controller.loginMessage=new Text();
        controller.passwordField=new PasswordField();
        controller.passwordField.setText(TEST_PASSWORD);
        controller.usernameField.setText(TEST_USER);

        regController.registrationMessage=new Text();
        regController.passwordField = new PasswordField();
        regController.usernameField=new TextField();
        regController.passwordField.setText(TEST_PASSWORD);
        regController.usernameField.setText(TEST_USER);
    }
    @Test
    public void NoUsername() throws InvalidUsername, InvalidPassword, NoPassword, NoUserName, IOException {
        regController.handleRegisterAction();
        controller.passwordField.setText("");
        controller.usernameField.setText("");
        controller.handleLoginButtonAction(new ActionEvent());
        assertEquals("Please enter an username!",controller.loginMessage.getText());
    }

    @Test
    public void NoPassword() throws InvalidUsername, InvalidPassword, NoPassword, NoUserName, IOException {
        regController.handleRegisterAction();
        controller.passwordField.setText("");
        controller.handleLoginButtonAction(new ActionEvent());
        assertEquals("Please enter a password!",controller.loginMessage.getText());
    }

    @Test
    public void InvalidPassword() throws InvalidUsername, InvalidPassword, NoPassword, NoUserName, IOException {
        regController.handleRegisterAction();
        controller.passwordField.setText("ceva");
        controller.handleLoginButtonAction(new ActionEvent());
        assertEquals("the password is not correct!",controller.loginMessage.getText());
    }

    @Test
    public void InvalidUsername() throws InvalidUsername, InvalidPassword, NoPassword, NoUserName, IOException {
        regController.handleRegisterAction();
        controller.usernameField.setText("ceva");
        controller.handleLoginButtonAction(new ActionEvent());
        assertEquals("the user is not valid!",controller.loginMessage.getText());
    }


}
