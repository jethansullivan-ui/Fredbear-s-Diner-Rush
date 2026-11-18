module com.fredbeardinerrush {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;

    opens com.fredbeardinerrush.controller to javafx.graphics, javafx.fxml;
    opens com.fredbeardinerrush.view to javafx.graphics;

    exports com.fredbeardinerrush.controller;
    exports com.fredbeardinerrush.model;
    exports com.fredbeardinerrush.service;
    exports com.fredbeardinerrush.view;
}