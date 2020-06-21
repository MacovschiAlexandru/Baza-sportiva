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
import services.FileSystemService;
import services.InstructorService;
import services.UserService;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class DeleteInstructorControllerTest extends ApplicationTest {
    public static final String TEST_USER = "testUser";
    public static final String TEST_PASSWORD = "testPassword";
    private DeleteInstructorController controller;
    private CreateInstructorController createController;

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
        controller = new DeleteInstructorController();
        createController=new CreateInstructorController();
        createController.creationMessage=new Text();
        createController.usernameField=new TextField();
        createController.passwordField=new PasswordField();
        controller.deleteMessage=new Text();
        controller.usernameField=new TextField();
        controller.usernameField.setText(TEST_USER);
        createController.usernameField.setText(TEST_USER);
        createController.passwordField.setText(TEST_PASSWORD);
    }

    @Test
    public void testDeleteInstructor() throws IOException {
        createController.handleCreationButton(new ActionEvent());
        controller.handleDeleteButton(new ActionEvent());
        assertEquals(1, UserService.getUsers().size());
        assertEquals(0, InstructorService.getInstructors().size());
        assertEquals("Instructor has been removed!",controller.deleteMessage.getText());
    }
    @Test
    public void testUsernameDoesNotExists() throws IOException {
        createController.usernameField.setText("ceva");
        createController.handleCreationButton(new ActionEvent());
        controller.handleDeleteButton(new ActionEvent());
        assertEquals("Username not found!",controller.deleteMessage.getText());
    }

}
