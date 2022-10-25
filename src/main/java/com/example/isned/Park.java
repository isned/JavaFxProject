package com.example.isned;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
public final class Park {
    private static ArrayList<Vehicule> listeVehicule;
    DatabaseConnection db;

    public Park() {
    }

    public ArrayList<Vehicule> getListVehicule() throws SQLException{
        if(listeVehicule==null)
            listeVehicule = afficher();
        return listeVehicule;
    }

    private ArrayList<Vehicule> afficher() throws SQLException{
        ArrayList<Vehicule> vList = new ArrayList<>();
        db = DatabaseConnection.getInstance();
        try {
            Connection con=db.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from vehicule");

            while(rs.next()){
                Vehicule v = new Vehicule(rs.getInt("immatricule"), rs.getString("constructeur") , rs.getString("marque"), rs.getString("etat"));

                System.out.println(v);
                vList.add(v);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Park.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vList;
    }
    public Vehicule getVehByImmat(int immat){
        for(Vehicule v: listeVehicule) {
            if(v.contain(immat))
                return v;
        }
        return null;
    }
}
