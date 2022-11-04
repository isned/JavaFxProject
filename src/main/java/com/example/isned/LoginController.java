package com.example.isned;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button btnInscrire;
    @FXML
    private CheckBox checkBox;
    @FXML
    private TextField passwordText;
    @FXML
    private Button buttonSortir;
    @FXML
    private Button LoginBtn;
    @FXML
    private PasswordField mdpTfd;
    @FXML
    private Button annumBtn;




    @FXML
    private TextField loginTfd;
    @FXML
    private Label wronginfo;

    @FXML
    private Hyperlink mdpOublié;



    public void login(ActionEvent event) throws IOException, SQLException {
        if(!loginTfd.getText().isBlank() && !mdpTfd.getText().isBlank()){
            validloginadmin();
        }else{
            wronginfo.setText("svp donner vos infos");
        }

    }

    private void validloginadmin() throws SQLException {
        DatabaseConnection db =DatabaseConnection.getInstance(); ;
        
        Connection con=db.getConnection();
        String req="select count(1) from Admin where login='"+loginTfd.getText()+"' and mdp='"+mdpTfd.getText()+"'";

        try {
            Statement st = con.createStatement();
            ResultSet queryResult = st.executeQuery(req);
            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                   methoadmin();
                } else {
                    wronginfo.setText("invalide login ");
                }
            }
            } catch(SQLException ex){
                Logger.getLogger(Park.class.getName()).log(Level.SEVERE, null, ex);

            }
        }

     public void methoadmin( ) {
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull((getClass()).getResource("hello-view.fxml")));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root);
            stage.setScene( scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }


    //on clique sur mdp oublié puis l'affichage de  l'interface mdp-login


   //vider les champs de login et mdp
    @FXML
    void annulerChamps(ActionEvent event) {
        loginTfd.setText("");
        mdpTfd.setText("");
    }

    @FXML
    void sortir(ActionEvent event) {
        Platform.exit();
    }


    //affichier le mot de passe
    @FXML
    void changeVisibilite(ActionEvent event) {
       if (checkBox.isSelected()){
           passwordText.setText(mdpTfd.getText());
           passwordText.setVisible(true);
           mdpTfd.setVisible(false);
           return;
       }
       mdpTfd.setText(passwordText.getText());
       mdpTfd.setVisible(true);
       passwordText.setVisible(false);
    }
    @FXML
    void sinscrire(ActionEvent event) throws IOException {

        root = FXMLLoader.load((getClass()).getResource("inscrire.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void MotDePasse(ActionEvent event) throws IOException {
        root = FXMLLoader.load((getClass()).getResource("mdp-login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
