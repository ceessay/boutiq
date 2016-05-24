
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.BackOffice_Admin;

//~--- non-JDK imports ------------------
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.text.WebTextField;

import sn.ceessay.samaBoutiq.FrontOffice_Boutiq.ChampsFocusListener;
import sn.ceessay.samaBoutiq.Classes.Constantes;

//~--- JDK imports ------------------------------------------------------------
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author ceessay
 */

/*
    panel de saise et d'affichage des information d'un article
*/
public class InfosArticlesPanel extends JPanel {

    private static ArrayList<String> valeursChamps;
    WebButton ajouterButton = new WebButton("Ajouter");
    private int idarticle = 0;
    String[] categories = new String[]{
        null, "Ordinateur de bureau", "Ordinateur Portable", "Smartphone", "Tablette", "Phablette"
    };
    
    private WebTextField desigationField, prixField, marqueField, gammeField, descripField, quantiteField;
    private WebComboBox categorieBox;

    public InfosArticlesPanel() {
        buildGUI();
    }

    public int getIdarticle() {
        return idarticle;
    }

    public void setIdarticle(int idarticle) {
        this.idarticle = idarticle;
    }

    public void buildGUI() {
        valeursChamps = new ArrayList<>();
        

        JLabel designationLabel = new JLabel("Désignation");

        desigationField = new WebTextField();
        desigationField.setColumns(20);
        desigationField.setInputPrompt("Désignation de l'article");
        desigationField.setHideInputPromptOnFocus(false);

        JLabel prixLabel = new JLabel("Prix");

        prixField = new WebTextField();
        prixField.setInputPrompt("Prix de l'article");
        prixField.setHideInputPromptOnFocus(false);

        JLabel marqueLabel = new JLabel("Marque");

        marqueField = new WebTextField();
        marqueField.setInputPrompt("Marque de l'article");
        marqueField.setHideInputPromptOnFocus(false);

        JLabel gammeLabel = new JLabel("Gamme");

        gammeField = new WebTextField();
        gammeField.setInputPrompt("Gamme de l'article");
        gammeField.setHideInputPromptOnFocus(false);

        JLabel descriptLabel = new JLabel("Description");

        descripField = new WebTextField();
        descripField.setColumns(20);
        descripField.setInputPrompt("Description de l'article");
        descripField.setHideInputPromptOnFocus(false);

        JLabel qajouteLabel = new JLabel("Quantité");

        quantiteField = new WebTextField();
        quantiteField.setInputPrompt("Quantité à ajouter");
        quantiteField.setHideInputPromptOnFocus(false);

        JLabel categorieLabel = new JLabel("Categorie");

        categorieBox = new WebComboBox(categories);
        //setLayout(new GridLayout(8, 2, 10, 10));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // definir la bordure
        setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 1, false), "Informations sur l'ariticle",
                TitledBorder.CENTER, TitledBorder.TOP, null, null));
        
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(7, 7, 7, 7);
        
        gbc.gridy = gbc.gridx =0;
        add(qajouteLabel, gbc);
        
        gbc.gridx = 1;
        add(quantiteField, gbc);
        
        gbc.gridy = 1;
        gbc.gridx = 0;
        
        add(designationLabel, gbc);
        
        gbc.gridy = 1;
        gbc.gridx = 1;
        add(desigationField, gbc);
        
        gbc.gridy = 2;
        gbc.gridx = 0;
        add(prixLabel, gbc);
        
        gbc.gridy = 2;
        gbc.gridx = 1;
        add(prixField, gbc);
        
        gbc.gridy = 3;
        gbc.gridx = 0;
        add(marqueLabel, gbc);
        
        gbc.gridy = 3;
        gbc.gridx = 1;
        add(marqueField, gbc);
        
        gbc.gridy = 4;
        gbc.gridx = 0;
        add(gammeLabel, gbc);
        
        gbc.gridy = 4;
        gbc.gridx = 1;
        add(gammeField, gbc);
        
        gbc.gridy = 5;
        gbc.gridx = 0;
        add(descriptLabel, gbc);
        
        gbc.gridy = 5;
        gbc.gridx = 1;
        add(descripField, gbc);
        
        gbc.gridy = 6;
        gbc.gridx = 0;
        add(categorieLabel, gbc);
        
        gbc.gridy = 6;
        gbc.gridx = 1;
        add(categorieBox, gbc);

//      add(new JPanel());
//      add(ajouterButton);
        
        quantiteField.addFocusListener(new ChampsFocusListener(quantiteField));
        desigationField.addFocusListener(new ChampsFocusListener(desigationField));
        prixField.addFocusListener(new ChampsFocusListener(prixField));
        marqueField.addFocusListener(new ChampsFocusListener(marqueField));
        gammeField.addFocusListener(new ChampsFocusListener(gammeField));
        descripField.addFocusListener(new ChampsFocusListener(descripField));
            
    }

    
    /* methode de verification des saisie. en cas de bonne saise, la valeur du champs 
    est ajoutée dans  un arraylist sinon le champ est colorie en rouge*/
    public boolean verifierChamps() {
        int nberreurs = 0;

        // champsDesignation
        if ( /* desigationField.getText().equals("") || */desigationField.getText().length() < 2) {
            nberreurs++;
            desigationField.setBackground(Constantes.COULEUR_ERR_CHAMPS);
            valeursChamps.clear();
        } else {
            valeursChamps.add(desigationField.getText());
           // System.out.println("designation ok");
        }

        // champs prix
        try {
            Integer.parseInt(prixField.getText());
            valeursChamps.add(prixField.getText());
           // System.out.println("prix ok");
        } catch (Exception e) {
            nberreurs++;
            prixField.setBackground(Constantes.COULEUR_ERR_CHAMPS);
            valeursChamps.clear();
            
        }

        // champs Marque
        if (marqueField.getText().equals("") || (marqueField.getText().length() < 2)) {
            nberreurs++;
            marqueField.setBackground(Constantes.COULEUR_ERR_CHAMPS);
           valeursChamps.clear();
        } else {
            valeursChamps.add(marqueField.getText());
           // System.out.println("marque ok");
        }

        // champs gamme
        if (gammeField.getText().equals("") || (gammeField.getText().length() < 2)) {
            nberreurs++;
            gammeField.setBackground(Constantes.COULEUR_ERR_CHAMPS);
            
            valeursChamps.clear();
        } else {
            valeursChamps.add(gammeField.getText());
           // System.out.println("gamme ok");
        }

        // champs description
        if (descripField.getText().equals("") || (descripField.getText().length() < 2)) {
            nberreurs++;
            descripField.setBackground(Constantes.COULEUR_ERR_CHAMPS);
            
            valeursChamps.clear();
        } else {
            valeursChamps.add(descripField.getText());
            //System.out.println("description ok");
        }

        // champ categorie
        if (categorieBox.getSelectedItem() == null) {
            nberreurs++;
            valeursChamps.clear();
        } else {
            valeursChamps.add((String) categorieBox.getSelectedItem());
            //System.out.println("categorie ok");
        }

        // champs quantite
        try {
            Integer.parseInt(quantiteField.getText());
            valeursChamps.add(quantiteField.getText());
           // System.out.println("quantité ok");
        } catch (Exception e) {
            nberreurs++;
            quantiteField.setBackground(Constantes.COULEUR_ERR_CHAMPS);
            
            valeursChamps.clear();
        }
        
        System.out.println(nberreurs);
        return 0 == nberreurs;
    }

    public WebComboBox getCategorieBox() {
        return categorieBox;
    }

    public ArrayList<String> getValeursChamps() {
        return valeursChamps;
    }

    public static void clearValeursChamps() {
        valeursChamps.clear();
    }

    public WebTextField getDesigationField() {
        return desigationField;
    }

    public WebTextField getPrixField() {
        return prixField;
    }

    public WebTextField getMarqueField() {
        return marqueField;
    }

    public WebTextField getGammeField() {
        return gammeField;
    }

    public WebTextField getDescripField() {
        return descripField;
    }

    public WebTextField getQuantiteField() {
        return quantiteField;
    }
}

