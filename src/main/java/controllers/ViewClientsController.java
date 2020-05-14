package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import registration.Client;
import javafx.stage.Stage;
import registration.User;
import services.InstructorService;

import java.io.IOException;

public class ViewClientsController {

    @FXML
    public TableView<Client> clientTable;

    @FXML
    public TableColumn<Client, String> clientNameColumn;
    @FXML
    public TableColumn<Client, Double> clientEntryHourColumn;
    @FXML
    public TableColumn<Client, Double> clientExitHourColumn;

    @FXML
    public void initialize() throws IOException {
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
        clientEntryHourColumn.setCellValueFactory(new PropertyValueFactory<>("entryHour"));
        clientExitHourColumn.setCellValueFactory(new PropertyValueFactory<>("exitHour"));
        setClients();
        clientTable.setItems(clientList);
    }

    private ObservableList<Client> clientList;
    private void setClients() throws IOException {
        InstructorService.loadUsersFromFile();
            clientList= FXCollections.observableArrayList(InstructorService.clients);
    }


    public void Back(ActionEvent event) throws IOException {
        Parent view2= FXMLLoader.load(getClass().getClassLoader().getResource("instructor_interface.fxml"));
        Scene tableScene=new Scene(view2);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableScene);
        window.show();
    }
}


