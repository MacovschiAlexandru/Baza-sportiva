package Sample;

import Exceptions.NoPassword;
import Exceptions.NoUserName;
import Exceptions.UsernameAlreadyExists;
import Services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


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
}