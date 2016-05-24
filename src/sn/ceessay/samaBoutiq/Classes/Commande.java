
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.Classes;

//~--- non-JDK imports --------------------------------------------------------
import sn.ceessay.samaBoutiq.FrontOffice_Boutiq.Model;
import sn.ceessay.samaBoutiq.FrontOffice_Boutiq.Notificateur;

//~--- JDK imports ------------------------------------------------------------
import java.sql.Date;

import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 *
 * @author ceessay
 */
public class Commande {

    private ArrayList<LigneDeCommande> listeLignesDeCommande ;//= new ArrayList<>();
    private Integer idClient;
    private java.sql.Date dateCmd;
    private Integer nbreArticles;
    private Integer valeurCmd;

    public Commande() {
        listeLignesDeCommande = new ArrayList<>();
        valeurCmd = 0;
        nbreArticles = 0;
        idClient = Model.CLIENT.getIdClient();
    }

    public Integer getNbreArticles() {
        return nbreArticles;
    }

    public void setNbreArticles() {
        Integer val = 0;

        for (LigneDeCommande ldc : listeLignesDeCommande) {
            val += ldc.getNombreUnites();
        }

        this.nbreArticles = val;
    }

    public Integer getValeurCmd() {
        return valeurCmd;
    }

    public void setValeurCmd() {
        Integer val = 0;

        for (LigneDeCommande ldc : listeLignesDeCommande) {
            val += ldc.getValeur();
        }

        this.valeurCmd = val;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public Date getDateCmd() {
        return dateCmd;
    }

    public void setDateCmd() {
        java.util.Date aujourdhui = new java.util.Date();
        this.dateCmd = new java.sql.Date(aujourdhui.getTime());

        java.util.Date a = new java.util.Date(this.dateCmd.getTime());

    }

    public boolean ajouterLigneDeCommande(LigneDeCommande ldc) {
        for (LigneDeCommande l : listeLignesDeCommande) {
            if (0 == ldc.getArticle().compareTo(l.getArticle())) {
                JOptionPane.showMessageDialog(null, "Cet article figure déja dans votre panier");

                return false;
            }
        }

        listeLignesDeCommande.add(ldc);
        setNbreArticles();
        setValeurCmd();
        return true;
    }

    public void retirerLigneDeCommande(LigneDeCommande ldc) {
        listeLignesDeCommande.remove(ldc);
        setValeurCmd();
    }

    public void afficherCommande() {
        System.out.println("Contenu de votre Panier");

        for (LigneDeCommande l : listeLignesDeCommande) {
            l.afficherLigneDeCommande();
        }

        System.out.println("\t\tTotal: " + this.valeurCmd);
    }

    public ArrayList<LigneDeCommande> getListeLignesDeCommande() {
        return listeLignesDeCommande;
    }

    public boolean enleverArticle(Article article) {
        for (LigneDeCommande ldc : listeLignesDeCommande) {
            if (0 == ldc.getArticle().compareTo(article)) {
                listeLignesDeCommande.remove(article);
                setValeurCmd();

                return true;
            }
        }

        return false;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
