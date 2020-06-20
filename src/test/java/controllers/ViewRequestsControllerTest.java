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
import services.ClientService;
import services.FileSystemService;
import services.InstructorService;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ViewRequestsControllerTest extends ApplicationTest {
    private static final String TEST_USERNAME = "testUsername";
    private static final String TEST_PASSWORD = "testPassword";
    public ViewRequestsController controller;
    public CreateInstructorController insController;

    @BeforeClass
    public static void setupClass() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration-example";
        FileSystemService.initApplicationHomeDirIfNeeded();
    }
    @Before
    public void setup() throws Exception{
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
        insController = new CreateInstructorController();
        insController.creationMessage = new Text();
        insController.usernameField = new TextField();
        insController.passwordField = new PasswordField();
        insController.passwordField.setText(TEST_PASSWORD);
        insController.usernameField.setText(TEST_USERNAME);
        insController.handleCreationButton(new ActionEvent());
        controller = new ViewRequestsController();
        controller.clientTable = new TableView<Client>();
        controller.clientEntryHourColumn = new TableColumn<Client, Double>();
        controller.clientExitHourColumn = new TableColumn<Client, Double> ();
        controller.clientNameColumn = new TableColumn<Client, String>();
    }
@Test
    public void testSetRequests() throws IOException{
    InstructorService.loadRequestsFromFile();
    InstructorService.addClient("Andrei", 10, 12);
    controller.setClients();
    int k = controller.requestList.size();
    assertEquals(k, controller.requestList.size());
}
}

