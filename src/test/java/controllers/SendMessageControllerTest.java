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
import services.MessageService;
import services.UserService;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SendMessageControllerTest extends ApplicationTest {
    public static final String TEST_MESSAGE="Te iau";
    public static final String TEST_USER = "null";
    public static final String TEST_PASSWORD = "testPassword";
    private SendMessageController controller;
    private RegistrationController regController;


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
        controller = new SendMessageController();
        regController=new RegistrationController();
        controller.messageField=new TextField();
        controller.messageField.setText(TEST_MESSAGE);
        controller.sendingMessage=new Text();
        regController.passwordField=new PasswordField();
        regController.usernameField=new TextField();
        regController.registrationMessage=new Text();
        regController.passwordField.setText(TEST_PASSWORD);
        regController.usernameField.setText(TEST_USER);
    }

    @Test
    public void testSendMessage() throws IOException {
        regController.handleRegisterAction();
        controller.handleSendingButton(new ActionEvent());
        assertEquals(1, MessageService.messages.size());
        assertEquals("Message sent",controller.sendingMessage.getText());
    }

}
