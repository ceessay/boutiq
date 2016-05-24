
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.FrontOffice_Boutiq;

//~--- non-JDK imports --------------------------------------------------------

import com.alee.laf.radiobutton.WebRadioButton;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextArea;
import com.alee.laf.text.WebTextField;

import org.apache.commons.validator.routines.EmailValidator;

import sn.ceessay.samaBoutiq.Classes.Constantes;

//~--- JDK imports ------------------------------------------------------------

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 *
 * @author ceessay
 * 
 * la classe InscriptionPanel représente un formulaire d'inscription
 * Elle comprend les champs necessaire a l'inscription et possede un methode de verificatioon de ces champs
 * Les valeurs saisies sont recupérées dans une arrayList qui possede un getter.
 */



public class InscriptionPanel extends JPanel {
    
    // les differents champs du formulaire d'inscription
    private WebTextArea       adresseField = new WebTextArea();
    private ButtonGroup       buttonGroup  = new ButtonGroup();
    private WebRadioButton    hommeButton  = new WebRadioButton("Homme");
    private WebRadioButton    femmeButton  = new WebRadioButton("Femme");
    private WebTextField      prenomField, nomField, telField, emailField, pseudoField, villeField;
    private WebPasswordField  passwdField1, passwdField2;
    private JComboBox         ageBox;
    private JComboBox         regionsComboBox;
    
    // arrayList qui recontiendra les valeurs des champs
    private ArrayList<String> infosClient;
 

    public InscriptionPanel() {
        setBackground(Constantes.COULEUR_DE_FOND);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc        = new GridBagConstraints();
        Font               font       = Constantes.POLICE_DE_TITRE;
        JLabel             titreLabel = new JLabel("Inscription");

        titreLabel.setFont(font);

        // font = new Font("Raleway Thin", Font.PLAIN, 20);
        JLabel prenomLabel = new JLabel("Prénom", SwingConstants.TRAILING);

        // prenomLabel.setFont(font);
        JLabel nomLabel = new JLabel("Nom", SwingConstants.TRAILING);

        // nomLabel.setFont(font);
        JLabel genreLabel = new JLabel("Genre", SwingConstants.TRAILING);

        // genreLabel.setFont(font);
        JLabel ageLabel = new JLabel("Age (ans)", SwingConstants.TRAILING);

        // ageLabel.setFont(font);
        JLabel telLabel = new JLabel("Téléphone", SwingConstants.TRAILING);

        // telLabel.setFont(font);
        JLabel emailLabel = new JLabel("Email", SwingConstants.TRAILING);

        // emailLabel.setFont(font);
        JLabel pseudoLabel = new JLabel("Pseudo", SwingConstants.TRAILING);

        // pseudoLabel.setFont(font);
        JLabel passwdLabel1 = new JLabel("Mot de passe", SwingConstants.TRAILING);

        // passwdLabel1.setFont(font);
        JLabel passwdLabel2 = new JLabel("Rentrez le mot de passe", SwingConstants.TRAILING);

        // passwdLabel2.setFont(font);
        JLabel adresseLabel = new JLabel("Adresse complête", SwingConstants.TRAILING);

        // adresseLabel.setFont(font);
        JLabel villeLabel = new JLabel("Ville", SwingConstants.TRAILING);

        // villeLabel.setFont(font);
        JLabel regionLabel = new JLabel("Région", SwingConstants.TRAILING);

        // regionLabel.setFont(font);

//      champs du formulaire d'inscription
        prenomField = new WebTextField();

        // prenomField.setFont(font);
        prenomField.setColumns(20);
        nomField = new WebTextField();
        nomField.setColumns(20);

        // nomField.setFont(font);

        Vector<Integer> ages = new Vector<>();

        ages.add(null);

        for (int i = 18; i <= 100; i++) {
            ages.add(i);
        }

        ageBox = new JComboBox(ages);
        ageBox.setBackground(Constantes.COULEUR_TRANPARENTE);

        // ageBox.setFont(font);
        buttonGroup.add(hommeButton);
        buttonGroup.add(femmeButton);

        JPanel genrePanel = new JPanel();

        genrePanel.add(hommeButton);
        genrePanel.setBackground(Constantes.COULEUR_TRANPARENTE);
        genrePanel.add(femmeButton);
        telField = new WebTextField();

        // telField.setFont(font);
        telField.setColumns(20);
        telField.setInputPrompt("Ex: 771234567");
        telField.setInputPromptFont(Constantes.POLICE_MINI);
        telField.setHideInputPromptOnFocus(false);
        
        emailField = new WebTextField();
        emailField.setInputPrompt("Entrez votre mail");
        emailField.setInputPromptFont(Constantes.POLICE_MINI);
        emailField.setHideInputPromptOnFocus(false);
        emailField.setColumns(20);
        
        pseudoField = new WebTextField();
        pseudoField.setColumns(20);
        pseudoField.setInputPrompt("Choisissez un pseudo");
        pseudoField.setInputPromptFont(Constantes.POLICE_MINI);
        pseudoField.setHideInputPromptOnFocus(false);
        passwdField1 = new WebPasswordField();
        passwdField1.setColumns(20);
        passwdField1.setInputPrompt("Choisissez un mot de passe");
        passwdField1.setInputPromptFont(Constantes.POLICE_MINI);
        passwdField1.setHideInputPromptOnFocus(false);
        passwdField2 = new WebPasswordField();
        passwdField2.setColumns(20);
        passwdField2.setInputPrompt("Confirmez le mot de passe");
        passwdField2.setInputPromptFont(Constantes.POLICE_MINI);
        passwdField2.setHideInputPromptOnFocus(false);
        villeField = new WebTextField();

        // villeField.setFont(font);
        villeField.setColumns(20);
        villeField.setInputPrompt("Indiquez votre ville");
        villeField.setInputPromptFont(Constantes.POLICE_MINI);
        villeField.setHideInputPromptOnFocus(false);

        String[] regions = new String[] {
            "", "Dakar", "Diourbel", "Fatick", "Kaffrine", "Kaolack", "Kédougou", "Kolda", "Louga", "Matam",
            "Saint-Louis", "Sédhiou", "Tambacounda", "Thiès", "Ziguinchor"
        };

        regionsComboBox = new JComboBox(regions);

        // regionsComboBox.setFont(font);
        adresseField = new WebTextArea();
        adresseField.setRows(3);
        adresseField.setLineWrap(true);
        adresseField.setWrapStyleWord(true);

        JScrollPane adresseScrollpane = new JScrollPane();

        adresseScrollpane.setViewportView(adresseField);

        JPanel jp  = new JPanel(new GridLayout(6, 2, 5, 7));
        JPanel jp2 = new JPanel(new GridLayout(6, 2, 5, 7));

        jp.setBackground(Constantes.COULEUR_TRANPARENTE);
        jp2.setBackground(Constantes.COULEUR_TRANPARENTE);
        
        jp.add(prenomLabel);
        jp.add(prenomField);
        jp.add(nomLabel);
        jp.add(nomField);
        jp.add(ageLabel);
        jp.add(ageBox);
        jp.add(telLabel);
        jp.add(telField);
        jp.add(emailLabel);
        jp.add(emailField);
        jp.add(genreLabel);
        jp.add(genrePanel);
        jp2.add(pseudoLabel);
        jp2.add(pseudoField);
        jp2.add(passwdLabel1);
        jp2.add(passwdField1);
        jp2.add(passwdLabel2);
        jp2.add(passwdField2);
        jp2.add(villeLabel);
        jp2.add(villeField);
        jp2.add(regionLabel);
        jp2.add(regionsComboBox);
        jp2.add(new JLabel());
        jp2.add(new JLabel());
        gbc.gridy     = 0;
        gbc.gridx     = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets    = new Insets(0, 0, 40, 0);

        // add(retourButton, gbc);
        gbc.gridy  = 1;
        gbc.gridx  = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 200, 30, 0);
        add(titreLabel, gbc);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy  = 2;
        gbc.gridx  = 0;
        add(jp);
        gbc.gridy = 2;
        gbc.gridx = 1;
        add(jp2);
        gbc.insets = new Insets(30, 200, 0, 0);
        gbc.gridy  = 3;
        gbc.gridx  = 0;
        add(adresseLabel, gbc);
        gbc.insets    = new Insets(0, 200, 0, 0);
        gbc.gridy     = 4;
        gbc.gridx     = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill      = GridBagConstraints.HORIZONTAL;
        gbc.anchor    = GridBagConstraints.LINE_START;
        add(adresseScrollpane, gbc);
        
        
        prenomField.addFocusListener(new ChampsFocusListener(prenomField));
        nomField.addFocusListener(new ChampsFocusListener(nomField));
        telField.addFocusListener(new ChampsFocusListener(telField));
        emailField.addFocusListener(new ChampsFocusListener(emailField));
        villeField.addFocusListener(new ChampsFocusListener(villeField));
        pseudoField.addFocusListener(new ChampsFocusListener(pseudoField));
        passwdField2.addFocusListener(new ChampsFocusListener(passwdField2));
    }

    
    
    public boolean validerInsccription() {
        infosClient = new ArrayList<>();

        int nbErreurs = 0;

        // nom
        if (nomField.getText().equals("") || (nomField.getText().length() < 2)) {
            nbErreurs++;
            nomField.setBackground(Constantes.COULEUR_ERR_CHAMPS);
            nomField.setInputPrompt("Trop court...");
            nomField.setInputPromptForeground(Color.WHITE);
            nomField.setInputPromptFont(Constantes.POLICE_MINI);
            
            
            
        } else {
            infosClient.add(nomField.getText());
           // System.out.println("nom ok");
        }

        // prenom
        if (prenomField.getText().equals("") || (prenomField.getText().length() < 2)) {
            nbErreurs++;
            prenomField.setBackground(Constantes.COULEUR_ERR_CHAMPS);
            prenomField.setInputPrompt("Trop court...");
            prenomField.setInputPromptForeground(Color.WHITE);
            prenomField.setInputPromptFont(Constantes.POLICE_MINI);
          //  prenomField.setHideInputPromptOnFocus(false);
            
        } else {
           // System.out.println("prenom ok");
            infosClient.add(prenomField.getText());
        }

        // age
        if (ageBox.getSelectedItem() == null) {
            nbErreurs++;
        } else {
            infosClient.add(String.valueOf(ageBox.getSelectedItem()));
           // System.out.println("age ok");
        }

        // telephone
        boolean t1;

        if (telField.getText().equals("") || (telField.getText().length() != 9)) {
            telField.setInputPrompt("Numero de téléphone incorrect");
            telField.setInputPromptFont(Constantes.POLICE_MINI);
            telField.setInputPromptForeground(Color.WHITE);
            //telField.setHideInputPromptOnFocus(false);
            
            t1 = false;
        } else {
            t1 = true;
        }

        boolean t2;

        if (telField.getText().equals("")
                ||!(telField.getText().startsWith("70") || telField.getText().startsWith("76")
                    || telField.getText().startsWith("77") || telField.getText().startsWith("78"))) {
            telField.setInputPrompt("Numéro de téléphone incorrect");
            telField.setInputPromptFont(Constantes.POLICE_MINI);
            //telField.setHideInputPromptOnFocus(false);
            t2 = false;
        } else if (telField.getText().startsWith("++221") || telField.getText().startsWith("00221")
                   || telField.getText().startsWith("221")) {
            telField.setInputPrompt("Indiquez le numéro sans l'indicatif");
            telField.setInputPromptFont(Constantes.POLICE_MINI);
            telField.setHideInputPromptOnFocus(false);
            
            t2 = false;
        } else {
            t2 = true;
        }

        if (t1 && t2) {
            infosClient.add(telField.getText());
           // System.out.println("tel ok");
        } else {
            nbErreurs++;
            telField.setBackground(Constantes.COULEUR_ERR_CHAMPS);
            telField.setInputPromptForeground(Color.WHITE);
        }

        // email
        if (EmailValidator.getInstance().isValid(emailField.getText())) {
            infosClient.add(emailField.getText());
            //System.out.println("mail ok");
        } else {
            nbErreurs++;
            emailField.setBackground(Constantes.COULEUR_ERR_CHAMPS);
            emailField.setInputPrompt("Email invalide...");
            emailField.setInputPromptFont(Constantes.POLICE_MINI);
            emailField.setInputPromptForeground(Color.WHITE);
          //  emailField.setHideInputPromptOnFocus(false);
            
        }

        // genre
        if (!hommeButton.isSelected() &&!femmeButton.isSelected()) {
            nbErreurs++;
        } else {
            if (hommeButton.isSelected()) {
                infosClient.add(hommeButton.getText());
            } else if (femmeButton.isSelected()) {
                infosClient.add(hommeButton.getText());
            }

           // System.out.println("genre ok");
        }

        // ville
        if (villeField.getText().equals("") || (villeField.getText().length() < 2)) {
            nbErreurs++;
            villeField.setBackground(Constantes.COULEUR_ERR_CHAMPS);
            villeField.setInputPrompt("Trop court...");
            villeField.setInputPromptForeground(Color.WHITE);
            
        } else {
            infosClient.add(villeField.getText());
            //System.out.println("ville ok");
        }

        // region
        if (regionsComboBox.getSelectedItem() == null) {
            nbErreurs++;
            regionsComboBox.setBackground(Constantes.COULEUR_ERR_CHAMPS);
        } else {
            infosClient.add((String) regionsComboBox.getSelectedItem());
//            System.out.println("region ok");
        }

        // adresse complête
        if (adresseField.getText().equals("") || (adresseField.getText().length() < 10)) {
            nbErreurs++;
            adresseField.setBackground(Constantes.COULEUR_ERR_CHAMPS);
            adresseField.addFocusListener(new FocusAdapter() {

                public void focusGained(FocusEvent e) {
                    adresseField.setBackground(Color.white);
                }

            });
        } else {
            infosClient.add(adresseField.getText());
//            System.out.println("Adresse ok");
        }

        // pseudo
        if (pseudoField.getText().equals("") || (pseudoField.getText().length() < 5)) {
            nbErreurs++;
            pseudoField.setBackground(Constantes.COULEUR_ERR_CHAMPS);
            pseudoField.setInputPrompt("Trop court: au moins 6 lettres requises");
            pseudoField.setInputPromptFont(Constantes.POLICE_MINI);
            pseudoField.setInputPromptForeground(Color.WHITE);
            //pseudoField.setHideInputPromptOnFocus(false);
            
        } else {
            infosClient.add(pseudoField.getText());
//            System.out.println("pseudo ok");
        }

        // mots de passe
        if (passwdField1.getText().equals("") || (passwdField1.getText().length() < 8)) {
            nbErreurs++;
            passwdField1.setBackground(Constantes.COULEUR_ERR_CHAMPS);
            passwdField1.setInputPromptForeground(Color.WHITE);
            passwdField1.setInputPrompt("Trop court: au moins 8 caractères requis");
            passwdField1.setInputPromptFont(Constantes.POLICE_MINI);
            passwdField1.setHideInputPromptOnFocus(false);
            passwdField1.addFocusListener(new ChampsFocusListener(passwdField1));
        }

        if (passwdField2.getText().equals("") ||!passwdField1.getText().equals(passwdField2.getText())) {
            nbErreurs++;
            passwdField2.setBackground(Constantes.COULEUR_ERR_CHAMPS);
            passwdField2.setInputPromptForeground(Color.WHITE);
            passwdField2.setInputPrompt("Les mots de passe ne correspondent pas");
            passwdField2.setInputPromptFont(Constantes.POLICE_MINI);
            passwdField2.setHideInputPromptOnFocus(false);
            
        } else {
            infosClient.add(passwdField2.getText());
//            System.out.println("pass ok");
        }

//        System.out.println(nbErreurs);

        return (nbErreurs == 0);
    }

    public ArrayList<String> getInfoClients() {
        return infosClient;
    }
    
    public void clear(){
        infosClient.clear();
    }
}

