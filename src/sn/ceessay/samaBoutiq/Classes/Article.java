
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.Classes;

//~--- JDK imports ------------------------------------------------------------

//import java.awt.BorderLayout;
import java.sql.Blob;

//import javax.swing.BoxLayout;
//import javax.swing.JLabel;
//import javax.swing.JPanel;

/**
 *
 * @author ceessay
 */
public class Article implements Comparable<Article> {
    private Integer         idArticle;
    private String          designation;
    private Integer         prix;
    private String          marque;
    private String          gamme;
    private String          description;
    private String          categorie;
    private Integer         quatiteDispo;
    private Integer         quatiteajoutee;
    private Integer         quantitevendue;
    private final Blob      img;
    private Carateristiques CarArticle;

    public Article(Integer idArticle, String designation, Integer prix, String marque, String gamme,
                   String description, String categorie, Integer quatiteDispo, Integer quatiteajoutee,
                   Integer quantitevendue, Blob img) {
        this.idArticle      = idArticle;
        this.designation    = designation;
        this.prix           = prix;
        this.marque         = marque;
        this.gamme          = gamme;
        this.description    = description;
        this.categorie      = categorie;
        this.quatiteDispo   = quatiteDispo;
        this.quatiteajoutee = quatiteajoutee;
        this.quantitevendue = quantitevendue;
        this.img            = img;
    }

    public Integer getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Integer idArticle) {
        this.idArticle = idArticle;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public Integer getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public String getGamme() {
        return gamme;
    }

    public void setGamme(String gamme) {
        this.gamme = gamme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Integer getQuatiteDispo() {
        return quatiteDispo;
    }

    public void setQuatiteDispo(Integer quatiteDispo) {
        this.quatiteDispo = quatiteDispo;
    }

    public Integer getQuantitevendue() {
        return quantitevendue;
    }

    public void setQuantitevendue(Integer quantitevendue) {
        this.quantitevendue = quantitevendue;
    }

    public Integer getQuatiteajoutee() {
        return quatiteajoutee;
    }

    public void setQuatiteajoutee(Integer quatiteajoutee) {
        this.quatiteajoutee = quatiteajoutee;
    }

    public Carateristiques getCarArticle() {
        return CarArticle;
    }

    public Blob getImageBlob() {
        return img;
    }

    public void setCarArticle(Carateristiques c) {
        this.CarArticle = c;
    }

//  public ArticlePanel getPanel() {
//      panel = new ArticlePanel(this);
//      
//      
//      return panel;
//  }
    @Override
    public int compareTo(Article o) {
        if (this.idArticle != o.getIdArticle()) {
            return 1;
        } else {
            return 0;
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
