
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.Classes;

/**
 *
 * @author ceessay
 */
public class LigneDeCommande {
    private Article article;
    private Integer nombreUnites;
    private Integer prixUnitaire;
    private Integer valeur;

    public LigneDeCommande(Article article) {
        this.article      = article;
        this.prixUnitaire = article.getPrix();
        this.nombreUnites = 1;
        setValeur();
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Integer getNombreUnites() {
        return nombreUnites;
    }

    public void setNombreUnites(Integer nombreUnites) {
        this.nombreUnites = nombreUnites;
        setValeur();
    }

    public Integer getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Integer prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Integer getValeur() {
        return this.valeur;
    }

    public void setValeur() {
        this.valeur = prixUnitaire * nombreUnites;
    }

    public void afficherLigneDeCommande() {
        System.out.println(article.getDesignation() + "\t" + this.prixUnitaire + "\t" + this.nombreUnites + "\t"
                           + this.valeur);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
