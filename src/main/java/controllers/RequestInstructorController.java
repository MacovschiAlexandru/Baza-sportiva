package controllers;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import exceptions.NoEntryHour;
import exceptions.NoExitHour;
import exceptions.NoUserName;
import exceptions.UsernameAlreadyExists;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ClientService;
import sun.text.normalizer.UCharacter;

import java.io.IOException;

import static services.ClientService.checkEnHIsNotEmpty;
import static services.ClientService.checkExHIsNotEmpty;

public class RequestInstructorController {

    @FXML
    public  TextField usernameField;
    @FXML
    public TextField entryHour;
    @FXML
    public TextField exitHour;
    @FXML
    public Text requestMessage;
    public static String text;
    public int enH, exH;
    public void requestInstructor(ActionEvent event) throws IOException, NoUserName, NoEntryHour, NoExitHour {
        try{ checkEnHIsNotEmpty(entryHour.getText());
            checkExHIsNotEmpty(exitHour.getText());
            text = usernameField.getText();
            enH = Integer.parseInt(entryHour.getText());
            exH = Integer.parseInt(exitHour.getText());
            ClientService.addRequest(LoginController.getCurrectUsername(), enH, exH);
            requestMessage.setText("your request has been sent!");
        }catch (NoUserName e){
            requestMessage.setText(e.getMessage());
        }catch(NoEntryHour e){
            requestMessage.setText(e.getMessage());
        }catch(NoExitHour e){
            requestMessage.setText(e.getMessage());
        }

    }
   public static String getUser(){return text;}

    public void Back(ActionEvent event) throws IOException {
        Parent view2= FXMLLoader.load(getClass().getClassLoader().getResource("client_interface.fxml"));
        Scene tableScene=new Scene(view2);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableScene);
        window.show();
    }
}
