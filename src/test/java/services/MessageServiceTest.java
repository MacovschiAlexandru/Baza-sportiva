package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.LoginController;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import registration.Message;
import sample.RegistrationController;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.*;

public class MessageServiceTest extends ApplicationTest {
    public static final String TEST_USER = "testUser";
    public static final String TEST_PASSWORD = "testPassword";
    private RegistrationController controller;
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
        controller = new RegistrationController();
        controller.registrationMessage=new Text();
        controller.passwordField = new PasswordField();
        controller.usernameField=new TextField();
        controller.passwordField.setText(TEST_PASSWORD);
        controller.usernameField.setText(TEST_USER);

    }
    @Test
    public void testLoadUsersFromFile() throws Exception {

        controller.handleRegisterAction();
        MessageService.loadMessagesFromFile(TEST_USER);
        assertNotNull(MessageService.messages);
        assertEquals(0, MessageService.messages.size());

    }

    @Test
    public void testAddMessage() throws IOException {
        controller.handleRegisterAction();
        MessageService.loadMessagesFromFile(TEST_USER);
        MessageService.addMessage("te iau");
        assertEquals(1, MessageService.messages.size());
    }

    @Test
    public void testDeleteMessage() throws IOException {
        controller.handleRegisterAction();
        LoginController.text=TEST_USER;
        MessageService.loadMessagesFromFile(TEST_USER);
        MessageService.addMessage("te iau");
        MessageService.deleteMessage("te iau",TEST_USER);
        MessageService.loadMessagesFromFile(TEST_USER);
        assertEquals(0, MessageService.messages.size());
    }

    @Test
    public void testAddOneMessageIsPersisted() throws Exception {
        controller.handleRegisterAction();
        MessageService.loadMessagesFromFile(TEST_USER);
        MessageService.addMessage("te iau");
        List<Message> messages = new ObjectMapper().readValue(MessageService.USERS_PATH.toFile(), new TypeReference<List<Message>>() {
        });
        assertNotNull(messages);
        assertEquals(1, messages.size());
    }
}
