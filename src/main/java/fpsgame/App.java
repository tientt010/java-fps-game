package fpsgame;

import fpsgame.ui.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        MainController.setStage(stage);
        stage.setResizable(false);
        stage.setTitle("FPS Game");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        System.out.println("🚀 Starting JavaFX Application with Module System and FXML...");
        launch(args);
    }
}


