package controllers;

import exceptions.InvalidPassword;
import exceptions.InvalidUsername;
import exceptions.NoPassword;
import exceptions.NoUserName;
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
import java.util.Objects;


public class LoginController {

    @FXML
    public Text loginMessage;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField usernameField;
    public static String text;

    @FXML
    public void handleLoginButtonAction(ActionEvent event) throws IOException, InvalidPassword, NoPassword, NoUserName, InvalidUsername{
        try {
            UserService.checkUser(usernameField.getText(), passwordField.getText());
            if(Objects.equals(UserService.getRole(),"admin"))
            { Parent view2= FXMLLoader.load(getClass().getClassLoader().getResource("admin_interface.fxml"));
                Scene tableScene=new Scene(view2);
                text=usernameField.getText();
                Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(tableScene);
                window.show();
            }
            else
            if(Objects.equals(UserService.getRole(),"Instructor")){
                Parent view2= FXMLLoader.load(getClass().getClassLoader().getResource("instructor_interface.fxml"));
                Scene tableScene=new Scene(view2);
                text=usernameField.getText();
                Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(tableScene);
                window.show();
            }
            else
            if(Objects.equals(UserService.getRole(),"client")){
                Parent view2= FXMLLoader.load(getClass().getClassLoader().getResource("client_interface.fxml"));
                Scene tableScene=new Scene(view2);
                text=usernameField.getText();
                Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(tableScene);
                window.show();
            }




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
    public static String getCurrectUsername()
    {
        return text;
    }


    public void Register(ActionEvent event) throws IOException {
        Parent view2= FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        Scene tableScene=new Scene(view2);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableScene);
        window.show();
    }

}
