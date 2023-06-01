package com.example.diplom;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class YeapController {
        private String order1;

        public void setOrder1(String order1) {
            this.order1 = order1;
        }

    String[] PaymentMethod = {"Наличный расчёт", "Безналичный расчёт"};
    Connection con;
    ResultSet rs;

    PreparedStatement pst;

    String sql;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextArea CommentArea;

    @FXML
    private TextArea AddresArea;

    @FXML
    private TextField EmailField;

    @FXML
    private TextField NameField;

    @FXML
    private TextField NumberField;

    @FXML
    private ChoiceBox<String> PaymentBox;

    @FXML
    private Button YeapButton;


    @FXML
    void initialize (){
        PaymentBox.getItems().addAll("Наличный расчёт","Безналичный расчёт");
        AddresArea.setText(order1);
        System.out.println(order1);
    }
    private void Alerti(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Ошибка!");
        alert.setContentText("Заполните поля!");
        alert.showAndWait();
    }

    private void UpOrder(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Оплата завершена");
        alert.setContentText("Ваш заказ принят.");
        alert.showAndWait();
    }

    @FXML
    void YeapEnt(ActionEvent event) throws SQLException, IOException {
        if(NameField.getText().trim().length()!=0 & NumberField.getText().trim().length()!=0){
            con = DBConnector.getConnection();
            UpOrder();
            String ClientName, ClientNumber, Email, Addres;
            ClientName = NameField.getText();
            ClientNumber = NumberField.getText();
            Email = EmailField.getText();
            Addres = AddresArea.getText();
            sql = "INSERT into apex.Client(Name, Number, Email) values ('" + ClientName + "', '" + ClientNumber + "', '" + Email + "')";
            pst = con.prepareStatement(sql);
            pst.execute();
            String da1 = String.valueOf(NameField);
            rs = con.createStatement().executeQuery("SELECT * FROM apex.Client where Name ='" + da1 + "'");
            while (rs.next()) {
                sql = "INSERT into apex.Orders(ClientId) values ('"+rs.getString("IdClient")+"')";
            }
        }
        else {
            Alerti();
        }
    }
}
