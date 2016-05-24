
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.FrontOffice_Boutiq;

//~--- non-JDK imports --------------------------------------------------------
import sn.ceessay.samaBoutiq.Classes.Article;
import sn.ceessay.samaBoutiq.Classes.Carateristiques;
import sn.ceessay.samaBoutiq.Classes.Client;
import sn.ceessay.samaBoutiq.Classes.Commande;
import sn.ceessay.samaBoutiq.Classes.DBConnect;

//~--- JDK imports ------------------------------------------------------------
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ceessay cette classe constitue le modele de donnees de l'application.
 * tous les produit y sont chargé sous form d'arraylist et par categorie; on y
 * retrouve auusi l'instance de la classe client representant le client et un
 * Panier qui est une instance de Commande;
 *
 * toutes les operation sont effectuées su les données figurant dans cette
 * classe
 */
public class Model {

    public static Client CLIENT;
    public static Commande PANIER ;//= new Commande();
    public static ArrayList<Article> tousLesArticleList;
    public static ArrayList<Article> ordiList;
    public static ArrayList<Article> tabletList;
    public static ArrayList<Article> smartList;
    public static ArrayList<Article> phablettList;
    public static ArrayList<Article> resultatRechercheList;

    public static void init() {
        CLIENT = new Client();
        PANIER = new Commande();
        tousLesArticleList = new ArrayList<>();

        // chargement des articles
        try {
            String req = "SELECT * FROM Articles";
            ResultSet rs = DBConnect.SELECT(req);
            Article a;

            while (rs.next()) {
                a = new Article(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getInt(8), rs.getInt(9), rs.getInt(10),
                        rs.getBlob(11));
                tousLesArticleList.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BoutiqMain.class.getName()).log(Level.SEVERE, null, ex);
        }

//      chargement des caracteristiques
        for (Article arti : tousLesArticleList) {
            Carateristiques c = DBConnect.getCaracteristiques(arti.getIdArticle());

            arti.setCarArticle(c);
        }

        ordiList = new ArrayList<>();
        smartList = new ArrayList<>();
        tabletList = new ArrayList<>();
        phablettList = new ArrayList<>();

        for (Article article : tousLesArticleList) {
            if (article.getCategorie().equalsIgnoreCase("ordinateur portable")) {
                ordiList.add(article);
            } else if (article.getCategorie().equalsIgnoreCase("ordinateur de bureau")) {
                ordiList.add(article);
            } else if (article.getCategorie().equalsIgnoreCase("smartphone")) {
                smartList.add(article);
            } else if (article.getCategorie().equalsIgnoreCase("tablette")) {
                tabletList.add(article);
            } else if (article.getCategorie().equalsIgnoreCase("phablette")) {
                phablettList.add(article);
            }
        }

        resultatRechercheList = new ArrayList<>();
    }
}
