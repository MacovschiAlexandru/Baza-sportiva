package controllers;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import registration.Message;

public class ViewMessagesController {
    public TableView<Message> messageTable;
    public TableColumn<Message, String> nameColumn;
    public TableColumn<Message, String> messageColumn;
}
