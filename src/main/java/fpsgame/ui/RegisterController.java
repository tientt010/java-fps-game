package fpsgame.ui;

import java.util.function.Consumer;

import fpsgame.network.packets.GeneralPackets.RegisterRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class RegisterController {
    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private Label warningLabel;

    @FXML
    private AnchorPane registerForm;
    private Runnable onSwitchToLogin;
    private Consumer<RegisterRequest> onSubmit;

    public void initialize() {
        System.out.println("RegisterController initialized");
    }

    public void onRegisterSubmit(){
        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText().trim();
        String confirmPassword = confirmPasswordTextField.getText().trim();
        String email = emailTextField.getText().trim();

        if(!password.equals(confirmPassword)){
            warningLabel.setText("Password and confirm password must be the same");
            return;
        }
        System.out.println("Registering user: " + username);
        onSubmit.accept(new RegisterRequest(username, password, email));
    }

    public void toLoginForm() {
        onSwitchToLogin.run();
    }

    public void setOnSwitchToLogin(Runnable onSwitchToLogin) {
        this.onSwitchToLogin = onSwitchToLogin;
    }

    public void setOnSubmit(Consumer<RegisterRequest> onSubmit) {
        this.onSubmit = onSubmit;
    }


}
