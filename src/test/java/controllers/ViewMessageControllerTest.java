package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import registration.Message;
import registration.User;
import sample.RegistrationController;
import services.FileSystemService;
import services.MessageService;
import services.UserService;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ViewMessageControllerTest extends ApplicationTest {
    private static final String TEST_USERNAME = "testUsername";
    private static final String TEST_PASSWORD = "testPassword";
    public RegistrationController regController;
    public ViewMessagesController controller;
    public LoginController logController;

    @BeforeClass
    public static void setupClass() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration-example";
        FileSystemService.initApplicationHomeDirIfNeeded();
    }
    @Before
    public void setup() throws Exception{
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
        UserService.loadUsersFromFile();
        regController = new RegistrationController();
        regController.passwordField = new PasswordField();
        regController.usernameField = new TextField();
        regController.registrationMessage = new Text();
        regController.usernameField.setText(TEST_USERNAME);
        regController.passwordField.setText(TEST_PASSWORD);
        controller = new ViewMessagesController();
        controller.messageTable = new TableView<Message>();
        controller.nameColumn = new TableColumn<Message, String>();
        controller.messageColumn = new TableColumn<Message, String>();

    }
    @Test
    public void testSetMessages() throws IOException{
        regController.handleRegisterAction();
        UserService.loadUsersFromFile();
        LoginController.text = TEST_USERNAME;
        MessageService.loadMessagesFromFile(TEST_USERNAME);
        MessageService.addMessage("Te-am acceptat");
        controller.setMessages();
        int k = controller.messageList.size();
        assertEquals(k, controller.messageList.size());
    }
}
