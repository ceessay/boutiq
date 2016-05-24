/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.BackOffice_Admin;

import com.alee.extended.panel.GridPanel;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.GroupingType;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.PatternSyntaxException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import sn.ceessay.samaBoutiq.Classes.Constantes;
import sn.ceessay.samaBoutiq.Classes.DBConnect;
import sn.ceessay.samaBoutiq.FrontOffice_Boutiq.Model;
import sn.ceessay.samaBoutiq.FrontOffice_Boutiq.Notificateur;

/**
 *
 * @author ceessay
 */
class AdminClientsPanel extends WebPanel {

    WebPanel hautPanel;// ce panel contient les champ de recherche et les boutons d'ajout et de suppression d'un client
    WebScrollPane jsp;
    WebTable table;
    WebButton afficherClientButton;
    WebButton supprimerClientButton;
    private WebLabel titre;
    private WebTextField textField;
    private TableRowSorter<TableModel> sorter;

    public AdminClientsPanel() {
        buildGUI();
    }

    private void buildGUI() {

        titre = new WebLabel("Clients");
        titre.setHorizontalAlignment(WebLabel.CENTER);
        titre.setFont(Constantes.POLICE_MOYENNE_25);

        textField = new WebTextField();
        textField.setInputPrompt("Filter...");
        textField.setHideInputPromptOnFocus(false);

        textField.getDocument().addDocumentListener(new TextfieldListener());

        afficherClientButton = new WebButton("Afficher");
        afficherClientButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() >= 0) {
                    afficherClient((int) table.getValueAt(table.getSelectedRow(), 0));

                } else {
                    Notificateur.Info("Vous devez d'abord selectionner un client");
                }

            }

        });

        supprimerClientButton = new WebButton("Supprimer");
        supprimerClientButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() >= 0) {

                    String mes = "Etes-vous sûr de vouloir supprimer ce client.\n Cette action est irreversible";

                    int n = JOptionPane.showConfirmDialog(null, mes);
                    if (n == JOptionPane.YES_OPTION) {
                        supprimerClient((int) table.getValueAt(table.getSelectedRow(), 0));
                    }

                } else {
                    Notificateur.Info("Vous devez d'abord selectionner un client");
                }
            }

        });

        hautPanel = new GroupPanel(GroupingType.fillAll, 15, true, textField, afficherClientButton, supprimerClientButton);

        // instanciation du tableau
        table = new WebTable() {
            //permet d'alterner la couleur de ligne de la table pour une meilleur lisibilité
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component returnComp = super.prepareRenderer(renderer, row, column);
                Color alternateColor = Constantes.COULEUR_LIGNE_TABLE;
                Color whiteColor = Color.WHITE;
                if (!returnComp.getBackground().equals(getSelectionBackground())) {
                    Color bg = (row % 2 == 0 ? alternateColor : whiteColor);
                    returnComp.setBackground(bg);
                    bg = null;
                }
                return returnComp;
            }
        };

        table.setModel(DonneesTables.getDonneesClients());
        table.setEditable(false);
        table.setRowHeight(25);
        table.setSelectionBackground(Constantes.COULEUR_2);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        sorter = new TableRowSorter<TableModel>(table.getModel());
        table.setRowSorter(sorter);

        jsp = new WebScrollPane(table, true, true);

        GroupPanel container = new GroupPanel(10, false, titre, hautPanel, jsp);

        add(container);

    }

    //methode de suppression d'un client a partir de son id
    private void supprimerClient(int i) {

        try {
            if (DBConnect.supprimerClient(i)) {
                
                removeAll();
                buildGUI();
                table.setModel(DonneesTables.getDonneesClients());
                repaint();
                revalidate();
                Notificateur.Info("Client supprimé");
            }
        } catch (SQLException ex) {
            // il est necessaire de gerer le cas ou le client a des commande enregistree. 
            //dans ce cas une exception SQL est lever. On demande a l'utilisateur s'il souhaite oui ou non supprimer
            //le client et toutes ses commande enregistree 
            
            String[] options = new String[] { "Supprimmer le client et ses commandes", "Annuler" };
            int      choix   = JOptionPane.showOptionDialog(null, "<html>Ce client a des commandes enregistées<br/>"
                                                                    +"Sa suppression entrainera la suppression de ses commandes ?<html/>",    // String message,
                "Supprimer le client ?",             // String titl
                JOptionPane.YES_NO_OPTION,          // int type d'option
                JOptionPane.INFORMATION_MESSAGE,    // int message
                null,                               // icone,
                options,                            // Object[] options,
                options[1]);                        // Object initialValue

            if (choix == 0) {
                DBConnect.supprimerCmdClient(i);
                removeAll();
                buildGUI();
                table.setModel(DonneesTables.getDonneesClients());
                repaint();
                revalidate();
                Notificateur.Info("Client supprimé");
            } else {}
            
            
        }

    }

    //metohode permettent d'afficher la fiche d'un client
    private void afficherClient(int i) {
 
        // un dialog est utiliser pour afficher les informations
        final WebDialog dialog = new WebDialog(this);
        dialog.setModal(true);
        dialog.setShowCloseButton(true);

        WebPanel container = new WebPanel();

        String nom = "Nom: ", prenom = "Prenom: ", sexe = "Sexe: ", telephone = "Téléphone: ", email = "Email: ",
                ville = "Ville: ", region = "Région: ", adresse = "Adresse: ", age = "Age: ",
                nbCmd = "Nombre de commandes effectuées: ", valeurDesCmd = "Valeur totale des commandes effectuées: ";

        //recuperation des infos clients
        ResultSet rs1 = DBConnect.SELECT("SELECT * FROM Clients WHERE idClient=" + i);

        try {
            if (rs1.next()) {

                nom = nom + rs1.getString("nom");
                prenom = prenom + rs1.getString("prenom");
                age = age + String.valueOf(rs1.getInt("age") + " ans");
                telephone = telephone + rs1.getString("telephone");
                email = email + rs1.getString("email");
                sexe = sexe + rs1.getString("sexe");
                ville = ville + rs1.getString("ville");
                region = region + rs1.getString("region");
                adresse = adresse + rs1.getString("adresse");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminClientsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        //recuperations nombre de commandes du client
        ResultSet rs2 = DBConnect.SELECT("SELECT COUNT(*) FROM Commandes where idClient = " + i);
        try {
            if (rs2.next()) {
                nbCmd = nbCmd + String.valueOf(rs2.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminClientsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        //recuperation valeur totale des commandes du cliens
        ResultSet rs3 = DBConnect.SELECT("SELECT SUM(valeurCmd) from Commandes where idClient = " + i);

        try {
            if (rs3.next()) {
                valeurDesCmd = valeurDesCmd + String.valueOf(rs3.getInt(1)) + "FCFA";
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminClientsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        WebLabel titreLabel = new WebLabel("Fiche Client");
        titreLabel.setFont(Constantes.POLICE_MOYENNE_35);
        titreLabel.setHorizontalAlignment(WebLabel.CENTER);

        WebLabel num = new WebLabel("Client N° " + i);
        num.setFont(Constantes.POLICE_DE_CMD);
        WebLabel prenoLabel = new WebLabel(prenom);
        prenoLabel.setFont(Constantes.POLICE_DE_CMD);
        WebLabel nomLabel = new WebLabel(nom);
        nomLabel.setFont(Constantes.POLICE_DE_CMD);
        WebLabel sexeLabel = new WebLabel(sexe);
        sexeLabel.setFont(Constantes.POLICE_DE_CMD);
        WebLabel agelaLabel = new WebLabel(age);
        agelaLabel.setFont(Constantes.POLICE_DE_CMD);
        WebLabel telLabel = new WebLabel(telephone);
        telLabel.setFont(Constantes.POLICE_DE_CMD);
        WebLabel emailLabel = new WebLabel(email);
        emailLabel.setFont(Constantes.POLICE_DE_CMD);
        WebLabel adressLabel = new WebLabel(adresse);
        adressLabel.setFont(Constantes.POLICE_DE_CMD);
        WebLabel villeLabel = new WebLabel(ville);
        villeLabel.setFont(Constantes.POLICE_DE_CMD);
        WebLabel regionlabel = new WebLabel(region);
        regionlabel.setFont(Constantes.POLICE_DE_CMD);
        WebLabel nbcmdLabel = new WebLabel(nbCmd);
        nbcmdLabel.setFont(Constantes.POLICE_DE_CMD);
        WebLabel valeurCmdLabel = new WebLabel(valeurDesCmd);
        valeurCmdLabel.setFont(Constantes.POLICE_DE_CMD);

        //bouton de fermeture du dialog
        WebButton fermerButton = new WebButton("Fermer");
        fermerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        //conteneur des element graphiques du dialog
        container.setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.insets = new Insets(10, 15, 10, 15);

        gb.gridy = gb.gridx = 0;
        gb.gridwidth = GridBagConstraints.REMAINDER;
        gb.anchor = GridBagConstraints.CENTER;
        container.add(titreLabel, gb);

        gb.anchor = GridBagConstraints.WEST;
        gb.gridwidth = GridBagConstraints.RELATIVE;

        gb.gridy = 1;
        gb.gridx = 0;
        container.add(num, gb);

        gb.gridy = 2;
        gb.gridx = 0;
        container.add(prenoLabel, gb);

        gb.gridy = 2;
        gb.gridx = 1;
        container.add(nomLabel, gb);

        gb.gridy = 3;
        gb.gridx = 0;
        container.add(sexeLabel, gb);

        gb.gridy = 3;
        gb.gridx = 1;
        container.add(agelaLabel, gb);

        gb.gridy = 4;
        gb.gridx = 0;
        container.add(telLabel, gb);

        gb.gridy = 4;
        gb.gridx = 1;
        container.add(emailLabel, gb);

        gb.gridy = 5;
        gb.gridx = 0;
        gb.gridwidth = GridBagConstraints.REMAINDER;
        container.add(adressLabel, gb);

        gb.gridy = 6;
        gb.gridx = 0;
        container.add(villeLabel, gb);

        gb.gridy = 6;
        gb.gridx = 1;
        container.add(regionlabel, gb);

        gb.gridy = 7;
        gb.gridx = 0;
        gb.gridwidth = GridBagConstraints.REMAINDER;
        container.add(nbcmdLabel, gb);

        gb.gridy = 8;
        gb.gridx = 0;
        gb.gridwidth = GridBagConstraints.REMAINDER;
        container.add(valeurCmdLabel, gb);

        gb.gridy = 9;
        gb.gridx = 0;
        gb.gridwidth = GridBagConstraints.REMAINDER;
        gb.anchor = GridBagConstraints.CENTER;
        container.add(fermerButton, gb);

        container.setBackground(Color.WHITE);

        dialog.setLocation(300, 100);

        dialog.setSize(700, 600);
        dialog.setResizable(false);
        dialog.add(container);
        dialog.setVisible(true);

    }

    // mthode de filtrage des element du table lors d'une recherche avec un 
    private void filtrer(DocumentEvent evt) {
        try {
            String text = textField.getText();
            if (text.length() == 0) {
                sorter.setRowFilter(null);
                repaint();
                revalidate();
                textField.requestFocus();
            } else {
                try {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                } catch (PatternSyntaxException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // listener du champ de filtrage du tableau
    private class TextfieldListener implements DocumentListener {

        public void insertUpdate(DocumentEvent evt) {
            filtrer(evt);
        }

        public void removeUpdate(DocumentEvent evt) {
            filtrer(evt);
        }

        public void changedUpdate(DocumentEvent evt) {
            filtrer(evt);
        }

    }
}
