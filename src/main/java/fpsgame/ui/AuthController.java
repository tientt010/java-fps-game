package fpsgame.ui;

import fpsgame.network.protocol.BaseMessage;
import fpsgame.network.protocol.LoginRequest;
import fpsgame.network.protocol.RegisterRequest;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import fpsgame.utils.Invalidate;
import javafx.util.Duration;

public class AuthController implements Initializable {
    @FXML
    private ToggleGroup authGroup;

    @FXML
    private ToggleButton btnLogin;

    @FXML
    private ToggleButton btnRegister;
    @FXML
    private VBox loginForm;
    @FXML
    private VBox registerForm;

    @FXML
    private Consumer<BaseMessage> onSubmit;

    @FXML
    private Label lblLoginError;
    @FXML
    private Label lblRegisterError;

    @FXML
    public void initialize(URL location, ResourceBundle resources){
        btnLogin.setSelected(true);
        System.out.println("LoginController initialized");
        authGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == btnLogin) {
                showLoginForm();
            } else if (newToggle == btnRegister) {
                showRegisterForm();
            }
        });

    }

    private void showLoginForm() {
        loginForm.setVisible(true);
        loginForm.setManaged(true);
        registerForm.setVisible(false);
        registerForm.setManaged(false);
    }

    private void showRegisterForm() {
        registerForm.setVisible(true);
        registerForm.setManaged(true);
        loginForm.setVisible(false);
        loginForm.setManaged(false);
    }
    /* Hiển thị thông báo lỗi */
    public void showError(Label tagetLabel, String message) {
        tagetLabel.setText(message);
        tagetLabel.setWrapText(true);
        tagetLabel.setVisible(true);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.5),e -> tagetLabel.setVisible(false)));
        timeline.play();
    }

   public void onLoginSubmit() {
       String username = ((TextField) loginForm.getChildren().get(0)).getText();
       String password = ((TextField) loginForm.getChildren().get(1)).getText();
       String error = Invalidate.validateLogin(username, password);
       if (error != null) {
           showError(lblLoginError, error);
       }
       else{
           onSubmit.accept(new LoginRequest(username, password));

       }

    }
    public void setOnSubmit(Consumer<BaseMessage> onSubmit) {
        this.onSubmit = onSubmit;
    }

    public void onRegisterSubmit() {
        String username = ((TextField) registerForm.getChildren().get(0)).getText();
        String password = ((TextField) registerForm.getChildren().get(1)).getText();
        String email = ((TextField) registerForm.getChildren().get(2)).getText();
        String confirmPassword = ((TextField) registerForm.getChildren().get(3)).getText();
        String error = Invalidate.validateRegister(username, password, email, confirmPassword);
        if (error != null) {
            showError(lblRegisterError, error);
        }
        else {
            onSubmit.accept(new RegisterRequest(username, password, email));
        }
    }

    /*Getter */
    public Label getLblLoginError() {return lblLoginError;}
    public Label getLblRegisterError() {return lblRegisterError;}

}
