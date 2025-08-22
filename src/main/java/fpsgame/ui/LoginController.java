package fpsgame.ui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import fpsgame.network.packets.GeneralPackets.LoginRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;


public class LoginController implements Initializable{
    @FXML
    private TextField usernameTextField;
        
    @FXML
    private TextField passwordTextField;

    private Consumer<LoginRequest> onSubmit;
    private Runnable onSwitchToRegister;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        System.out.println("LoginController initialized");
    }

    public void onLoginSubmit(){
        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText().trim();

        onSubmit.accept(new LoginRequest(username, password));
    }

    public void setOnSubmit(Consumer<LoginRequest> onSubmit) {
        this.onSubmit = onSubmit;
    }

    public void registerFormController() {
        System.out.println("Switching to register form");
        onSwitchToRegister.run();
    }

    

    public Runnable getOnSwitchToRegister() {
        return onSwitchToRegister;
    }

    public void setOnSwitchToRegister(Runnable onSwitchToRegister) {
        this.onSwitchToRegister = onSwitchToRegister;
    }

}
