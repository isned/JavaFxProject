package com.example.isned;

import com.example.isned.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class NvClientController implements Initializable {

    @FXML
    private Label txtAjouterClient;
    @FXML
    private Button btnEnrregistrer;

    @FXML
    private Label lblAlert;

    @FXML
    private TextField txtAdresse;

    @FXML
    private TextField txtCin;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrenom;

    @FXML
    void Ajouter(ActionEvent event) {
        if(isEmpty()){
            lblAlert.setText("Il faut remplir les champs");
            lblAlert.setVisible(true);
        }else{
            String cin=txtCin.getText().trim();
            String nom=txtNom.getText().trim();
            String prenom=txtPrenom.getText().trim();
            String adresse=txtAdresse.getText().trim();
            if(new Client(cin,nom,prenom,adresse).add()){
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.close();
            }
        }

    }

     //Initializes the controller class.

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //mazallll
    }
    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    private boolean isEmpty(){
        return txtCin.getText().trim().isEmpty() && txtNom.getText().trim().isEmpty()
                && txtPrenom.getText().trim().isEmpty() && txtAdresse.getText().trim().isEmpty();
    }
}
