package controllers;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
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

public class RequestInstructorController {

    @FXML
    public  TextField usernameField;
    @FXML
    private TextField entryHour;
    @FXML
    private TextField exitHour;
    @FXML Text requestMsg;
    public static String text;
    public int enH, exH;
    public void requestInstructor(ActionEvent event) throws NoUserName, IOException {
        try{
            text = usernameField.getText();
            enH = Integer.parseInt(entryHour.getText());
            exH = Integer.parseInt(exitHour.getText());
            ClientService.addRequest(LoginController.getCurrectUsername(), enH, exH);
        }catch (NoUserName e){
            requestMsg.setText("instructor not found");
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
