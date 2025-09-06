package fpsgame;

import fpsgame.game.DesktopLauncher;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("mainScene.fxml"));
        // Parent root = loader.load();
        // Scene scene = new Scene(root);
        // MainController.setStage(stage);
        // stage.setResizable(false);
        // stage.setTitle("FPS Game");
        // stage.setScene(scene);
        // stage.show();
        DesktopLauncher.main(new String[]{});
    }
    
    public static void main(String[] args) {
        System.out.println("🚀 Starting JavaFX Application with Module System and FXML...");
        launch(args);
    }
}


