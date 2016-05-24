
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.FrontOffice_Boutiq;

//~--- non-JDK imports --------------------------------------------------------

import com.alee.extended.layout.GroupLayout;
import com.alee.extended.layout.WrapFlowLayout;
import com.alee.extended.panel.GridPanel;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.StyleConstants;
import com.alee.laf.panel.WebPanel;

import sn.ceessay.samaBoutiq.Classes.Article;
import sn.ceessay.samaBoutiq.Classes.Constantes;

//~--- JDK imports ------------------------------------------------------------

import java.awt.CardLayout;
import java.awt.GridLayout;

import java.util.ArrayList;
//import javafx.scene.layout.GridPane;

import javax.swing.JPanel;

/**
 *
 * @author ceessay
 * 
 * 
 * Cette classe represente la vitrine c'est a dire la partie de l'application où sont affichée les articles
 * Elle utilise un cardLayout de cinq panels . 
 * Une correspond a  catégorie d'article et qui sont
 * appelés a chaque fois q'il y'a un clique sur le bouton correspondant
 * et une pour les resultats des recherches utilisateur qui est affiché lorsque l'utilisateur 
 * effectue une recherche.
 * 
 * L'appelle(L'affichage) d'un des panel se fait a l'aide d'un mot clé
 * 
 */
public class VitrinePanel extends WebPanel {
    // le layout est un cardLayout
    private static CardLayout cardLayout;
    
    // Le cardlayut est composé de cinq panels
    private JPanel            tousLesproduitsPanel, smartPanel, ordiPanel, tabletPanel, phabletPanel,
                              resultatRecherchePanel;

    public VitrinePanel(String cle) {
        
        int n = Model.tousLesArticleList.size();
        int m = n/4;
         GridLayout layout = new GridLayout(4, 4, 10,10);
        //WrapFlowLayout layout = new WrapFlowLayout(10, 10);
        
        
        tousLesproduitsPanel = new JPanel(new GridLayout(n/4+1, 4, 10, 10));
       
        
        

        // tousLesproduitsPanel.setPreferredSize(new Dimension(1000, 1));
        smartPanel             = new JPanel(new WrapFlowLayout(10, 10));
        ordiPanel              = new JPanel(new WrapFlowLayout(10, 10));
        tabletPanel            = new JPanel(new WrapFlowLayout(10, 10));
        phabletPanel           = new JPanel(new WrapFlowLayout(10, 10));
        resultatRecherchePanel = new JPanel(new WrapFlowLayout(10, 10));

        // setPreferredSize(new Dimension(1000, 600));

        // setBackground(Constants.COULEUR_DE_FOND);
        
        setRound(StyleConstants.largeRound);
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setTousLesproduitsPanel();
        setOrdiPanel();
        setSmartPanel();
        setTabletPanel();
        setPhabletPanel();
        
        //ajout des produits au cardlayout avec association de chacun d'eux avec un mot-clé
        add(tousLesproduitsPanel, "tout");
        add(ordiPanel, "ordinateurs");
        add(smartPanel, "smartphones");
        add(tabletPanel, "tablettes");
        add(phabletPanel, "phablettes");
        add(resultatRecherchePanel, "resultatRecherche");

        if (cle.equals("resultatRecherche")) {
            setResultatRecherchePanel();
        }

        cardLayout.show(this, cle);
    }

    private void ajouter(JPanel j, ArrayList<Article> articles) {
        ArticlePanel a;

        for (Article article : articles) {
            a = new ArticlePanel(article);
            j.add(a);
        }
    }

    private void setTousLesproduitsPanel() {
        tousLesproduitsPanel.setBackground(Constantes.COULEUR_DE_FOND);
        ajouter(tousLesproduitsPanel, Model.tousLesArticleList);
    }

    private void setSmartPanel() {
        smartPanel.setBackground(Constantes.COULEUR_DE_FOND);
        ajouter(smartPanel, Model.smartList);
    }

    private void setOrdiPanel() {
        ordiPanel.setBackground(Constantes.COULEUR_DE_FOND);
        ajouter(ordiPanel, Model.ordiList);
    }

    private void setTabletPanel() {
        tabletPanel.setBackground(Constantes.COULEUR_DE_FOND);
        ajouter(tabletPanel, Model.tabletList);
    }

    private void setPhabletPanel() {
        phabletPanel.setBackground(Constantes.COULEUR_DE_FOND);
        ajouter(phabletPanel, Model.phablettList);
    }

    public void setResultatRecherchePanel() {
        resultatRecherchePanel.setBackground(Constantes.COULEUR_DE_FOND);
        ajouter(resultatRecherchePanel, Model.resultatRechercheList);
    }
}

