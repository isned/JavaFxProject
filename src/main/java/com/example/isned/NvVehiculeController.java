 package com.example.isned;

import com.example.isned.Vehicule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

public class NvVehiculeController implements Initializable {
    @FXML
    private Button btnEnrregistrer;

    @FXML
    private Label lblAlert;

    @FXML
    private TextField txtConstructeur;

    @FXML
    private TextField txtImmat;

    @FXML
    private TextField txtMarque;

    Vehicule vehicule;
    @FXML
    void Enregistrer(ActionEvent event) {
        int immat;
        String constructeur;
        String marque;

        if(!isEmpty()){
            immat = Integer.parseInt(txtImmat.getText().trim());
            constructeur = txtConstructeur.getText().trim();
            marque = txtMarque.getText().trim();
            Vehicule vehicule = new Vehicule(immat, constructeur, marque);
            if(vehicule.AjouterVehicule()){
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.close();
            }
            else{
                lblAlert.setText("erreur de connexion");
                lblAlert.setVisible(true);
            }
        }
        else{
            lblAlert.setText("Il faut remplir les champs");
            lblAlert.setVisible(true);
        }
    }

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    private boolean isEmpty(){
        return txtConstructeur.getText().trim().isEmpty() || txtImmat.getText().trim().isEmpty()
                || txtMarque.getText().trim().isEmpty();
    }
    //Initializes the controller class.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //mazal
    }

}
