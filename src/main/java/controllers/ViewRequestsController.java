package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import registration.Client;
import javafx.stage.Stage;
import registration.Instructor;
import registration.User;
import services.InstructorService;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Objects;

public class ViewRequestsController {

    @FXML
    public TableView<Client> clientTable;

    @FXML
    public TableColumn<Client, String> clientNameColumn;
    @FXML
    public TableColumn<Client, Double> clientEntryHourColumn;
    @FXML
    public TableColumn<Client, Double> clientExitHourColumn;
    public static String clientToSend;


    @FXML
    public void initialize() throws IOException {
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
        clientEntryHourColumn.setCellValueFactory(new PropertyValueFactory<>("entryHour"));
        clientExitHourColumn.setCellValueFactory(new PropertyValueFactory<>("exitHour"));
        addApprovalButtonToTable();
        addRejectionButtonToTable();
        setClients();
        clientTable.setItems(requestList);
    }

    private ObservableList<Client> requestList;
    public void setClients() throws IOException {
        InstructorService.loadRequestsFromFile();
        requestList= FXCollections.observableArrayList(InstructorService.requests);
    }

    private void addApprovalButtonToTable() {
        TableColumn<Client, Void> colBtn = new TableColumn("Accept Button Column");

        Callback<TableColumn<Client, Void>, TableCell<Client, Void>> cellFactory = new Callback<TableColumn<Client, Void>, TableCell<Client, Void>>() {
            @Override
            public TableCell<Client, Void> call(final TableColumn<Client, Void> param) {
                final TableCell<Client, Void> cell = new TableCell<Client, Void>() {

                    private final Button btn = new Button("Accept");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Client client = getTableView().getItems().get(getIndex());
                            clientToSend=client.getClient();
                            System.out.println(clientToSend);
                            try {
                                InstructorService.loadUsersFromFile(LoginController.getCurrectUsername());
                                InstructorService.loadInstructorsFromFile();
                                InstructorService.changeNumberOfClients(LoginController.getCurrectUsername(),clientToSend);
                                InstructorService.addClient(client.getClient(),client.getEntryHour(),client.getExitHour());


                                Parent view2= FXMLLoader.load(getClass().getClassLoader().getResource("send_message.fxml"));
                                Scene tableScene=new Scene(view2);
                                Stage window=new Stage();
                                window.setScene(tableScene);
                                window.show();

                                clientTable.getItems().remove(getTableView().getItems().get(getIndex()));
                                InstructorService.deleteRequest(client.getClient());
                                InstructorService.loadRequestsFromFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        clientTable.getColumns().add(colBtn);

    }

    public static String getClientToSend()
    {
        return clientToSend;
    }

    private void addRejectionButtonToTable() {
        TableColumn<Client, Void> colBtn = new TableColumn("Reject Button Column");

        Callback<TableColumn<Client, Void>, TableCell<Client, Void>> cellFactory = new Callback<TableColumn<Client, Void>, TableCell<Client, Void>>() {
            @Override
            public TableCell<Client, Void> call(final TableColumn<Client, Void> param) {
                final TableCell<Client, Void> cell = new TableCell<Client, Void>() {

                    private final Button btn = new Button("Reject");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Client client = getTableView().getItems().get(getIndex());
                            clientToSend=client.getClient();
                            System.out.println(clientToSend);
                            try {
                                clientTable.getItems().remove(getTableView().getItems().get(getIndex()));
                                InstructorService.deleteRequest(client.getClient());
                                InstructorService.loadRequestsFromFile();

                                Parent view2= FXMLLoader.load(getClass().getClassLoader().getResource("send_message.fxml"));
                                Scene tableScene=new Scene(view2);
                                Stage window=new Stage();
                                window.setScene(tableScene);
                                window.show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        clientTable.getColumns().add(colBtn);

    }


    public void Back(ActionEvent event) throws IOException {
        Parent view2= FXMLLoader.load(getClass().getClassLoader().getResource("instructor_interface.fxml"));
        Scene tableScene=new Scene(view2);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableScene);
        window.show();
    }
}


