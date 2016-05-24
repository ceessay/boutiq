
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.FrontOffice_Boutiq;

//~--- non-JDK imports --------------------------------------------------------

import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;

import sn.ceessay.samaBoutiq.Classes.Constantes;

//~--- JDK imports ------------------------------------------------------------

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ceessay
 */
public class ConnectionPanel extends JPanel {
    private Font             font;
    private JLabel           titreLabel;
    private JButton          retourButton;
    private JLabel           loginLabel;
    private WebTextField     loginField;
    private JLabel           passwdLabel;
    private WebPasswordField passwdField;
    private JButton          connexionButton;

    public ConnectionPanel() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        font       = Constantes.POLICE_DE_TITRE;
        titreLabel = new JLabel("Connexion");
        titreLabel.setFont(font);
        font       = Constantes.POLICE_MOYENNE_25;
        loginLabel = new JLabel("Pseudo");
        loginField = new WebTextField(20);
        loginField.setInputPrompt("Entrez votre pseudo...");
        loginField.setHideInputPromptOnFocus(false);
        loginLabel.setFont(font);
        loginField.setFont(font);
        passwdLabel = new JLabel("Mot de passe");
        passwdLabel.setFont(font);
        passwdField = new WebPasswordField(20);
        passwdField.setFont(font);
        passwdField.setInputPrompt("Entrez votre mot de passe...");
        passwdField.setHideInputPromptOnFocus(false);
        gbc.insets = new Insets(30, 0, 0, 0);
        gbc.gridx  = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(40, 0, 0, 0);
        add(titreLabel, gbc);
        gbc.gridy  = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(30, 0, 0, 0);
        add(loginLabel, gbc);
        gbc.gridy  = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(loginField, gbc);
        gbc.gridy  = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(20, 0, 0, 0);
        add(passwdLabel, gbc);
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridy  = GridBagConstraints.RELATIVE;
        add(passwdField, gbc);
        setBackground(new Color(0, 0, 0, 100));
        setVisible(true);
        setBackground(Constantes.COULEUR_DE_FOND);
    }

    public boolean verifier() {
        String login = loginField.getText();
        String pass  = passwdField.getText();

        return !login.equals("") &&!pass.equals("");
    }

    public String getLogin() {
        return loginField.getText();
    }

    public String getPasswd() {
        return passwdField.getText();
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
