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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class OrderController {
    String order="", order1="";
    String sql;
    PreparedStatement pst;
    int total1=0, total2=0;
    int index=-1;
    Connection con;
    ResultSet rs;
    private Stage stage;
    private Scene scene;
    private Parent root;
    ObservableList<ModelTable> oblist = FXCollections.observableArrayList();

    @FXML
    private TextArea Order;

    @FXML
    private Button search;

    @FXML
    private TextField txt_search;

    @FXML
    private TableColumn<?, ?> TableCon;

    @FXML
    private TableColumn<?, ?> TableName;

    @FXML
    private TableView<ModelTable> TableO;

    @FXML
    private TableColumn<?, ?> TablePrice;

    @FXML
    private Button YeapButton;

    @FXML
    private Label label;

    @FXML
    private Button otmena;

    @FXML
    private Button ClearButton;

    private void Alerti(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Ошибка!");
        alert.setContentText("Выберите услуги из перечня!");
        alert.showAndWait();
    }

    @FXML
    void YeapEnt(ActionEvent event) throws SQLException, IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Выберите");
        alert.setHeaderText("Перейти к оформлению заказа?");

        ButtonType Registration = new ButtonType("Да");
        ButtonType Choice = new ButtonType("Отмена");

        alert.getButtonTypes().clear();

        alert.getButtonTypes().addAll(Registration, Choice);

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {
        } else if (option.get() == Choice) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("YeapWindow.fxml"));
                Parent root = loader.load();
                YeapController yeapController = loader.getController();
                yeapController.setOrder1(order1);
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("ООО \"ОПТК\"");
                stage.setScene(scene);
                stage.show();
//            НОВОЕ ОКНО, ПОВЕРХ СТАРОГО!!!!!!

//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("YeapOrderWindow.fxml"));
//            try {
//                loader.load();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            YeapButton.getScene().getWindow();
//            Parent root = loader.getRoot();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.setTitle("ООО \"ПРОКАТ-АВТО\"");
//            stage.showAndWait();
//            stage.setResizable(false);

        } else if (option.get() == Registration) {
            if(Order.getText().trim().length()!=0){
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("YeapWindow.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("ООО \"ОПТК\"");
                stage.setScene(scene);
                stage.show();
            }
            else {
                Alerti();
            }
        }
    }

    public void RefrashTable () throws SQLException {
        TableName.setCellValueFactory(new PropertyValueFactory<>("TableName"));
        TableCon.setCellValueFactory(new PropertyValueFactory<>("TableCon"));
        TablePrice.setCellValueFactory(new PropertyValueFactory<>("TablePrice"));
        con = DBConnector.getConnection();
        rs = con.createStatement().executeQuery("select * from mydb.Products");
        while (rs.next()) {
            oblist.add(new ModelTable(rs.getString("Name"), rs.getString("Composition"), rs.getString("Price")));
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

    public void getSel1(javafx.scene.input.MouseEvent mouseEvent) throws SQLException {
        con = DBConnector.getConnection();
        rs = con.createStatement().executeQuery("select * from mydb.Products");
        index=TableO.getSelectionModel().getSelectedIndex();
        if (index<=-1){
            return;
        }
        String where = TableName.getCellData(index).toString();
        String what = where;
        rs = con.createStatement().executeQuery("select * from mydb.Products where Name='"+what+"'");
        while (rs.next()){
            order = rs.getString("Name") + ", ";
            total1 = rs.getInt("Price");
        }
        order1 = order1+order;
        total2 = total2+total1;
        Order.setText(order1);
        label.setText(String.valueOf(total2+" руб."));
    }

    @FXML
    void search_a(ActionEvent event) {
        try {
            con=DBConnector.getConnection();
            String abon = txt_search.getText();
            rs = con.createStatement().executeQuery("SELECT * FROM mydb.Products WHERE Name LIKE'"+abon+"'");
            while (rs.next()) {
                TableO.getItems().clear();
                oblist.add(new ModelTable(rs.getString("Name"), rs.getString("Composition"),rs.getString("Price")));
                TableO.setItems(oblist);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void ClearEnt(ActionEvent event) {
        order1 = "";
        total2 = 0;
        Order.clear();
        label.setText("");
    }


}
