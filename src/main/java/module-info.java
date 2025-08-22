module fpsgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires kryo;
    requires kryonet;
    requires java.desktop;

    opens fpsgame to javafx.fxml, javafx.base, javafx.graphics;
    opens fpsgame.ui to javafx.fxml;
    exports fpsgame.network;
    exports fpsgame.network.packets;
    exports fpsgame;
}
