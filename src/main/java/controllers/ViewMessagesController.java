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
import registration.Message;
import services.InstructorService;
import services.MessageService;

import java.io.IOException;

public class ViewMessagesController {
    @FXML
    public TableView<Message> messageTable;
    @FXML
    public TableColumn<Message, String> nameColumn;
    @FXML
    public TableColumn<Message, String> messageColumn;

    @FXML
    public void initialize() throws IOException {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("instructor"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        deleteMessageButtonToTable();
        setMessages();
        messageTable.setItems(messageList);
    }
    public ObservableList<Message> messageList;
    public void setMessages() throws IOException {
        MessageService.loadMessagesFromFile(LoginController.getCurrectUsername());
        messageList= FXCollections.observableArrayList(MessageService.messages);
    }

    private void deleteMessageButtonToTable() {
        TableColumn<Message, Void> colBtn = new TableColumn("Clear");

        Callback<TableColumn<Message, Void>, TableCell<Message, Void>> cellFactory = new Callback<TableColumn<Message, Void>, TableCell<Message, Void>>() {
            @Override
            public TableCell<Message, Void> call(final TableColumn<Message, Void> param) {
                final TableCell<Message, Void> cell = new TableCell<Message, Void>() {

                    private final Button btn = new Button("Clear");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Message message = getTableView().getItems().get(getIndex());

                            try {


                                messageTable.getItems().remove(getTableView().getItems().get(getIndex()));
                                MessageService.deleteMessage(message.getMessage(),message.getInstructor());
                                MessageService.loadMessagesFromFile(LoginController.getCurrectUsername());
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

        messageTable.getColumns().add(colBtn);

    }


    public void Back(ActionEvent event) throws IOException {
        Parent view2= FXMLLoader.load(getClass().getClassLoader().getResource("client_interface.fxml"));
        Scene tableScene=new Scene(view2);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableScene);
        window.show();
    }
}
