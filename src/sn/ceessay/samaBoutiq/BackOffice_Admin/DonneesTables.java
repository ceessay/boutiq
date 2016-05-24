/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.BackOffice_Admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import sn.ceessay.samaBoutiq.Classes.DBConnect;

/**
 *
 * @author ceessay
 */

/*
cette classe contient des tablemodel, modeles des données affichées dans les jtables.
elle permet de recuperere les données necessaire au niveau de la base de données et
de les mettre en forme pour un eventuel affichage dans un jtable

*/
public class DonneesTables {

    public static TableModel getDonneesArticles() {
        
        // recuperation des données
        ResultSet rs = DBConnect.SELECT("Select * from Articles");

        // préparation des entetes
        String[] entetes = new String[]{"Identifiant", "Désignation", "Prix", "Marque",
            "Gamme", "Déscription", "Categorie", "Quantité disponible", /*"Quantité ajoutée",*/ "Quantité vendue"};
      
        // creation du table model a partir d'un model vided par defaut
        DefaultTableModel model = new DefaultTableModel(entetes, 0);
        
        // recuperation du resultset ligne par ligne dans un vecteur (1) 
        // selection des colonnes (parmis les dix que contient la table) desirée qui sont ajoutées au vecteur(2).
        //et ajout du vecteur au model(3)
        
        try {
            while (rs.next()) {//1
                Vector row = new Vector();

                for (int i = 1; i <= 10; i++) {
                    
                    //on ignore un colonne dont on a pas besoin
                    if(i==9)
                        continue;
                    
                    row.addElement(rs.getObject(i));//2
                    
                }

                model.addRow(row);//3
            }
        } catch (SQLException ex) {
            Logger.getLogger(DonneesTables.class.getName()).log(Level.SEVERE, null, ex);
        } 

        return model;
    }

    public static TableModel getDonneesCmd() {

        String req = "SELECT Commandes.idCmd, Commandes.dateCmd,Commandes.livree, "
                + "Clients.prenom, Clients.nom FROM Commandes, Clients "
                + "WHERE Commandes.idClient = Clients.idClient  ORDER BY Commandes.dateCmd DESC";

        ResultSet resultSet = DBConnect.SELECT(req);

        String[] entetes = new String[]{"N° commande", "Date", "Livraison", "Prénom du client", "Nom du client"};
        DefaultTableModel model = new DefaultTableModel(entetes, 0);

        try {
            while (resultSet.next()) {
                Vector row = new Vector();

                for (int i = 1; i <= 5; i++) {
                    row.addElement(resultSet.getObject(i));
                }

                if ((boolean) row.get(2) == true) {
                    row.remove(2);
                    row.add(2, "Livrée");
                } else {
                    row.remove(2);
                    row.add(2, "Non livrée");
                }
                row.trimToSize();
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DonneesTables.class.getName()).log(Level.SEVERE, null, ex);
        } 
//          if (i == 3) {
//                if (resultSet.getBoolean(i) == true) {
//                    row.addElement(new String("Livré"));
//                    
//                } else {
//                    row.addElement(new String("Non livré"));
//                   
//                }
//            }
        
        
        return model;

    }

    public static TableModel getDonneesClients() {
        
        String req = "SELECT idClient,nom, prenom, sexe, age, telephone, email FROM Clients ORDER BY nom";

        ResultSet resultSet = DBConnect.SELECT(req);

        String[] entetes = new String[]{"N° Client", "Nom", "Prénom", "Sexe", "Age", "Téléphone", "Email"};
        DefaultTableModel model = new DefaultTableModel(entetes, 0);

        try {
            while (resultSet.next()) {
                Vector row = new Vector();

                for (int i = 1; i <= 7; i++) {
                    row.addElement(resultSet.getObject(i));
                }
                row.trimToSize();
                model.addRow(row);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DonneesTables.class.getName()).log(Level.SEVERE, null, ex);
        } 
         
        return model;

    }
    
    
    
    

}
