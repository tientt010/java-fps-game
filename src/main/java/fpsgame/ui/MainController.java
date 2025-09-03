package fpsgame.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fpsgame.network.ResponseHandler;
import fpsgame.network.TCPClient;
import fpsgame.network.User;
import fpsgame.network.protocol.LoginRequest;
import fpsgame.network.protocol.LoginResponse;
import fpsgame.network.protocol.RegisterRequest;
import fpsgame.network.protocol.RegisterResponse;
import fpsgame.utils.Config;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController implements ResponseHandler, Initializable {

    private static Stage stage;

    
    private Scene scene;
    private Parent root;

    

    private static LoginController loginController;
    private static RegisterController registerController;
    private static OnlineModeController onlineModeController;

    private static User user;

    private static TCPClient tcpClient;

    @FXML
    private AnchorPane secondaryAnchorPane;

    public static void setClient(TCPClient tcpClient) {
        MainController.tcpClient = tcpClient;
    }

    public static void setStage(Stage stage) {
        MainController.stage = stage;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {       
        if (secondaryAnchorPane != null) {
            double duration = 0.2;
            AnimationUtils.applyEffect(secondaryAnchorPane, duration);
        }
    }

    public void quit(ActionEvent event) {
        System.exit(0);
    }

    public FXMLLoader switchScene(String fxmlFile) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fpsgame/" + fxmlFile));
            root = loader.load();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/fpsgame/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            return loader;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getCause());
            System.out.println(e);
        }
        return null;
    }

    @Override
    public void handleLoginResponse(LoginResponse response) {
        if(!response.isSuccess) {
            System.out.println("Login failed: " + response.message);
            return;
        }
        System.out.println(response);
        user = new User(response.userId, response.username, response.email, response.score);

        new Thread(() -> {
            Platform.runLater(() -> {
                System.out.println("Login successful: " + user.username);
                onlineMode();
            });
        }).start();
    }

    @Override
    public void handleRegisterResponse(RegisterResponse response) {
        if(!response.isSuccess) {
            System.out.println("Register failed: " + response.message);
            return;
        }
        System.out.println(response);
        new Thread(() -> {
            Platform.runLater(() -> {
                System.out.println("Register successful: " + response.message);
                switchToLogin();
            });
        }).start();
    }

    public void onlineMode(){
        if(user == null){
            System.out.println("User is not logged in.");
            return;
        }

        FXMLLoader loader = switchScene("onlineMode.fxml");
        onlineModeController = loader.getController();
        onlineModeController.setUser(user);
        onlineModeController.setTCPClient(tcpClient);

        System.out.println("Kết nối với server và đăng nhập thành công");
    }

    public void switchToLogin(){
        if(tcpClient == null){
            try{
                tcpClient = new TCPClient();
                tcpClient.connect(Config.serverAddress, Config.serverTcpPort).thenRun(() -> {
                    System.out.println("Chuyển sang màn hình đăng nhập");
                    tcpClient.setLoginResponseHandler(this::handleLoginResponse);
                    tcpClient.setRegisterResponseHandler(this::handleRegisterResponse);
                });
            }catch(Exception e){
                System.out.println("Error connecting to server: " + e.getMessage());
            }
        }

        FXMLLoader loader = switchScene("loginScene.fxml");
        loginController = loader.getController();
        loginController.setOnSubmit((loginRequest) -> {
            LoginRequest req = (LoginRequest) loginRequest;
            tcpClient.sendLoginRequest(req.username, req.password);
        });

        loginController.setOnSwitchToRegister(() -> {
            switchToRegister();
        });
    }

    public void switchToRegister(){
        FXMLLoader loader = switchScene("registerScene.fxml");
        registerController = loader.getController();
        registerController.setOnSubmit((registerRequest) -> {
            RegisterRequest req = (RegisterRequest) registerRequest;
            tcpClient.sendRegisterRequest(req.username, req.password, req.email);
        });

        registerController.setOnSwitchToLogin(() -> {
            switchToLogin();
        });
        
    }

}
