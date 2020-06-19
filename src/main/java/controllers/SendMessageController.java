package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.MessageService;

import java.io.IOException;

public class SendMessageController {
    @FXML
    public TextField messageField;
    @FXML
    public Text sendingMessage;

    public void handleSendingButton(ActionEvent actionEvent) throws IOException {
        MessageService.loadMessagesFromFile(ViewRequestsController.getClientToSend());
        MessageService.addMessage(messageField.getText());
        messageField.setText("Message sent");
    }
}
