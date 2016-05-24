/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.FrontOffice_Boutiq;

import com.alee.extended.button.WebSplitButton;
import com.alee.extended.label.WebLinkLabel;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.TooltipWay;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.StyleConstants;
import sn.ceessay.samaBoutiq.Classes.Article;
import sn.ceessay.samaBoutiq.Classes.Client;
import sn.ceessay.samaBoutiq.Classes.Commande;
import sn.ceessay.samaBoutiq.Classes.Constantes;
import sn.ceessay.samaBoutiq.Classes.DBConnect;

/**
 *
 * @author ceessay
 *
 * cette classe represente un panel de connexion avec les champs pseudo et mot
 * de pass
 */
public class BoutiqMain extends JFrame {

    private static JFrame samaboutiq;

    private Toolkit monToolkit;
    private Dimension tailleEcran;
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel vitrinePanel;
    private JPanel leftPanel;
    private JPanel acceuilEtCategoriesPanel;
    private InscriptionPanel inscriptionPanel;
    private ConnectionPanel connectionPanel;
    private PanierPanel panierPanel = new PanierPanel();
    private JPanel userPanel = new JPanel();
    private JScrollPane mainnScrollPane;
    private WebButtonGroup group;
    private WebTextField champDeRecherche;

    public BoutiqMain() throws HeadlessException {
        samaboutiq = this;

        mainPanel = new JPanel(new GridBagLayout());
        int larg = mainPanel.getWidth();
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;

//        //reperes
//        for (int i = 0; i < 5; i++) {
//            JPanel j = new JPanel();
//            j.setPreferredSize(new Dimension(200, 10));
//            j.setBackground(Color.BLACK);
//            gbc.gridx = i;
//            mainPanel.add(j, gbc);
//        }
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 10, 20, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        ////=====================TOP=============================
        /*Partie haute de l'IHM contenant le logo, le champs de recherche et les bouton d'inscription
         et de connexion (ou le nom du clien et le bouton de deconnection si le client est connecté)*/
        JLabel logoLabel = new JLabel(getImageIcon("../Images/logolite.png"));
        logoLabel.addMouseListener(new LogoClickListener());
         TooltipManager.setTooltip ( logoLabel, "A propos", TooltipWay.down, 500 );
        
        champDeRecherche = new WebTextField();

        JButton connexionButton = new JButton("Connexion");
        connexionButton.setFont(Constantes.POLICE_MOYENNE_20);
        connexionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pageDeConnexion();
            }
        });
        JButton sinscrireButton = new JButton("Inscription");
        sinscrireButton.setFont(Constantes.POLICE_MOYENNE_20);
        sinscrireButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pageDInscription();
            }
        });

        champDeRecherche.setFont(Constantes.POLICE_MOYENNE_20);
        champDeRecherche.setPreferredSize(connexionButton.getPreferredSize());
        champDeRecherche.setColumns(20);

        champDeRecherche.setInputPrompt("Rechercher...");
        champDeRecherche.setInputPromptFont(Constantes.POLICE_MOYENNE_15);
        champDeRecherche.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (champDeRecherche.getText().length() < 2) {
                    Notificateur.Info("Vous devez saisir au moins 2 lettres pour éffectuer une recherche");
                } else {
                    Model.resultatRechercheList.clear();

                    for (Article article : Model.tousLesArticleList) {
                        if (article.getDesignation().toLowerCase().contains(champDeRecherche.getText().toLowerCase())) {
                            Model.resultatRechercheList.add(article);
                            // System.out.println("article" + article.getDesignation());
                        } else if (article.getCategorie().toLowerCase().contains(champDeRecherche.getText().toLowerCase())) {
                            Model.resultatRechercheList.add(article);
                            //System.out.println("categorie" + article.getDesignation());
                        } else if (article.getMarque().toLowerCase().contains(champDeRecherche.getText().toLowerCase())) {
                            Model.resultatRechercheList.add(article);
                            //System.out.println("categorie" + article.getDesignation());
                        }

                    }

                    if (Model.resultatRechercheList.isEmpty()) {
                        Notificateur.Info("Aucun resultat  trouvé");
                    } else {
                        mainPanel.remove(vitrinePanel);
                        repaint();
                        revalidate();
                        vitrinePanel = new VitrinePanel("resultatRecherche");
                        mainPanel.add(vitrinePanel, gbc);
                        mainPanel.revalidate();
                        mainPanel.repaint();
                        repaint();
                        revalidate();
                    }

                }

            }
        });

        userPanel = new JPanel();// jpanel contenant les bouttons d'insciption et de connexion
        userPanel.setLayout(new GridBagLayout());
        userPanel.setBackground(Constantes.COULEUR_DE_FOND);
        GridBagConstraints gbc1 = new GridBagConstraints();

        group = new WebButtonGroup(sinscrireButton, connexionButton);
        group.setBackground(Constantes.COULEUR_TRANPARENTE);
        group.setButtonsLeftRightSpacing(10);

        userPanel.add(group);

        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(Constantes.COULEUR_TRANPARENTE);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(logoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainPanel.add(champDeRecherche, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 3;
        gbc.gridy = 1;

        mainPanel.add(userPanel, gbc);

        //============================Accueil + CAtegories================================    
        acceuilEtCategoriesPanel = new JPanel();

        WebToggleButton homeButton;
        WebToggleButton ordiButton;
        WebToggleButton smartButton;
        WebToggleButton tabletButton;
        WebToggleButton phabletButton;
        WebSplitButton panierButton;

        acceuilEtCategoriesPanel.setBackground(Constantes.COULEUR_TRANPARENTE);

        homeButton = new WebToggleButton();
        homeButton.setIcon(getImageIcon("../Images/home.png"));
        homeButton.setFont(Constantes.POLICE_MOYENNE_20);
        homeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                mainPanel.remove(vitrinePanel);
                vitrinePanel = new VitrinePanel("Tous");

                repaint();
                revalidate();
                mainPanel.add(vitrinePanel, gbc);
                repaint();
                revalidate();

            }
        });
        ordiButton = new WebToggleButton("Ordinateurs");
        ordiButton.setFont(Constantes.POLICE_MOYENNE_20);
        ordiButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                mainPanel.remove(vitrinePanel);
                vitrinePanel = new VitrinePanel("ordinateurs");

                repaint();
                revalidate();
                mainPanel.add(vitrinePanel, gbc);
                repaint();
                revalidate();

            }
        });

        smartButton = new WebToggleButton("Smartphones");
        smartButton.setFont(Constantes.POLICE_MOYENNE_20);
        smartButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                mainPanel.remove(vitrinePanel);
                vitrinePanel = new VitrinePanel("smartphones");

                repaint();
                revalidate();
                mainPanel.add(vitrinePanel, gbc);
                repaint();
                revalidate();
            }
        });

        tabletButton = new WebToggleButton("Tablettes");
        tabletButton.setFont(Constantes.POLICE_MOYENNE_20);
        tabletButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.remove(vitrinePanel);
                vitrinePanel = new VitrinePanel("tablettes");

                repaint();
                revalidate();
                mainPanel.add(vitrinePanel, gbc);
                repaint();
                revalidate();

            }
        });
        phabletButton = new WebToggleButton("Phablettes");
        phabletButton.setFont(Constantes.POLICE_MOYENNE_20);
        phabletButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                mainPanel.remove(vitrinePanel);
                vitrinePanel = new VitrinePanel("phablettes");
                repaint();
                revalidate();
                mainPanel.add(vitrinePanel, gbc);
                repaint();
                revalidate();

            }
        });
        WebButtonGroup menuGroup = new WebButtonGroup(true, homeButton, ordiButton, smartButton, tabletButton, phabletButton);
        menuGroup.setButtonsLeftRightSpacing(15);
        menuGroup.setButtonsDrawFocus(true);

        panierButton = new WebSplitButton("Panier");
        panierButton.setIcon(getImageIcon("../Images/panier.png"));
        panierButton.setFont(Constantes.POLICE_MOYENNE_20);

        WebPopupMenu popupMenu = new WebPopupMenu();

        WebMenuItem voirPanier = new WebMenuItem("Voir mon panier");
        voirPanier.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pagePanier();
            }
        });

        WebMenuItem validerCmd = new WebMenuItem("Valider ma commande");
        validerCmd.addActionListener(new ValiderCommandeListener(panierPanel));

        WebMenuItem viderPanier = new WebMenuItem("Vider mon panier");
        viderPanier.addActionListener(new ViderPanierListener(panierPanel));

        popupMenu.add(voirPanier);

        popupMenu.addSeparator();
        popupMenu.add(validerCmd);
        popupMenu.addSeparator();
        popupMenu.add(viderPanier);

        panierButton.setPopupMenu(popupMenu);

        acceuilEtCategoriesPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();

        gbc2.gridx = gbc2.gridy = 0;
        acceuilEtCategoriesPanel.add(menuGroup, gbc2);

        gbc2.gridx = GridBagConstraints.RELATIVE;
        gbc2.insets = new Insets(0, 50, 0, 0);
        acceuilEtCategoriesPanel.add(panierButton, gbc2);

        //===================================================================================
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        mainPanel.add(acceuilEtCategoriesPanel, gbc);

        //============================LEFT============================
//        leftPanel = new JPanel();
//        leftPanel.setPreferredSize(new Dimension(250, 500));
//
//        gbc.gridx = 0;
//        gbc.gridy = 3;
//        gbc.gridwidth = 2;
//        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
//        gbc.gridheight = GridBagConstraints.REMAINDER;
//        mainPanel.add(leftPanel, gbc);
        //=====================Rayons : la ou les articles sont affiché=================
        vitrinePanel = new VitrinePanel("tout");

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        mainPanel.add(vitrinePanel, gbc);

        //==============================
        mainPanel.setBackground(Constantes.COULEUR_DE_FOND);
        mainnScrollPane = new JScrollPane();
        mainnScrollPane.setViewportView(mainPanel);

        monToolkit = getToolkit();
        tailleEcran = monToolkit.getScreenSize();

//        toolkit = getToolkit();
//        Dimension screensize = toolkit.getScreenSize();
//        setLocation((screensize.width - getWidth())/2, 
//            (screensize.height - getHeight())/2);
        setContentPane(mainnScrollPane);
        
//        int largeurFenetre = (int) (tailleEcran.width * (0.9));
//        int hauterFenetre = (int) (tailleEcran.height * (0.7));
        setIconImage(getImage());
        setLocation(0, 0);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                String mes = "Voulez-vous vraiment quitter";
                if (!Model.PANIER.getListeLignesDeCommande().isEmpty()) {
                    mes = mes + "\nIl y'a encore des articles dans votre panier";
                }

                int n = JOptionPane.showConfirmDialog(null, mes);
                if (n == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        setSize((int) tailleEcran.getWidth(), (int) tailleEcran.getHeight());
        setTitle("Boutiq");
        setVisible(true);

    }

    public void pageDeConnexion() {

        JPanel retourPanel = getRetourPanel ();

        connectionPanel = new ConnectionPanel();
        setContentPane(connectionPanel);

        JPanel jp = new JPanel();
        jp.setBackground(Constantes.COULEUR_DE_FOND);
        jp.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton connectionButton = new JButton("Connexion");
        connectionButton.setFont(Constantes.POLICE_MOYENNE_15);
        connectionButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (connectionPanel.verifier()) {
                    ResultSet rs, rs2;
                    int id = 0;
                    String req = "SELECT * FROM Comptes WHERE pseudo = '" + connectionPanel.getLogin() + "'AND motdepasse = '" + connectionPanel.getPasswd() + "'";

                    rs = DBConnect.SELECT(req);
                    try {
                        if (rs.next()) {
                            try {
                                id = rs.getInt(4);
                            } catch (SQLException ex) {
                                Logger.getLogger(BoutiqMain.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                rs.close();
                            }
                            try {
                                String req2 = "SELECT * FROM Clients WHERE idClient = " + id;
                                rs2 = DBConnect.SELECT(req2);

                                if (rs2.next()) {
                                    Client c = new Client(rs2.getInt(1), rs2.getString(2), rs2.getString(3),
                                            rs2.getInt(4), rs2.getString(5), rs2.getString(6), rs2.getString(7), rs2.getString(8),
                                            rs2.getString(9), rs2.getString(10), id);
                                    c.afficherClient();
                                    Model.CLIENT = c;
                                    userPanel.removeAll();

                                    JButton deconnectionButton = new JButton("Deconnexion");
                                    deconnectionButton.setFont(Constantes.POLICE_MOYENNE_15);
                                    deconnectionButton.addActionListener(new ActionListener() {

                                        @Override
                                        public void actionPerformed(ActionEvent e) {

                                            Model.CLIENT = new Client();
                                            Model.PANIER = new Commande();
                                            userPanel.removeAll();
                                            userPanel.add(group);
                                            setContentPane(mainnScrollPane);
                                            repaint();
                                            revalidate();
                                            Notificateur.Info("Vous êtes maintenant déconnecté");
                                        }
                                    });

                                    JLabel bienvenuLabel = new JLabel(c.getPrenom() + " " + c.getNom() + "  ", FlowLayout.TRAILING);
                                    Notificateur.Info("Vous êtes connecté");
                                    bienvenuLabel.setFont(Constantes.POLICE_MOYENNE_25);
                                    userPanel.add(bienvenuLabel);
                                    userPanel.add(deconnectionButton);
                                    setContentPane(mainnScrollPane);
                                    repaint();
                                    revalidate();
                                }
                            } catch (SQLException ex) {

                            }
                        } else {
                            Notificateur.Info("Login et\\ou mot de passe incorrect");
                        }
                    } catch (SQLException ex) {

                    }

                } else {
                    Notificateur.Info("Entrez votre login et votre mot de passe");
                }
            }
        });

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(20, 0, 0, 0);

        jp.add(retourPanel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        jp.add(connectionPanel, gbc);

        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        jp.add(connectionButton, gbc);

        setContentPane(jp);
        repaint();
        revalidate();
    }

    public void pageDInscription() {

        JPanel retourPanel = getRetourPanel();
        inscriptionPanel = new InscriptionPanel();

        JPanel jp = new JPanel();
        jp.setBackground(Constantes.COULEUR_DE_FOND);
        jp.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton sinscrireButton = new JButton("S'inscrire");
        sinscrireButton.setFont(Constantes.POLICE_MOYENNE_15);
        sinscrireButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean v = inscriptionPanel.validerInsccription();
                if (v) {

                    ResultSet r = DBConnect.SELECT("SELECT * FROM Comptes WHERE pseudo = '"
                            + inscriptionPanel.getInfoClients().get(9) + "'");

                    try {
                        if (r.next()) {
                            Notificateur.Erreur("Ce pseudo n'est plus disponilble. Choisissez en un autre");
                            inscriptionPanel.clear();
                            return;
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(BoutiqMain.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    ArrayList<String> a = inscriptionPanel.getInfoClients();
                    boolean b = DBConnect.inscrireClient(a);
//                    System.out.println(b);
                    if (b) {
                        Notificateur.InfoInscription();
                        pageDeConnexion();
                    } else {
                        Notificateur.Erreur("Echec de l'inscription.");

                    }
                }
            }
        });

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(20, 0, 0, 0);

        jp.add(retourPanel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        jp.add(inscriptionPanel, gbc);

        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        jp.add(sinscrireButton, gbc);

        JScrollPane jsp = new JScrollPane();
        jsp.setViewportView(jp);
        setContentPane(jsp);
        repaint();
        revalidate();
    }

    public void pagePanier() {
        JPanel retourPanel = getRetourPanel();
        panierPanel = new PanierPanel();

        JPanel jp = new JPanel();
        jp.setBackground(Constantes.COULEUR_DE_FOND);
        jp.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton viderPAnierButton = new JButton("Vider mon panier");
        viderPAnierButton.setFont(Constantes.POLICE_MOYENNE_15);
        viderPAnierButton.addActionListener(new ViderPanierListener(panierPanel));
        JButton validercmdButton = new JButton("Valider ma commande");
        validercmdButton.setFont(Constantes.POLICE_MOYENNE_15);
        validercmdButton.addActionListener(new ValiderCommandeListener(panierPanel));
        if (Model.PANIER.getListeLignesDeCommande().isEmpty()) {
            viderPAnierButton.setEnabled(false);
        }
        if (Model.PANIER.getListeLignesDeCommande().isEmpty()) {
            validercmdButton.setEnabled(false);
        }

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(20, 0, 0, 0);

        jp.add(retourPanel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        jp.add(panierPanel, gbc);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        jp.add(viderPAnierButton, gbc);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        jp.add(validercmdButton, gbc);

        JScrollPane jsp = new JScrollPane();
        jsp.setViewportView(jp);
        setContentPane(jsp);
        repaint();
        revalidate();

    }

    public JPanel getRetourPanel() {
        JPanel jp = new JPanel();
        jp.setBackground(Constantes.COULEUR_DE_FOND);
        jp.setLayout(new FlowLayout(FlowLayout.LEADING));

        JButton retourButton = new JButton("Revenir à la boutique");
        retourButton.setFont(Constantes.POLICE_MOYENNE_25);
        retourButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(mainnScrollPane);
                rafraichir();
            }
        });

        jp.add(retourButton);
        return jp;
    }

    public static void rafraichir() {
        samaboutiq.repaint();
        samaboutiq.revalidate();

    }

    public void setVitrinePanel(String cle) {
        vitrinePanel = new VitrinePanel(cle);
        repaint();
        revalidate();
    }

    
    private ImageIcon getImageIcon(String url) {
        try {
            URL myurl = this.getClass().getResource(url);
            Toolkit tk = this.getToolkit();
            return new ImageIcon(tk.getImage(myurl));
        } catch (Exception e) {
            return null;
        }

    }

    private Image getImage() {
        URL myurl = this.getClass().getResource("../Images/iconApp.png");

        try {
            return ImageIO.read(myurl);
        } catch (IOException ex) {
            Logger.getLogger(BoutiqMain.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }



    private class LogoClickListener extends MouseAdapter {

        private final WebPopOver popup;

        public LogoClickListener() {
            popup = new WebPopOver();

            JLabel logo = new JLabel();
            JLabel iconLabel = new JLabel();
            WebLabel label = new WebLabel("Conçu et developpé par: ");
            label.setFont(Constantes.POLICE_CONN);
            label.setFontStyle(Font.BOLD);
            WebLabel auteurLabel = new WebLabel("Mohamed CISSE");
            auteurLabel.setFont(Constantes.POLICE_CONN);
            WebLabel label1 = new WebLabel("Mail: ");
            label1.setFont(Constantes.POLICE_CONN);
            label1.setFontStyle(Font.BOLD);
            WebLinkLabel maiLabel = new WebLinkLabel("mceessay@gmail.com");
            maiLabel.setFont(Constantes.POLICE_CONN);
            WebLabel label2 = new WebLabel("Twitter: ");
            label2.setFont(Constantes.POLICE_CONN);
            label2.setFontStyle(Font.BOLD);
            WebLinkLabel twitterlLabel = new WebLinkLabel("@cisseCeessay");
            twitterlLabel.setFont(Constantes.POLICE_CONN);
            WebLabel label3 = new WebLabel("Tel: ");
            label3.setFont(Constantes.POLICE_CONN);
            label3.setFontStyle(Font.BOLD);
            WebLabel tellLabel = new WebLabel("+221 78 213 91 63");
            tellLabel.setFont(Constantes.POLICE_CONN);
            
            //twitterlLabel.setLink("www.twitter.com");

            iconLabel.setIcon(getImageIcon("../Images/iconApp.png"));
          logo.setIcon(getImageIcon("../Images/logosmall.png"));
            logo.setText("Boutiq");
            logo.setFont(Constantes.POLICE_DE_TITRE);

            WebPanel p = new WebPanel();

            p.setLayout(new GridBagLayout());
            p.setBackground(Color.WHITE);

            GridBagConstraints g = new GridBagConstraints();
            
            
            g.anchor = GridBagConstraints.CENTER;
            g.gridy =0;
            g.gridx = 0;
            p.add(iconLabel, g);
            
           // g.insets = new Insets(10, 0, 15, 0);
            g.anchor = GridBagConstraints.CENTER;
            
            
            g.gridy =0;
            g.gridx = 1;
            g.gridwidth = GridBagConstraints.REMAINDER;
            p.add(logo, g);
            
            
            g.ipadx = 5;
            
            g.gridwidth =1;
            g.insets = new Insets(5, 0, 5, 0);
            
            
            
            
            g.gridy=1;
            g.gridx=0;
            g.anchor = GridBagConstraints.EAST;
            p.add(label,g);
           
            
             g.gridy=1;
            g.gridx=1;
            g.anchor = GridBagConstraints.WEST;
            p.add(auteurLabel,g);
           
            
            g.gridy=2;
            g.gridx=0;
            g.anchor = GridBagConstraints.EAST;
            p.add(label1, g);
            
            g.gridy=2;
            g.gridx=1;
            g.anchor = GridBagConstraints.WEST;
            p.add(maiLabel, g);
            
            g.gridy=3;
            g.gridx=0;
            g.anchor = GridBagConstraints.EAST;
            p.add(label2, g);
            
            g.gridy=3;
            g.gridx=1;
            g.anchor = GridBagConstraints.WEST;
            p.add(twitterlLabel, g);
            
            g.gridy=4;
            g.gridx=0;
            g.anchor = GridBagConstraints.EAST;
            p.add(label3, g);
            
            
            g.gridy=4;
            g.gridx=1;
            g.anchor = GridBagConstraints.WEST;
            p.add(tellLabel, g);

            popup.setContentBackground(Constantes.COULEUR_DE_FOND);
            popup.add(p);
            popup.setCloseOnFocusLoss(true);
            popup.setSize(550, 350);

            popup.setLocationRelativeTo(null);

        }

        public void mouseClicked(MouseEvent e) {
            popup.show();
        }

       

    }

    public static void main(String[] args) {

        //installation du look&feel
        WebLookAndFeel.install();
     
        
     // WebLookAndFeel.setDecorateFrames(true);
     //  WebLookAndFeel.setDecorateDialogs(true);

        DBConnect.ON();
        Model.init();
        new BoutiqMain();

    }

}
