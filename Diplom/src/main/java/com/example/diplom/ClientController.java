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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class ClientController {
    Connection con;
    ResultSet rs;
    private Stage stage;
    private Scene scene;
    private Parent root;
    int index=-1;

    ObservableList<ModelTable2> oblist = FXCollections.observableArrayList();
    @FXML
    private TableColumn<?, ?> TableEmail;

    @FXML
    private TableColumn<?, ?> TableName;

    @FXML
    private TableColumn<?, ?> TableNum;

    @FXML
    private TableView<ModelTable2> TableO;

    @FXML
    private Button otmena;

    @FXML
    private Button search;

    @FXML
    private TextField txt_search;
    @FXML
    void backent(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("RegWindow.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("ООО \"ОПТК\"");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void redactro(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("RedactorClientsWindow.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("ООО \"ОПТК\"");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void getSel1(MouseEvent event) throws SQLException, IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Выбор клиента");
        alert.setHeaderText("Присутпить к офрмлению заказа");
        alert.setContentText("Вы согласны?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        con = DBConnector.getConnection();
        rs = con.createStatement().executeQuery("select * from mydb.Client");
        index=TableO.getSelectionModel().getSelectedIndex();
        if (index<=-1){
            return;
        }
        String where = TableName.getCellData(index).toString();
        rs = con.createStatement().executeQuery("select * from mydb.Client where Name='"+where+"'");
        while (rs.next()){
        }
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("OrderWindow.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("ООО \"ОПТК\"");
        stage.setScene(scene);
        stage.show();
    }
}

    @FXML
    void search_a(ActionEvent event) {
        try {
            con=DBConnector.getConnection();
            String abon = txt_search.getText();
            rs = con.createStatement().executeQuery("SELECT * FROM mydb.Client WHERE Name LIKE'"+abon+"'");
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
}
