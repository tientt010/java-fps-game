package fpsgame.ui;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import fpsgame.network.TCPClient;
import fpsgame.network.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class OnlineModeController implements Initializable {

    private  User user;
    private TCPClient tcpClient;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField nameField;

    @FXML
    private Button helloButton;

    @FXML
    private Button clearButton;

    @FXML
    private Label messageLabel;

    @FXML
    private Label moduleLabel;

    @FXML
    private Label statusLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("GameController initialized successfully!");

        // Update module info`
        // Set focus to name field
        Platform.runLater(() -> nameField.requestFocus());
    }

    @FXML
    private void onHelloButtonClick() {
        String name = nameField.getText().trim();
        
        if (name.isEmpty()) {
            messageLabel.setText("⚠️ Please enter your name first!");
            messageLabel.setStyle("-fx-text-fill: #f44336; -fx-font-weight: bold;");
        } else {
            String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            messageLabel.setText("🎮 Hello " + name + "! Welcome to FPS Game! (" + currentTime + ")");
            messageLabel.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;");
        }
        
        System.out.println("Hello button clicked by: " + (name.isEmpty() ? "anonymous" : name));
    }

    @FXML
    private void onClearButtonClick() {
        nameField.clear();
        messageLabel.setText("");
        
        // Update status
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        statusLabel.setText("Status: Cleared at " + currentTime);
        
        // Focus back to name field
        Platform.runLater(() -> nameField.requestFocus());
        
        System.out.println("Clear button clicked - fields reset");
    }

    public String getCurrentMessage() {
        return messageLabel.getText();
    }

    public String getCurrentName() {
        return nameField.getText();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTCPClient(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }
}
