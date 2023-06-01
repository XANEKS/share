package com.example.diplom;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class HelloController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    Connection con;
    ResultSet rs;
    ResultSet rs1;

    @FXML
    private Label label;

    @FXML
    private TextField login;

    @FXML
    private TextField password;


    @FXML
    void ent(ActionEvent event) throws SQLException, IOException {
        String log = login.getText();
        String pass = password.getText();
        try {
            if (!log.equals("") && !pass.equals("")) {
                con = DBConnector.getConnection();
                rs = con.createStatement().executeQuery("SELECT * FROM mydb.managers where Login ='" + log + "' and Password = '" + pass + "' and idLevel = '1'");
                while (rs.next()) {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("OrderWindow.fxml")));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setTitle("ООО \"ОПТК\"");
                    stage.setScene(scene);
                    stage.show();
                }
                rs1 = con.createStatement().executeQuery("SELECT * FROM mydb.managers where Login ='" + log + "' and Password = '" + pass + "' and idLevel = '2'");
                while (rs1.next()) {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("OrderWindow.fxml")));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setTitle("ООО \"ОПТК\"");
                    stage.setScene(scene);
                    stage.show();
                    }
            } else {
                label.setText("Введите данные");
            }
        } finally {
            label.setText("Пользователь не найден");
        }
    }
}