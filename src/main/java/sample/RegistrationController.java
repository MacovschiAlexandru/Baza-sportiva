package sample;

import exceptions.NoPassword;
import exceptions.NoUserName;
import exceptions.UsernameAlreadyExists;
import services.UserService;
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

import java.io.IOException;


public class RegistrationController {

    @FXML
    private Text registrationMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    public void handleRegisterAction() {
        try {
            UserService.addUser(usernameField.getText(), passwordField.getText());
            registrationMessage.setText("Account created successfully!");
        } catch (UsernameAlreadyExists e) {
            registrationMessage.setText(e.getMessage());
        } catch (NoUserName e){
            registrationMessage.setText(e.getMessage());
        } catch (NoPassword e){
            registrationMessage.setText(e.getMessage());
        }
    }

    public void Back(ActionEvent event) throws IOException {
        Parent view2= FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        Scene tableScene=new Scene(view2);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableScene);
        window.show();
    }
}
