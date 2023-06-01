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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class RedactorClientsController {
    int sel, index=-1;
    Connection con;
    ResultSet rs;
    String sql;
    PreparedStatement pst;
    private Stage stage;
    private Scene scene;
    private Parent root;
    ObservableList<ModelTable2> oblist = FXCollections.observableArrayList();

    @FXML
    private TextField RedEmail;

    @FXML
    private TextField RedName;

    @FXML
    private TextField RedNumber;

    @FXML
    private TableColumn<?, ?> TableEmail;

    @FXML
    private TableColumn<?, ?> TableName;

    @FXML
    private TableColumn<?, ?> TableNum;

    @FXML
    private TableView<ModelTable2> TableO;

    @FXML
    private Button back;

    @FXML
    private Button otmena;

    @FXML
    private Button search;

    @FXML
    private Button search1;

    @FXML
    private Button search11;

    @FXML
    private TextField txt_search;

    @FXML
    void backent(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ClientsWindow.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("ООО \"ОПТК\"");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void getSel1(MouseEvent event) throws SQLException {
        con = DBConnector.getConnection();
        rs = con.createStatement().executeQuery("select * from mydb.Client");
        index=TableO.getSelectionModel().getSelectedIndex();
        if (index<=-1){
            return;
        }
        String where = TableName.getCellData(index).toString();
        String what = where;
        rs = con.createStatement().executeQuery("select * from mydb.Client where Name='"+what+"'");
        while (rs.next()){
            sel = rs.getInt("idClient");
            RedName.setText(rs.getString("Name"));
            RedNumber.setText(rs.getString("Number"));
            RedEmail.setText(rs.getString("Email"));
        }
    }

    @FXML
    void search_a(ActionEvent event) {
        try {
            con=DBConnector.getConnection();
            String abon = txt_search.getText();
            rs = con.createStatement().executeQuery("SELECT * FROM mydb.Client WHERE Name LIKE'"+abon+"' or Number LIKE'"+abon+"' or Email LIKE'"+abon+"'");
            while (rs.next()) {
                TableO.getItems().clear();
                oblist.add(new ModelTable2(rs.getString("Name"), rs.getString("Number"),rs.getString("Email")));
                TableO.setItems(oblist);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void RefrashTable () throws SQLException {
        TableName.setCellValueFactory(new PropertyValueFactory<>("TableName"));
        TableNum.setCellValueFactory(new PropertyValueFactory<>("TableNum"));
        TableEmail.setCellValueFactory(new PropertyValueFactory<>("TableEmail"));
        con = DBConnector.getConnection();
        rs = con.createStatement().executeQuery("select * from mydb.Client");
        while (rs.next()) {
            oblist.add(new ModelTable2(rs.getString("Name"), rs.getString("Number"), rs.getString("Email")));
        }
        TableO.setItems(oblist);
    }
    @FXML
    void initialize () throws SQLException {
        RefrashTable();
    }

    @FXML
    void otm(ActionEvent event) throws SQLException {
        TableO.getItems().clear();
        RefrashTable();
    }


    @FXML
    void savent(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Редактор клиента");
        alert.setHeaderText("Принять изменения?");
        alert.setContentText("Вы согласны?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            index = TableO.getSelectionModel().getSelectedIndex();
            if (index <= -1) {
                return;
            }
            String value1 = RedName.getText();
            String value2 = RedNumber.getText();
            String value3 = RedEmail.getText();
            String sql = "update mydb.Client set Name='"+value1+"',Number='"+value2+"',Email='"+value3+"'where idClient='" +sel+ "'";
            pst = con.prepareStatement(sql);
            pst.execute();
            TableO.getItems().clear();
            RefrashTable();
        }
    }

    @FXML
    void delent(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Редактор клиента");
        alert.setHeaderText("Удалить запись клиента?");
        alert.setContentText("Вы согласны?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            index = TableO.getSelectionModel().getSelectedIndex();
            if (index <= -1) {
                return;
            }
            String sql = "delete from mydb.Client where idClient='" +sel+ "'";
            pst = con.prepareStatement(sql);
            pst.execute();
            TableO.getItems().clear();
            RefrashTable();
    }
}
    @FXML
    void creatent(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Редактор клиента");
        alert.setHeaderText("Создать запись клиента?");
        alert.setContentText("Вы согласны?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            String value1 = RedName.getText();
            String value2 = RedNumber.getText();
            String value3 = RedEmail.getText();
            String sql = "INSERT into mydb.Client(Name,Number,Email) values ('"+value1+"','"+value2+"','"+value3 +"')";
            pst = con.prepareStatement(sql);
            pst.execute();
            TableO.getItems().clear();
            RefrashTable();
            }
    }
}
