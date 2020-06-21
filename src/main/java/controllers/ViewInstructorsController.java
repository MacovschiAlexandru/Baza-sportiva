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
import javafx.stage.Stage;
import javafx.util.Callback;
import registration.Client;
import registration.Instructor;
import services.InstructorService;

import java.io.IOException;
import java.util.ArrayList;

public class ViewInstructorsController {

    @FXML
    public TableView<Instructor> instructorTable;

    @FXML
    public TableColumn<Instructor, String> NameColumn;
    @FXML
    public TableColumn<Instructor, Integer> clientsColumn;



    @FXML
    public void initialize() throws IOException {
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        clientsColumn.setCellValueFactory(new PropertyValueFactory<>("clients"));
        addScheduleButtonToTable();
        setInstructors();
        instructorTable.setItems(instructorsList);
    }

    public ObservableList<Instructor> instructorsList;
    public void setInstructors() throws IOException {
        InstructorService.loadInstructorsFromFile();
        instructorsList= FXCollections.observableArrayList(InstructorService.instructors);
    }

    private void addScheduleButtonToTable() {
        TableColumn<Instructor, Void> colBtn = new TableColumn("Schedule Button Column");

        Callback<TableColumn<Instructor, Void>, TableCell<Instructor, Void>> cellFactory = new Callback<TableColumn<Instructor, Void>, TableCell<Instructor, Void>>() {
            @Override
            public TableCell<Instructor, Void> call(final TableColumn<Instructor, Void> param) {
                final TableCell<Instructor, Void> cell = new TableCell<Instructor, Void>() {

                    private final Button btn = new Button("Schedule");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Instructor i = getTableView().getItems().get(getIndex());


                            try {
                                InstructorService.loadUsersFromFile(i.getName());
                                Parent view2= FXMLLoader.load(getClass().getClassLoader().getResource("view_clients2.fxml"));
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

        instructorTable.getColumns().add(colBtn);

    }

    public void Back(ActionEvent event) throws IOException {
        Parent view2= FXMLLoader.load(getClass().getClassLoader().getResource("client_interface.fxml"));
        Scene tableScene=new Scene(view2);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableScene);
        window.show();
    }
}
