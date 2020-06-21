package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import registration.Instructor;
import sample.RegistrationController;
import services.FileSystemService;
import services.InstructorService;
import services.UserService;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ViewInstructorsControllerTest extends ApplicationTest {
    private static final String TEST_USERNAME = "testUsername";
    private static final String TEST_PASSWORD = "testPassword";
    public RegistrationController regController;
    public ViewInstructorsController controller;

    @BeforeClass
    public static void setupClass() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration-example";
        FileSystemService.initApplicationHomeDirIfNeeded();
        UserService.loadUsersFromFile();
    }

    @Before
    public void setup() throws Exception {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
        InstructorService.loadInstructorsFromFile();
        controller = new ViewInstructorsController();
        controller.instructorTable = new TableView<Instructor>();
        controller.clientsColumn = new TableColumn<Instructor, Integer>();
        controller.NameColumn = new TableColumn<Instructor, String>();
        regController = new RegistrationController();
        regController.passwordField = new PasswordField();
        regController.usernameField = new TextField();
        regController.registrationMessage = new Text();
        regController.usernameField.setText(TEST_USERNAME);
        regController.passwordField.setText(TEST_PASSWORD);
        regController.handleRegisterAction();
    }

    @Test
    public void testSetInstructor() throws IOException {
        InstructorService.loadInstructorsFromFile();
        InstructorService.addInstructor("Maco", 3);
        controller.setInstructors();
        int k = controller.instructorsList.size();
        assertEquals(1, controller.instructorsList.size());

    }
}
