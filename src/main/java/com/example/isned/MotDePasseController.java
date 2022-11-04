package com.example.isned;

import com.example.isned.DatabaseConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;


//import static com.example.isned.Location.db;


public class MotDePasseController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button buttonSortir;

    @FXML
    private Button boutonRetour;

    @FXML
    private Button buttonConfirmer;

    @FXML
    private TextField textId;

    @FXML
    private TextField textMdp;
    @FXML
    private Label labelErreur;
    @FXML
    private TextField textMdpConfirmer;

    @FXML
    void recuprerInfo(ActionEvent event) throws SQLException {
      if(!textId.getText().isBlank() && (!textMdp.getText().isBlank())){
          changer_password();
          labelErreur.setText("mot de passe modifier avec succées");
      }else{
          labelErreur.setText("remplir les champs ");
      }
    }

    private void changer_password() throws SQLException {
        DatabaseConnection db =DatabaseConnection.getInstance(); ;
        Connection con=db.getConnection();
        try{
            Statement statement=con.createStatement();
            statement.executeUpdate("update admin set mdp='"+textMdpConfirmer.getText()+"'");

        }catch(SQLException e){
        e.printStackTrace();
        e.getCause();}
    }

    @FXML
    void retourLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load((getClass()).getResource("login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void sortir(ActionEvent event) {
        Platform.exit();
    }
}
