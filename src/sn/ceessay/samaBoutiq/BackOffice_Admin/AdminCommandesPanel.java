/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.BackOffice_Admin;

import com.alee.extended.button.WebSwitch;
import com.alee.extended.panel.GridPanel;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.GroupingType;
import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.PatternSyntaxException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import sn.ceessay.samaBoutiq.Classes.Constantes;
import sn.ceessay.samaBoutiq.Classes.DBConnect;
import sn.ceessay.samaBoutiq.FrontOffice_Boutiq.Notificateur;

/**
 *
 * @author ceessay
 */
class AdminCommandesPanel extends WebPanel {

    WebPanel hautPanel;
    WebScrollPane jsp;
    WebTable table;
    //WebButton nouvelleCmdButton;
    WebButton afficherCmdButton;
    WebButton supprimerCmdButton;
    private WebLabel titre;
    private WebTextField textField;
    private TableRowSorter<TableModel> sorter;

    public AdminCommandesPanel() {
        buildGUI();
    }

    private void buildGUI() {

        titre = new WebLabel("Commandes");
        titre.setHorizontalAlignment(WebLabel.CENTER);
        titre.setFont(Constantes.POLICE_MOYENNE_25);

        textField = new WebTextField();
        textField.setInputPrompt("Filter...");
        textField.setHideInputPromptOnFocus(false);

        textField.getDocument().addDocumentListener(new TextfieldListener());

        afficherCmdButton = new WebButton("Afficher");
        afficherCmdButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() >= 0) {
                    afficherCmd((int) table.getValueAt(table.getSelectedRow(), 0));

                } else {
                    Notificateur.Info("Vous devez d'abord selectionner une commande");
                }

            }

        });

//        nouvelleCmdButton = new WebButton("Nouveau");
//        nouvelleCmdButton.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ajouterNouvelleCmd();
//            }
//
//        });
        supprimerCmdButton = new WebButton("Supprimer");
        supprimerCmdButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (table.getSelectedRow() >= 0) {

                    String mes = "Etes-vous sûr de vouloir supprimer cette commande.\nCette action est irreversible";

                    int n = JOptionPane.showConfirmDialog(null, mes);
                    if (n == JOptionPane.YES_OPTION) {
                        supprimerCmd((int) table.getValueAt(table.getSelectedRow(), 0));
                    }

                } else {
                    Notificateur.Info("Vous devez d'abord selectionner une commande");
                }
            }

        });

        hautPanel = new GroupPanel(GroupingType.fillAll, 15, true, textField, afficherCmdButton, /*nouvelleCmdButton,*/ supprimerCmdButton);

        
        
        
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
        
        table.setModel(DonneesTables.getDonneesCmd());
        
        
        
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
    
    
    // methode d'affichage d'une commende a partir de son id. les infos sont recuperees dans la base de donnees
    private void afficherCmd(int i) {
        ResultSet rs;
        final int idcmd = i;
        String dateCmd = null;
        int valeurCmd = 0;
        int idClient = 0;
        boolean etatcmd = false;
        String prenom = null;
        String nom = null;
        String tel = null;
        String adresse = null;
        WebTable commandeTable = null;

        // infos Commande
        rs = DBConnect.SELECT("SELECT * FROM Commandes WHERE idCmd = " + idcmd);

        try {
            if (rs.next()) {
                dateCmd = rs.getDate(3).toString();
                valeurCmd = rs.getInt(2);
                idClient = rs.getInt(4);
                etatcmd = rs.getBoolean(5);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminCommandesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        // infos client
        rs = DBConnect.SELECT(
                "SELECT Clients.prenom, Clients.nom, Clients.telephone, Clients.adresse, Clients.ville, Clients.region "
                + "            FROM Clients WHERE idClient = " + idClient);

        try {
            if (rs.next()) {
                prenom = rs.getString(1);
                nom = rs.getString(2);
                tel = rs.getString(3);
                adresse = rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminCommandesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        // infos lignes de commande
        rs = DBConnect.SELECT(
                "SELECT Articles.designation, Articles.prix, Lignedecommades.quantite, Lignedecommades.valeurlignecmd FROM Articles, Lignedecommades "
                + "WHERE  Lignedecommades.idCmd =" + idcmd + "  AND Lignedecommades.idArticles = Articles.idArticles");

        String[] entetes = new String[]{"Article(s)", " Prix unitaire", "Nombre d'unités", "Valeur"};
        DefaultTableModel model = new DefaultTableModel(entetes, 0);

        try {
            while (rs.next()) {
                Vector row = new Vector();

                for (int j = 1; j <= 4; j++) {
                    row.addElement(rs.getObject(j));
                }

                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminCommandesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        commandeTable = new WebTable();
        commandeTable.setEditable(false);
        commandeTable.setRowHeight(20);
        commandeTable.setFontSize(17);
        commandeTable.setPreferredSize(new Dimension(500, 100));
        commandeTable.setModel(model);
        commandeTable.setRowHeight(15);

        JScrollPane sp = new JScrollPane();

        sp.setViewportView(commandeTable);

        // = new JTable(donnees);
        // definir le panel
        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBackground(Color.WHITE);

        GridBagConstraints g = new GridBagConstraints();
        final WebCheckBox switch1 = new WebCheckBox();
        switch1.setSelected(etatcmd);
        switch1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (switch1.isSelected()) {
                    if (DBConnect.changerEtatCmd(idcmd, switch1.isSelected())) {
                        Notificateur.Info("<html>Etat de la commande modifiée<br>Commande livrée</html>");
                    }
                } else {
                    if (DBConnect.changerEtatCmd(idcmd, switch1.isSelected())) {
                        Notificateur.Info("<html>Etat de la commande modifiée<br>Commande non livrée</html>");
                    }
                }

                table.setModel(DonneesTables.getDonneesCmd());
                repaint();
                revalidate();
            }
        });

        JLabel nulcmdLabel = new JLabel("Commande N° " + idcmd);

        nulcmdLabel.setFont(Constantes.POLICE_DE_CMD);

        JLabel etatcmdLabel = new JLabel("Livraison : ");

        etatcmdLabel.setFont(Constantes.POLICE_DE_CMD);

        JLabel datecmdlabel = new JLabel("Date: " + dateCmd);

        datecmdlabel.setFont(Constantes.POLICE_DE_CMD);

        JLabel nomClientLabel = new JLabel("Client: " + prenom + " " + nom);

        nomClientLabel.setFont(Constantes.POLICE_DE_CMD);

        JLabel tellabel = new JLabel("Téléphone: " + tel);

        tellabel.setFont(Constantes.POLICE_DE_CMD);

        JLabel adresslabel = new JLabel("Adresse: " + adresse);

        adresslabel.setFont(Constantes.POLICE_DE_CMD);

        JLabel prixLabel = new JLabel("TOTAL:   " + String.valueOf(valeurCmd)+" FCFA");

        prixLabel.setFont(Constantes.POLICE_PPIX_TOTAL);

        JPanel j = new JPanel(new GridLayout(2, 2, 50, 20));

        j.setBackground(Color.WHITE);
        j.add(nulcmdLabel);

        GridPanel etatPanel = new GridPanel(1, 2, etatcmdLabel, switch1);
        
        j.add(datecmdlabel);
        j.add(etatPanel);
        j.add(nomClientLabel);
        j.add(tellabel);
        g.insets = new Insets(0, 0, 20, 0);
        g.gridy = g.gridx = 0;
        panel.add(j, g);
        g.gridy = 1;
        panel.add(adresslabel, g);
        g.gridy = 2;
        g.gridx = 0;
        g.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(sp, g);
        g.gridy = 3;
        g.gridx = 0;
        g.anchor = GridBagConstraints.CENTER;
        panel.add(prixLabel, g);

        JScrollPane scrollPane = new JScrollPane();

        scrollPane.setViewportView(panel);

        JFrame frame = new JFrame("Commande N°" + idcmd);

        frame.setLocation(300, 200);
        frame.setSize(700, 400);

        // frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(scrollPane);
        frame.setVisible(true);

    }

//    private void ajouterNouvelleCmd() {
//
//    }
    
    // suppression d'une commande a partir de son id
    private void supprimerCmd(int i) {

         if (DBConnect.supprimerCmd(i)) {
            removeAll();
            buildGUI();
            table.setModel(DonneesTables.getDonneesCmd());
            repaint();
            revalidate();
            Notificateur.Info("Commande supprimée");
        } else {
            Notificateur.Info("<html>Une erreur s'est produite lors de la suppression<br>"
                    + "La commande n'a pa été supprimmé</html>");
        }
    }

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
