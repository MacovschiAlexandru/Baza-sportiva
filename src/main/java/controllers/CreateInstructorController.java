package controllers;

import exceptions.NoPassword;
import exceptions.NoUserName;
import exceptions.UsernameAlreadyExists;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.FileSystemService;
import services.UserService;

import java.io.IOException;
import java.nio.file.Path;

public class CreateInstructorController {
    @FXML
    public Text creationMessage;
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public void BackToAdminInterfaceFromCreation(ActionEvent event) throws IOException {
        Parent view2= FXMLLoader.load(getClass().getClassLoader().getResource("admin_interface.fxml"));
        Scene tableScene=new Scene(view2);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableScene);
        window.show();
    }

    public void handleCreationButton(ActionEvent event) {
        try {
            UserService.addInstructor(usernameField.getText(), passwordField.getText());
            creationMessage.setText("Account created successfully!");
        } catch (UsernameAlreadyExists e) {
            creationMessage.setText(e.getMessage());
        } catch (NoUserName e){
            creationMessage.setText(e.getMessage());
        } catch (NoPassword e){
            creationMessage.setText(e.getMessage());
        }
    }

}
