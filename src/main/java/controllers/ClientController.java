package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import services.FileSystemService;
import services.UserService;

import java.io.IOException;
import java.nio.file.Path;

public class ClientController {

    public void requestInstructor(ActionEvent event)throws IOException {
        Parent view2= FXMLLoader.load(getClass().getClassLoader().getResource("request_panel.fxml"));
        Scene tableScene=new Scene(view2);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableScene);
        window.show();
    }

    public void viewInstructors(ActionEvent event)throws IOException {
        Parent view2= FXMLLoader.load(getClass().getClassLoader().getResource("view_instructors.fxml"));
        Scene tableScene=new Scene(view2);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableScene);
        window.show();
    }
    public void Back(ActionEvent event)throws IOException {
        Parent view2= FXMLLoader.load(getClass().getClassLoader().getResource("client_interface.fxml"));
        Scene tableScene=new Scene(view2);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableScene);
        window.show();
    }

    public void handleRequestButtonAction(ActionEvent event) {

    }

    public void BackToLogin(ActionEvent event)throws IOException {
        Parent view2= FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        Scene tableScene=new Scene(view2);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableScene);
        window.show();
    }
}
