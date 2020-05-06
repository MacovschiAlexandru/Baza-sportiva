package controllers;

import exceptions.InstructorNotFound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.UserService;

import java.io.IOException;


public class DeleteInstructorController {
    @FXML
    public Text deleteMessage;
    @FXML
    public TextField usernameField;
    @FXML
    public void handleDeleteButton(ActionEvent actionEvent) throws IOException {
        try{
            UserService.deleteInstructor(usernameField.getText());
            deleteMessage.setText("Instructor has been removed!");
        }catch (InstructorNotFound e) {
            deleteMessage.setText(e.getMessage());
        }
    }


    public void BackToAdminInterfaceFromDeletion(ActionEvent event)throws IOException {
        Parent view2= FXMLLoader.load(getClass().getClassLoader().getResource("admin_interface.fxml"));
        Scene tableScene=new Scene(view2);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableScene);
        window.show();
    }
}
