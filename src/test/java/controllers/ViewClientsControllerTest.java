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
import registration.Client;
import registration.Instructor;
import services.FileSystemService;
import services.InstructorService;
import services.UserService;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

public class ViewClientsControllerTest extends ApplicationTest {

private static final String TEST_INSTRUCTOR = "testInstructor";
private static final String TEST_PASSWORD = "testPassword";
public ViewClientsController controller;
public CreateInstructorController insController;

@BeforeClass
    public static void setupClass() throws Exception{
    FileSystemService.APPLICATION_FOLDER = ".test-registration-example";
    FileSystemService.initApplicationHomeDirIfNeeded();
}
@Before
    public void setup() throws Exception{
    FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
    controller = new ViewClientsController();
    controller.clientTable = new TableView<Client>();
    controller.clientNameColumn = new TableColumn<Client, String>();
    controller.clientEntryHourColumn = new TableColumn<Client, Double>();
    controller.clientExitHourColumn = new TableColumn<Client, Double>();
    insController = new CreateInstructorController();
    insController.creationMessage = new Text();
    insController.usernameField = new TextField();
    insController.passwordField = new PasswordField();
    insController.passwordField.setText(TEST_PASSWORD);
    insController.usernameField.setText(TEST_INSTRUCTOR);
    insController.handleCreationButton(new ActionEvent());
}
@Test
    public void testSetClients() throws IOException{
    InstructorService.loadUsersFromFile(TEST_INSTRUCTOR);
    InstructorService.addClient("Alex", 10, 13);
    controller.setClients();
    int k = controller.clientList.size();
    assertEquals(k,controller.clientList.size());
}
}
