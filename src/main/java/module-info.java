
module fpsgame {
    // Java Platform modules
    requires java.base;
    requires java.desktop;
    requires java.logging;
    
    // JavaFX modules
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.base;
    requires javafx.graphics;
    
    requires io.netty.transport;
    requires io.netty.codec;
    requires io.netty.handler;
    requires io.netty.buffer;
    requires io.netty.common;
    
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    
    exports fpsgame;
    exports fpsgame.ui;
    exports fpsgame.network;
    exports fpsgame.network.protocol;
    exports fpsgame.utils;
    
    opens fpsgame to javafx.fxml;
    opens fpsgame.ui to javafx.fxml, javafx.base;
    opens fpsgame.network.protocol to com.fasterxml.jackson.databind;
    
    opens fpsgame.utils to javafx.fxml, com.fasterxml.jackson.databind;
}
