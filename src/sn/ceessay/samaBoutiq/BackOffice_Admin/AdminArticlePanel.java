/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.BackOffice_Admin;

import com.alee.extended.panel.GridPanel;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.GroupingType;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
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
public class AdminArticlePanel extends WebPanel {
    
    WebPanel hautPanel;
    WebScrollPane jsp;
    WebTable table;
    WebButton nouvelArticleButton;
    WebButton afficherArticleButton;
    WebButton supprimerArticlButton;
    WebTextField textField;
    private WebLabel titre;
    private TableRowSorter<TableModel> sorter;
    private AfficherArticleListener afficherArticleListener = new AfficherArticleListener();
    private NouvelArticleListener nouvelArticleListener = new NouvelArticleListener();
    private SupprimmerListener supprimmerListener = new SupprimmerListener();
    
    public AdminArticlePanel() {
        buildGUI();
    }
    
    private void buildGUI() {
        
        titre = new WebLabel("Articles");
        titre.setHorizontalAlignment(WebLabel.CENTER);
        titre.setFont(Constantes.POLICE_MOYENNE_25);
        
        textField = new WebTextField();
        textField.setInputPrompt("Filter...");
        textField.setHideInputPromptOnFocus(false);
        textField.getDocument().addDocumentListener(new TextfieldListener());
        
        afficherArticleButton = new WebButton("Afficher/Modifier");
        afficherArticleButton.addActionListener(afficherArticleListener);
        
        nouvelArticleButton = new WebButton("Nouveau");
        nouvelArticleButton.addActionListener(nouvelArticleListener);
        
        supprimerArticlButton = new WebButton("Supprimer");
        supprimerArticlButton.addActionListener(supprimmerListener);
        
        hautPanel = new GroupPanel(GroupingType.fillAll, 15, true, textField, afficherArticleButton, nouvelArticleButton, supprimerArticlButton);

        
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
        
        
        table.setModel(DonneesTables.getDonneesArticles());
        table.setRowHeight(25);

//        
//        table.setDefaultRenderer(Object.class, new TableCellRenderer(){
//            private DefaultTableCellRenderer DEFAULT_RENDERER =  new DefaultTableCellRenderer();
//            @Override
//            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                if (row%2 == 0){
//                    c.setBackground(Color.WHITE);
//                }
//                else {
//                    c.setBackground(Color.LIGHT_GRAY);
//                }                        
//                return c;
//            }
//
//            
//
//        });
        table.setSelectionBackground(Constantes.COULEUR_2);
        table.setEditable(false);
        //
        // table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        WebPopupMenu popupMenu = new WebPopupMenu();
//        
//        WebMenuItem afficheritem = new WebMenuItem("Afficher/Modifier");
//        afficheritem.addActionListener(afficherArticleListener);
//        WebMenuItem nouvel = new WebMenuItem("Ajouter un nouvel article");
//        nouvel.addActionListener(nouvelArticleListener);
//        WebMenuItem suppr = new WebMenuItem("Supprimmer");
//        suppr.addActionListener(supprimmerListener);
//        
//        popupMenu.add(afficheritem);
//        popupMenu.addSeparator();
//        popupMenu.add(nouvel);
//        popupMenu.addSeparator();
//        popupMenu.add(suppr);

//        table.setComponentPopupMenu(popupMenu);
//        table.setco
        sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        
        jsp = new WebScrollPane(table, true, true);
        
        GroupPanel container = new GroupPanel(10, false, titre, hautPanel, jsp);
        
        add(container);
        textField.requestFocus();
    }
    
    
    // methode d'ajout d'un article. elle ouvre un dialog dans le quel l'utilisateur saisie les 
    //differentes informations sur l'articles avant de les valider
    private void ajouterNouvelArticle() {
        
        final WebDialog dialog = new WebDialog(this);
        dialog.setTitle("Ajouter un nouvel article");
        
        JPanel jp = new JPanel();
        GridBagConstraints g = new GridBagConstraints();
        
        final InfosArticlesPanel infosArticle = new InfosArticlesPanel();
        final ImageArticlePanel imgpanel = new ImageArticlePanel();
        final CaracteristiquesArticlePanel caracteristiquesPanel = new CaracteristiquesArticlePanel();
        
        final JButton ajouterButton = new JButton("Ajouter");
        final JButton annulerButton = new JButton("Annuler");
        
        jp.setLayout(new GridBagLayout());
        ajouterButton.setFont(Constantes.POLICE_MOYENNE_17);
        
        
        ajouterButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String txt = "<html>Erreur(s): champ(s) invalide(s).<br>Veuillez verifier les valeurs entrées dans les champs<html/>";
                if (!(infosArticle.verifierChamps() && caracteristiquesPanel.verifierChamps())) {
                    infosArticle.clearValeursChamps();
                    caracteristiquesPanel.clearValeursChamps();
                    
                    if (infosArticle.getCategorieBox().getSelectedItem() == null) {
                        txt = txt + "<br>Vous n'avez pas défini la categorie";
                        //ajouterButton.requestFocusInWindow();
                        infosArticle.clearValeursChamps();
                        caracteristiquesPanel.clearValeursChamps();
                    }
                    if (caracteristiquesPanel.getArchCPUBox().getSelectedItem() == null) {
                        txt = txt + "<br>Vous n'avez pas défini l'architechture du processeur";
                        // ajouterButton.requestFocusInWindow();
                        infosArticle.clearValeursChamps();
                        caracteristiquesPanel.clearValeursChamps();
                    }
                    
                    if (imgpanel.getImage() == null) {
                        txt = txt + "<br>Vous n'avez pas choisi d'image";
                        ajouterButton.requestFocusInWindow();
                        infosArticle.clearValeursChamps();
                        caracteristiquesPanel.clearValeursChamps();
                    }
                    
                    ajouterButton.requestFocusInWindow();
                    Notificateur.Erreur(txt);
                    
                } else if (imgpanel.getImage() == null) {
                    txt = "Vous n'avez pas choisi d'image";
                    Notificateur.Erreur(txt);
                    ajouterButton.requestFocusInWindow();
                    infosArticle.clearValeursChamps();
                    caracteristiquesPanel.clearValeursChamps();
                    
                } else {
                    
                    DBConnect.ajouterArticle(infosArticle.getValeursChamps(), caracteristiquesPanel.getValeursChamps(), imgpanel.getImage());

//                AjouterArticleDialog.this.setVisible(false);
//                AjouterArticleDialog.this.dispatchEvent(new WindowEvent(
//                AjouterArticleDialog.this, WindowEvent.WINDOW_CLOSING));
                    dialog.dispose();
                    removeAll();
                    buildGUI();
                    table.setModel(DonneesTables.getDonneesArticles());
                    repaint();
                    revalidate();
                    Notificateur.Info(infosArticle.getMarqueField().getText() + " " + infosArticle.getDesigationField().getText() + " ajouté");
                    
                }
            }
        });
        
        annulerButton.setFont(Constantes.POLICE_MOYENNE_17);
        annulerButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        
        imgpanel.setPreferredSize(infosArticle.getPreferredSize());
        JScrollPane jsp = new JScrollPane();
        
        g.gridy = g.gridx = 0;
        jp.add(infosArticle, g);
        
        g.gridx = 1;
        g.anchor = GridBagConstraints.LINE_END;
        jp.add(imgpanel, g);
        
        g.gridy = 1;
        g.gridx = 0;
        //g.insets = new Insets(50, 0, 0, 0);
        g.gridwidth = GridBagConstraints.REMAINDER;
        jp.add(caracteristiquesPanel, g);

        //  GroupPanel gp = new GroupPanel(GroupingType.fillAll, 15, true, annulerButton, ajouterButton);
        GridPanel gp = new GridPanel(1, 2, 15, ajouterButton, annulerButton);
        
        g.gridy = 2;
        g.gridx = 0;
        g.insets = new Insets(10, 0, 0, 0);
        g.anchor = GridBagConstraints.LINE_END;
        jp.add(gp, g);

//        g.gridy = 2;
//        g.gridx = 1;
//        g.gridwidth = GridBagConstraints.REMAINDER;
//        g.anchor = GridBagConstraints.CENTER;
//        jp.add(ajouterButton, g);
        jsp.setViewportView(jp);
        dialog.add(jsp);
        //dialog.setBackground(Constantes.COULEUR_DE_FOND);
        dialog.setSize(800, 700);
        dialog.setModal(true);
        dialog.setLocationRelativeTo(jsp);
        dialog.setVisible(true);
        
    }

    //methode qui ouvre un dialogue contenant les caracteristiques de l'article
    private void afficherArticle(int id) {
        
        final WebDialog dialog = new WebDialog();
        dialog.setTitle("Vue article");
        
        ResultSet rs1;
        ResultSet rs2;
        ResultSet rs3;
        
        final InfosArticlesPanel infosArticle = new InfosArticlesPanel();
        final ImageArticlePanel imagePanel = new ImageArticlePanel();
        final CaracteristiquesArticlePanel caracteristiquesPanel = new CaracteristiquesArticlePanel();
        
        final int idarti = id;

        //infos Commande
        rs1 = DBConnect.SELECT("SELECT * FROM Articles WHERE idArticles = " + idarti);
        
        try {
            if (rs1.next()) {
                infosArticle.setIdarticle(rs1.getInt(1));
                infosArticle.getDesigationField().setText(rs1.getString(2));
                infosArticle.getPrixField().setText(String.valueOf(rs1.getInt(3)));
                infosArticle.getMarqueField().setText(rs1.getString(4));
                infosArticle.getGammeField().setText(rs1.getString(5));
                infosArticle.getDescripField().setText(rs1.getString(6));
                infosArticle.getCategorieBox().setSelectedItem(rs1.getString(7));
                infosArticle.getQuantiteField().setText(rs1.getString(8));
                
            }
            
            rs2 = DBConnect.SELECT("SELECT image From Articles WHERE idArticles = " + idarti);
            if (rs2.next()) {
//                    Blob aBlob = rs.getBlob("Photo");
//                    InputStream is = aBlob.getBinaryStream(0, aBlob.length());
//                    BufferedImage imag = ImageIO.read(is);
//                    Image image = imag;
//                    ImageIcon icon = new ImageIcon(image);
//                    lblImage.setIcon(icon);

                Blob aBlob = rs2.getBlob(1);
                InputStream is = aBlob.getBinaryStream(1, aBlob.length());
                imagePanel.setImageLabel(aBlob);
            }
            
            rs3 = DBConnect.SELECT("SELECT * FROM Caracteristiques WHERE idArticles = " + idarti);
            if (rs3.next()) {
                caracteristiquesPanel.getOsField().setText(rs3.getString(2));
                caracteristiquesPanel.getMarqueCPUField().setText(rs3.getString(3));
                caracteristiquesPanel.getCadenceCPUField().setText(String.valueOf(rs3.getDouble(4)));
                caracteristiquesPanel.getArchCPUBox().setSelectedItem(rs3.getString(5));
                caracteristiquesPanel.getTailleRAMField().setText(String.valueOf(rs3.getDouble(6)));
                caracteristiquesPanel.getTailleDDField().setText(String.valueOf(rs3.getInt(7)));
                caracteristiquesPanel.getTailleEcranField().setText(String.valueOf(rs3.getDouble(8)));
                caracteristiquesPanel.getWifiCheckBox().setSelected(rs3.getBoolean(9));
                caracteristiquesPanel.getBluetoothCheckBox().setSelected(rs3.getBoolean(10));
                caracteristiquesPanel.getWebcamCheckBox().setSelected(rs3.getBoolean(11));
                caracteristiquesPanel.getCameraCheckBox().setSelected(rs3.getBoolean(13));
                
                if (caracteristiquesPanel.getWebcamCheckBox().isSelected()) {
                    caracteristiquesPanel.getResolutionWebField().setText(String.valueOf(rs3.getDouble(12)));
                    caracteristiquesPanel.getResolutionWebField().setEnabled(true);
                } else {
                    caracteristiquesPanel.getResolutionWebField().setText("");
                    caracteristiquesPanel.getResolutionWebField().setEnabled(false);
                }
                
                if (caracteristiquesPanel.getCameraCheckBox().isSelected()) {
                    caracteristiquesPanel.getResolutionCamField().setText(String.valueOf(rs3.getDouble(14)));
                    caracteristiquesPanel.getResolutionCamField().setEnabled(true);
                } else {
                    caracteristiquesPanel.getResolutionCamField().setText("");
                    caracteristiquesPanel.getResolutionCamField().setEnabled(false);
                }
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        dialog.setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
        
        JPanel jp = new JPanel();
        jp.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        
        JButton modifierButton = new JButton("Modifier");
        modifierButton.setFont(Constantes.POLICE_MOYENNE_17);
        modifierButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                String txt = "<html>Erreur Champs invalides.<br>Veuillez verifier les valeurs entrées dans les champs<br> <html/>";
                if (infosArticle.verifierChamps() && caracteristiquesPanel.verifierChamps() && (imagePanel.getImage() != null)) {
                    
                    if (DBConnect.modifierArticle(idarti, infosArticle.getValeursChamps(), caracteristiquesPanel.getValeursChamps(), imagePanel.getImage())) {
                        
                        dialog.dispose();
                        table.setModel(DonneesTables.getDonneesArticles());
                        repaint();
                        revalidate();
                        Notificateur.Info(infosArticle.getMarqueField().getText() + " " + infosArticle.getDesigationField().getText() + " modifié");
                    }
                    
                } else {
                    infosArticle.clearValeursChamps();
                    caracteristiquesPanel.clearValeursChamps();
                    Notificateur.Erreur(txt);
                }
            }
        });
        JButton annulerButton = new JButton("Fermer");
        annulerButton.setFont(Constantes.POLICE_MOYENNE_17);
        annulerButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                //  imagePanel.deleteimg();
                dialog.dispose();
                dialog.removeAll();
                
            }
        });
        
        imagePanel.setPreferredSize(infosArticle.getPreferredSize());
        JScrollPane jsp = new JScrollPane();
        
        g.gridy = g.gridx = 0;
        jp.add(infosArticle, g);
        
        g.gridx = 1;
        g.gridwidth = GridBagConstraints.REMAINDER;
        g.anchor = GridBagConstraints.LINE_END;
        jp.add(imagePanel, g);
        
        g.gridy = 1;
        g.gridx = 0;
        g.gridwidth = GridBagConstraints.REMAINDER;
        jp.add(caracteristiquesPanel, g);

        //  GroupPanel gp = new GroupPanel(GroupingType.fillAll, 15, true, annulerButton, modifierButton);
        GridPanel gp = new GridPanel(1, 2, 15, modifierButton, annulerButton);
        
        g.gridy = 2;
        g.gridx = 0;
        g.anchor = GridBagConstraints.LINE_END;
        g.insets = new Insets(10, 0, 0, 0);
        jp.add(gp, g);

//        g.gridy = 2;
//        g.gridx = 1;
//        g.gridwidth = GridBagConstraints.REMAINDER;
//        g.anchor = GridBagConstraints.LINE_END;
//        jp.add(modifierButton, g);
        jsp.setViewportView(jp);
        dialog.add(jsp);
        
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        //boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();

        dialog.setTitle(infosArticle.getMarqueField().getText() + " " + infosArticle.getDesigationField().getText());
        dialog.setSize(800, 700);
        dialog.setLocationRelativeTo(jsp);
        dialog.setModal(true);
        dialog.setVisible(true);
        
    }
    
    
    //suppression d'un article a partir de son id
    private void supprimerArticle(int i) {
        
        if (DBConnect.supprimerArticle(i)) {
            removeAll();
            buildGUI();
            table.setModel(DonneesTables.getDonneesArticles());
            repaint();
            revalidate();
            Notificateur.Info("<html>Article supprimé</html>");
            
        } else {
            repaint();
            revalidate();
//            Notificateur.Info("<html>Une erreur s'est produite lors de la suppression<br>"
//                    + "L'article n'a pa été supprimmé</html>");
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
    
    private class AfficherArticleListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRow() >= 0) {
                afficherArticle((int) table.getValueAt(table.getSelectedRow(), 0));
                
            } else {
                Notificateur.Info("Vous devez d'abord séléctionner un article");
            }
            
        }
        
    }
    
    private class NouvelArticleListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            ajouterNouvelArticle();
        }
    }
    
    private class SupprimmerListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if (table.getSelectedRow() >= 0) {
                
                String mes = "Etes-vous sûr de vouloir supprimer cet article.\n Cette action est irreversible";
                
                int n = JOptionPane.showConfirmDialog(null, mes);
                if (n == JOptionPane.YES_OPTION) {
                    
                    supprimerArticle((int) table.getValueAt(table.getSelectedRow(), 0));
                    
                }
                
            } else {
                Notificateur.Info("Vous devez d'abord séléctionner un article");
            }
        }
    }

//    private class RowRightClickListener implements MouseListener {
//
//        @Override
//        public void mouseReleased(MouseEvent me) {
//
//        }
//
//        @Override
//        public void mouseClicked(MouseEvent me) {
//
//            if (table.getSelectedRow() >= 0) {
//                
//                WebPopupMenu popupMenu = new WebPopupMenu();
//
//                WebMenuItem afficheritem = new WebMenuItem("Afficher/Modifier");
//                afficheritem.addActionListener(afficherArticleListener);
//                WebMenuItem nouvel = new WebMenuItem("Ajouter un nouvel article");
//                nouvel.addActionListener(nouvelArticleListener);
//                WebMenuItem suppr = new WebMenuItem("Supprimmer");
//                suppr.addActionListener(supprimmerListener);
//
//                popupMenu.add(afficheritem);
//                popupMenu.addSeparator();
//                popupMenu.add(nouvel);
//                popupMenu.addSeparator();
//                popupMenu.add(suppr);
//
//            }
//        }
//
//        @Override
//        public void mousePressed(MouseEvent me) {
//        }
//
//        @Override
//        public void mouseEntered(MouseEvent me) {
//        }
//
//        @Override
//        public void mouseExited(MouseEvent me) {
//        }
//
//    }
//    private void showPopup(){
//        
//        WebPopupMenu popupMenu = new WebPopupMenu();
//        
//        WebMenuItem afficheritem = new WebMenuItem("Afficher/Modifier");
//        afficheritem.addActionListener(afficherArticleListener);
//        WebMenuItem nouvel = new WebMenuItem("Ajouter un nouvel article");
//        nouvel.addActionListener(nouvelArticleListener);
//        WebMenuItem suppr = new WebMenuItem("Supprimmer");
//        suppr.addActionListener(supprimmerListener);
//        
//        popupMenu.add(afficheritem);
//        popupMenu.addSeparator();
//        popupMenu.add(nouvel);
//        popupMenu.addSeparator();
//        popupMenu.add(suppr);
//        
//        
//        
//        
//        popupMenu.show();
//        
//    }
}
