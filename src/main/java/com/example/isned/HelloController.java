package com.example.isned;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.example.isned.Location.getLocationList;


public class HelloController implements Initializable {

    @FXML
    private Label txtAjoutVehicule;
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
    @FXML
    private Button buttonSortir;
    @FXML
    private Button boutonDeconncter;
    @FXML
    private Button btnConsultByClient;

    @FXML
    private Button btnConsultLocation;

    @FXML
    private Button btnFinLocation;

    @FXML
    private Button btnSuppVehicule;
    @FXML
    private Button btnNvAcquisition;
    @FXML
    private Button btnModifVehicule;

    @FXML
    private Button btnNewLocation;

    @FXML
    private ComboBox<String> cBoxClients;

    @FXML
    private TableView<Vehicule> tvConsult;
    @FXML
    private TableView<Location> tvConsultLocation;
    Park park=new Park();
    ArrayList<Location> locationList;


    @FXML
    private AnchorPane anchorePaneVehicule;


    //ajout d'une nouvelle aquisition
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
                //Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                //stage.close();

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

    //vider les champs pour ajouter nouvelle aquisition
    @FXML
    void close(ActionEvent event) {
        txtConstructeur.setText("");
        txtImmat.setText("");
        txtMarque.setText("");
    }
    //tester si les champs sont vides au non au niveau d'ajout une nouvelle aquisition
    private boolean isEmpty(){
        return txtConstructeur.getText().trim().isEmpty() || txtImmat.getText().trim().isEmpty()
                || txtMarque.getText().trim().isEmpty();
    }

    @FXML
    void AjoutLocation(ActionEvent event) {
        try {
            FXMLLoader fxmlloader = new FXMLLoader();
            fxmlloader.setLocation(HelloApplication.class.getResource("NvLocation.fxml"));
            AnchorPane root = fxmlloader.<AnchorPane>load();
            Stage stage = new Stage();
            stage.setTitle("Ajouter une location");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }

    }

    @FXML
    void ajouterVehicule(ActionEvent event) throws SQLException {
       anchorePaneVehicule.setVisible(true);
       tvConsult.setVisible(false);

    }

    @FXML
    void ConsultationPark(ActionEvent event) throws SQLException {
        tvConsult.setVisible(true);
        tvConsult.setEditable(true);
        SetVehicules();
        System.out.println(tvConsult.getItems().toString());
        anchorePaneVehicule.setVisible(false);
    }

    @FXML
    void modifierEtat(ActionEvent event) throws SQLException {
        tvConsult.getSelectionModel().selectedItemProperty().get().modifierVehicule(Vehicule.ETAT_EN_COURS);
        repaintVehiculesTable();
    }


    @FXML
    void supprimerVehicule(ActionEvent event) throws SQLException {
        Vehicule v = (Vehicule) tvConsult.getSelectionModel().selectedItemProperty().get();
        v.supprimerVehicule();
        park.getListVehicule().remove(v);
        repaintVehiculesTable();
    }
    private void repaintLocationTable(){
        tvConsultLocation.getItems().clear();
        try {
            setLocations(getLocationList());
        } catch (SQLException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    @FXML
    void finLocation(ActionEvent event)  {
        Location location = tvConsultLocation.getSelectionModel().selectedItemProperty().get();
        location.deleteLocation();
        location.getV().modifierVehicule(Vehicule.ETAT_DISPONIBLE);
        repaintLocationTable();
        repaintVehiculesTable();
    }

    @FXML
    void getVehiculeLoue(ActionEvent event) {
        tvConsultLocation.setVisible(true);
        tvConsultLocation.setEditable(true);
        try {
            setLocations(Location.getLocationList());
        } catch (SQLException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvConsult.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnModifVehicule.setDisable(false);
            btnSuppVehicule.setDisable(false);
        });
        tvConsultLocation.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnFinLocation.setDisable(false);
        });

        cBoxClients.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            tvConsultLocation.getItems().clear();
            ArrayList<Location> locationList;
            try {
                locationList = Location.getLocationList();
                ArrayList<Location> newList=new ArrayList<>();
                for(Location l : locationList)
                    if(l.contain(newValue))
                        newList.add(l);
                setLocations(newList);

            } catch (SQLException ex) {
                Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
            }


        });
    }
    private void SetVehicules() throws SQLException {
        ObservableList<Vehicule> vehicules = FXCollections.observableArrayList();

        TableColumn immatCol = new TableColumn("Immatricule");
        immatCol.setCellValueFactory(new PropertyValueFactory("immatricule"));
        TableColumn constuctCom = new TableColumn("Constructeur");
        constuctCom.setCellValueFactory(new PropertyValueFactory("constructeur"));
        TableColumn marqueCol = new TableColumn("Marque");
        marqueCol.setCellValueFactory(new PropertyValueFactory("marque"));
        TableColumn etatCol = new TableColumn("Etat du véhicule");
        etatCol.setCellValueFactory(new PropertyValueFactory("etat"));

        tvConsult.getColumns().setAll(immatCol, constuctCom , marqueCol , etatCol);
        ArrayList<Vehicule> list= park.getListVehicule();

        list.forEach((vehicule) -> {
            vehicules.add(vehicule);
        });

        tvConsult.setItems(vehicules);
    }

    private void setLocations(ArrayList<Location> locationList) throws SQLException {
        ObservableList<Location> locations = FXCollections.observableArrayList();

        TableColumn immatCol = new TableColumn("Immatricule");
        immatCol.setCellValueFactory(new PropertyValueFactory("v"));
        TableColumn clientCol = new TableColumn("Client");
        clientCol.setCellValueFactory(new PropertyValueFactory("c"));
        TableColumn dateDebCol = new TableColumn("Date Debut");
        dateDebCol.setCellValueFactory(new PropertyValueFactory("datedeb"));
        TableColumn dateFinCol = new TableColumn("Date Fin");
        dateFinCol.setCellValueFactory(new PropertyValueFactory("datefin"));
        TableColumn prixTotalCol = new TableColumn("Prix Total");
        prixTotalCol.setCellValueFactory(new PropertyValueFactory("prixTotal"));
        TableColumn prixAvanceCol = new TableColumn("Prix Avance");
        prixAvanceCol.setCellValueFactory(new PropertyValueFactory("prixAvance"));


        tvConsultLocation.getColumns().setAll(immatCol, clientCol , dateDebCol , dateFinCol , prixTotalCol , prixAvanceCol);
        locationList= Location.getAll();

        locationList.forEach((location) -> {
            locations.add(location);
            System.out.println(location);
        });

        tvConsultLocation.setItems(locations);
    }



    @FXML
    void getVehiculeLoueByClient(ActionEvent event) {
        tvConsultLocation.setVisible(true);
        tvConsultLocation.setEditable(true);
        cBoxClients.setVisible(true);
        ArrayList<Client> listClient = null;
        try {
            listClient = Client.getAll();
        } catch (SQLException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<String> list = new ArrayList<>();
        for(Client cin : listClient )
            list.add(cin.toString());
        cBoxClients.getItems().setAll(list);
        
    }



    private void repaintVehiculesTable()  {
        tvConsult.getItems().clear();
        try {
            SetVehicules();
        } catch (SQLException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void deconnecter(ActionEvent event) throws IOException {
        Scene scene;
        Stage stage;
        Parent root ;
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