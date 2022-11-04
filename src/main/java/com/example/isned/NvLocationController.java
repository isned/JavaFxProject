package com.example.isned;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class NvLocationController implements Initializable {
    @FXML
    private ButtonBar barBtn;

    @FXML
    private Button btnAddClient;

    @FXML
    private DatePicker DateDeb;

    @FXML
    private DatePicker DateFin;
    @FXML
    private Label txtVehicule;
    @FXML
    private Label txtConfirmerLocation;

    @FXML
    private Label txtClient;
    ArrayList<Vehicule> listVehicules;

    ObservableList<Client> clients = FXCollections.observableArrayList();

    @FXML
    private Button btnAjouter;

    @FXML
    private AnchorPane formAnchorPane;

    @FXML
    private Label lblAlert;

    @FXML
    private TableView<Client> tvClient;

    @FXML
    private TableView<Vehicule> tvVehicule;

    @FXML
    private TextField txtDateDeb;

    @FXML
    private TextField txtDateFin;

    @FXML
    private TextField txtPrixAvance;

    @FXML
    private TextField txtPrixTotal;

    @FXML
    private Button actualiserbtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            listVehicules =new Park().getListVehicule();

            SetVehicules();
            SetClients();
        } catch (SQLException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tvClient.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            try {
                tvClient.getItems().clear();
                SetClients();
            } catch (SQLException ex) {
                Logger.getLogger(NvLocationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    void ajoutLocation(ActionEvent event) {
        if(!btnAjouter.getText().equals("confirmer"))
            if(!test()){//test tableView non selectionné
                lblAlert.setVisible(true);
                lblAlert.setText("Il faut selectionner un client et une vehicule");


            }
            else{//passage au formulaire 2
                lblAlert.setVisible(false);
                btnAjouter.setText("confirmer");
                btnAddClient.setText("Retour");
                formAnchorPane.setPrefSize(226,264);
                formAnchorPane.setVisible(true);
                tvClient.setVisible(false);
                tvVehicule.setVisible(false);
                txtClient.setVisible(false);
                txtVehicule.setVisible(false);

            }
        else{
            if(!isEmpty()){//test les champs vides
                Client c = tvClient.getSelectionModel().selectedItemProperty().get();
                Vehicule v = tvVehicule.getSelectionModel().selectedItemProperty().get();
                //String dateDeb = txtDateDeb.getText().trim();
                //String dateFin = txtDateFin.getText().trim();
                LocalDate startdate=DateDeb.getValue();
                LocalDate endDate=DateFin.getValue();
                String varstartdate=startdate.toString();
                String varenddate=endDate.toString();

                int prixTotal=0;
                int prixAvance=0;
                try{
                    prixTotal = Integer.parseInt(txtPrixTotal.getText().trim());
                    prixAvance = Integer.parseInt(txtPrixAvance.getText().trim());
                }catch(NumberFormatException e){
                    lblAlert.setText("inserer un entier");
                    lblAlert.setVisible(true);
                }
                if (new Location(v, c, varstartdate, varenddate, prixTotal, prixAvance).addLocation()) {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                    v.modifierVehicule(Vehicule.ETAT_LOUE);
                } else {
                    return;
                }
            }
            else{
                lblAlert.setText("vous devez remplir les champs");
                lblAlert.setVisible(true);
            }
        }
    }

    @FXML
    void addClient(ActionEvent event) {
        if(btnAddClient.getText().equals("Retour")){
            lblAlert.setVisible(false);
            btnAjouter.setText("Suivant");
            btnAddClient.setText("Nouveau Client");
            //formAnchorPane.setPrefSize(226,264);
            formAnchorPane.setVisible(false);
            tvClient.setVisible(true);
            tvVehicule.setVisible(true);
            txtVehicule.setVisible(true);
            txtClient.setVisible(true);
            txtConfirmerLocation.setVisible(true);

        }else
            try {
                FXMLLoader fxmlloader = new FXMLLoader();
                fxmlloader.setLocation(HelloApplication.class.getResource("NvClient.fxml"));
                AnchorPane root =  fxmlloader.load();
                Stage stage = new Stage();
                stage.setTitle("Ajouter un client");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(Arrays.toString(ex.getStackTrace()));
            }
    }
    private boolean test(){
        return tvVehicule.getSelectionModel().selectedItemProperty().get()!=null &&
                tvClient.getSelectionModel().selectedItemProperty().get()!=null;
    }



    private void SetVehicules() throws SQLException {
        tvVehicule.getItems().clear();
        ObservableList<Vehicule> vehicules = FXCollections.observableArrayList();

        TableColumn immatCol = new TableColumn<Vehicule, Object>("Immatricule");
        immatCol.setCellValueFactory(new PropertyValueFactory<Vehicule, Object>("immatricule"));
        TableColumn constuctCom = new TableColumn("Constructeur");
        constuctCom.setCellValueFactory(new PropertyValueFactory("constructeur"));
        TableColumn marqueCol = new TableColumn("Marque");
        marqueCol.setCellValueFactory(new PropertyValueFactory("marque"));
        TableColumn etatCol = new TableColumn("Etat du véhicule");
        etatCol.setCellValueFactory(new PropertyValueFactory("etat"));

        tvVehicule.getColumns().setAll(immatCol, constuctCom , marqueCol , etatCol);

        listVehicules.forEach((vehicule) -> {
            vehicules.add(vehicule);
        });

        tvVehicule.setItems(vehicules);

    }


    private void SetClients() throws SQLException {
        tvClient.getItems().clear();
        clients.clear();
        TableColumn cinCol = new TableColumn<Client, Object>("CIN");
        cinCol.setCellValueFactory(new PropertyValueFactory<Client, Object>("cin"));
        TableColumn nomCom = new TableColumn("Nom");
        nomCom.setCellValueFactory(new PropertyValueFactory("nom"));
        TableColumn prenomCol = new TableColumn("Prénom");
        prenomCol.setCellValueFactory(new PropertyValueFactory("prenom"));
        TableColumn adresseCol = new TableColumn("Adresse");
        adresseCol.setCellValueFactory(new PropertyValueFactory("adresse"));

        tvClient.getColumns().setAll(cinCol, nomCom , prenomCol , adresseCol);

        ArrayList<Client> listclients = Client.getAll();

        clients.addAll(listclients);

        tvClient.setItems(clients);
    }

    public  void refresh(ActionEvent event) throws SQLException {
        tvClient.getItems().clear();
        clients.clear();
        TableColumn cinCol = new TableColumn<Client, Object>("CIN");
        cinCol.setCellValueFactory(new PropertyValueFactory<Client, Object>("cin"));
        TableColumn nomCom = new TableColumn("Nom");
        nomCom.setCellValueFactory(new PropertyValueFactory("nom"));
        TableColumn prenomCol = new TableColumn("Prénom");
        prenomCol.setCellValueFactory(new PropertyValueFactory("prenom"));
        TableColumn adresseCol = new TableColumn("Adresse");
        adresseCol.setCellValueFactory(new PropertyValueFactory("adresse"));

        tvClient.getColumns().setAll(cinCol, nomCom , prenomCol , adresseCol);

        ArrayList<Client> listclients = Client.getAll();

        clients.addAll(listclients);

        tvClient.setItems(clients);
    }


    private boolean isEmpty(){
        return DateDeb.getValue().equals("") || DateFin.getValue().equals("")
                || txtPrixAvance.getText().trim().isEmpty() || txtPrixTotal.getText().trim().isEmpty();
    }
    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
