package com.example.isned;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class inscrireController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label labelErreur;
    @FXML
    private Button btnRetouLogin;
    @FXML
    private Button btnAnnuler;
    @FXML
    private Button btnValider;

    @FXML
    private TextField loginInscrire;


    @FXML
    private TextField mdpInscrire;

    @FXML
    void retouLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load((getClass()).getResource("login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public void cree_compte() throws SQLException {
        DatabaseConnection db = DatabaseConnection.getInstance();
        Connection con = db.getConnection();
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate("INSERT INTO `isned`.`admin` VALUES('" + loginInscrire.getText() + "','" + mdpInscrire.getText() + "');");
            labelErreur.setText(("inscription faite avec succés"));
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();

        }
    }
    @FXML
    void validerInscrire(ActionEvent event) throws SQLException {

        DatabaseConnection db = DatabaseConnection.getInstance();
        Connection con = db.getConnection();
        String verifAdmin = ("select count(login) from Admin where login='" + loginInscrire.getText() + "'");
        try {
            Statement statement = con.createStatement();
            ResultSet queryResult = statement.executeQuery(verifAdmin);
            while (queryResult.next()) {
                if (queryResult.getInt(1) < 1) {
                    cree_compte();
                } else {
                    labelErreur.setText("admin deja existe ");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }






    @FXML
    void Sortir(ActionEvent event) {
        Platform.exit();
    }

}
