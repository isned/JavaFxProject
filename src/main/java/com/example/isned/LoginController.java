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
import java.io.IOException;
import java.util.Objects;

public class LoginController {
    private Stage stage;
    private Scene scene;
    private Parent root;


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



    public void login(ActionEvent event) throws IOException {
        if(loginTfd.getText().toString().equals("admin") && mdpTfd.getText().toString().equals("admin")){
            root = FXMLLoader.load(Objects.requireNonNull((getClass()).getResource("hello-view.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if (loginTfd.getText().isEmpty() && mdpTfd.getText().isEmpty()) {
            wronginfo.setText("svp donner vos infos");
        }
        else {
            wronginfo.setText("login ou mdp incorrecte");
        }
    }



   @FXML
   void MotDePasse(ActionEvent event) throws IOException {

       root = FXMLLoader.load((getClass()).getResource("mdp-login.fxml"));
       stage = (Stage)((Node)event.getSource()).getScene().getWindow();
       scene = new Scene(root);
       stage.setScene(scene);
       stage.show();
   }
    @FXML
    void annulerChamps(ActionEvent event) {
        loginTfd.setText("");
        mdpTfd.setText("");
    }

    @FXML
    void sortir(ActionEvent event) {
        Platform.exit();
    }
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

}
