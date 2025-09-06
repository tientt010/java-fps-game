package fpsgame.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import fpsgame.utils.Config;

public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("FPS Game");
        config.setWindowedMode(Config.SCR_WIDTH, Config.SCR_HEIGHT);
        new Lwjgl3Application(new MainGame(), config);
    }
}