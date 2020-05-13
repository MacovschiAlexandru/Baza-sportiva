package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.InstructorService;

import java.io.IOException;

public class InstructorController {
    public void viewClients(ActionEvent actionEvent) throws IOException {
        InstructorService.loadUsersFromFile();
        InstructorService.afisare();
    }




    public void response(ActionEvent actionEvent) {
    }

    public void Back(ActionEvent event) throws IOException {
        Parent view2= FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        Scene tableScene=new Scene(view2);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableScene);
        window.show();
    }
}
