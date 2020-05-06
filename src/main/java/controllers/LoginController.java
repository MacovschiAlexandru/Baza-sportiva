package Controllers;

import Exceptions.InvalidPassword;
import Exceptions.InvalidUsername;
import Exceptions.NoPassword;
import Exceptions.NoUserName;
import Services.UserService;
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


public class LoginController {

    @FXML
    public Text loginMessage;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField usernameField;

    @FXML
    public void handleLoginButtonAction() throws InvalidPassword, NoPassword, NoUserName, InvalidUsername {
        try {
            UserService.checkUser(usernameField.getText(), passwordField.getText());
            loginMessage.setText("e ok");

        } catch (InvalidUsername e) {
            loginMessage.setText(e.getMessage());

        } catch (InvalidPassword e) {
            loginMessage.setText(e.getMessage());

        } catch (NoUserName e) {
            loginMessage.setText(e.getMessage());

        } catch (NoPassword e) {
            loginMessage.setText(e.getMessage());

        }
    }

    public void Register(ActionEvent event) throws IOException {
        Parent view2= FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        Scene tableScene=new Scene(view2);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableScene);
        window.show();
    }
}
