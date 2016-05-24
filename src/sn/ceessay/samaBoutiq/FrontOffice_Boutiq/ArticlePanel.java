
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.FrontOffice_Boutiq;

//~--- non-JDK imports --------------------------------------------------------
import com.alee.extended.panel.WebButtonGroup;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.spinner.WebSpinner;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Painter;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import org.jdesktop.swingx.border.DropShadowBorder;
import sn.ceessay.samaBoutiq.BackOffice_Admin.ImageUtils;
import sn.ceessay.samaBoutiq.Classes.Article;
import sn.ceessay.samaBoutiq.Classes.Constantes;
import sn.ceessay.samaBoutiq.Classes.LigneDeCommande;

/**
 *
 * @author ceessay
 */

/*
unité graphique qui affiche l'image d'un article , sa designation, son prix
*/
public class ArticlePanel extends WebPanel {

    // private final Box container;
    private final JPanel imagePanel;
    private final JPanel boutonPanel;
    private final JPanel nomPanel;
    private final JPanel prixPanel;
    private final JButton ajouterAuPanierButton;
    private final JLabel imageLabel;
    private final Article article;
    private final WebButton infosButton;

    public ArticlePanel(Article arti) {
        super();
        this.article = arti;

        // container = Box.createVerticalBox();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        imagePanel = new JPanel();
        imagePanel.setBackground(Color.WHITE);
        nomPanel = new JPanel();
        nomPanel.setBackground(Color.WHITE);
        prixPanel = new JPanel();
        prixPanel.setBackground(Color.WHITE);
        boutonPanel = new JPanel();
        boutonPanel.setBackground(Color.WHITE);

        
        ajouterAuPanierButton = new JButton("Ajouter au panier");
        ajouterAuPanierButton.setFont(Constantes.POLICE_BOUTON);
        infosButton = new WebButton(getInfoIcon());

        ajouterAuPanierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final WebDialog dialog = new WebDialog();

                dialog.setTitle("Ajouter au panier");
                dialog.setModal(true);
                dialog.setLayout(new GridLayout(2, 1));
                dialog.setLocationRelativeTo(null);

                int dispo = article.getQuatiteDispo();
                JLabel nbUnites = new JLabel("Nombre d'unites");
                final WebSpinner spinner = new WebSpinner(new SpinnerNumberModel(1, 1, 5, 1));
                final WebButton validerButton = new WebButton("Valider");
                final WebButton annulerButton = new WebButton("Annuler");
                JPanel jp = new JPanel();

                jp.add(validerButton);
                jp.add(annulerButton);

                JPanel jp2 = new JPanel();

                jp2.add(nbUnites);
                jp2.add(spinner);
                annulerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                    }
                });
                validerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        LigneDeCommande ldc = new LigneDeCommande(article);
                        int n;

                        n = (Integer) spinner.getModel().getValue();
                        ldc.setNombreUnites(n);
                        boolean a = Model.PANIER.ajouterLigneDeCommande(ldc);
                        //Model.PANIER.afficherCommande();
                        dialog.dispose();
                        if (a) {
                            Notificateur.Info("<html>Article ajouté à votre panier.<br>" + Model.PANIER.getNbreArticles() + " article(s) dans votre panier</html>");
                        }
                    }
                });
                dialog.add(jp2);
                dialog.add(jp);
                dialog.setSize(300, 100);
                dialog.setResizable(false);
                dialog.setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        infosButton.addMouseListener(voirPlus);

        Blob b = null;

        b = article.getImageBlob();
        imageLabel = new JLabel();
        imageLabel.setIcon(creerImagelabel(b));
        imagePanel.add(imageLabel);

        JLabel nomJLabel = new JLabel(article.getMarque() + " " + article.getDesignation(), JLabel.CENTER);

        nomPanel.add(new JPanel().add(nomJLabel));
        nomJLabel.setFont(Constantes.POLICE_PRIX);

        JLabel prixLabel = new JLabel(article.getPrix().toString() + " FCFA", JLabel.CENTER);

        prixLabel.setFont(Constantes.POLICE_MOYENNE_25);
        prixLabel.setForeground(Constantes.COULEUR_PRIX);
        prixPanel.add(prixLabel);

        WebButtonGroup buttonGroup;
        if (article.getQuatiteDispo() != 0) {
            buttonGroup = new WebButtonGroup(ajouterAuPanierButton, infosButton);
        } else {
            JLabel jl = new JLabel("Epuisé  ");
            jl.setBackground(Color.red);

            jl.setFont(Constantes.POLICE_PRIX);
            buttonGroup = new WebButtonGroup(jl, infosButton);
        }

        boutonPanel.add(buttonGroup);
        add(imagePanel);
        add(nomPanel);
        add(prixPanel);
        add(boutonPanel);

        setPreferredSize(new Dimension(300, 350));
        setMargin(20);

        shadow();
        // setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        // add(container);
    }

    private ImageIcon creerImagelabel(Blob b) {
        InputStream is = null;
        BufferedImage imag = null;
        try {
            is = b.getBinaryStream(1, b.length());
            imag = ImageIO.read(is);
        } catch (SQLException ex) {
            Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArticlePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        imag = ImageUtils.redimensionnerImage(imag, 210, 193, true);
        ImageIcon ico = new ImageIcon(imag);
        JLabel j = new JLabel(ico);

        return ico;

    }

    // cette methode ajoute une ombre sur le pannel
    public void shadow() {
        DropShadowBorder shadow = new DropShadowBorder();
       // shadow.setCornerSize(20);
        shadow.setShadowSize(4);
        
        shadow.setShadowColor(Color.BLACK);
        shadow.setShowLeftShadow(true);
        shadow.setShowRightShadow(true);
        shadow.setShowBottomShadow(true);
        shadow.setShowTopShadow(true);
        this.setBorder(shadow);
    }

    private MouseAdapter voirPlus = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            WebPopOver popup = new WebPopOver();
            popup.add(getDetailPanel());
            popup.setCloseOnFocusLoss(true);
            popup.setSize(750, 500);

            popup.show();
            popup.setLocationRelativeTo(null);
        }
    };

    private ImageIcon getInfoIcon() {
        try {
            URL myurl = this.getClass().getResource("../Images/infos.png");
            Toolkit tk = this.getToolkit();
            return new ImageIcon(tk.getImage(myurl));
        } catch (Exception e) {
            Logger.getLogger(ArticlePanel.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    //methode retournant un panel contenant les details sur les caracteristiques de l'article
    private JPanel getDetailPanel() {

        WebPanel p = new WebPanel(new GridBagLayout());
        //p.setBackground(Constantes.COULEUR_DE_FOND_TOP);
        GridBagConstraints gbc = new GridBagConstraints();

        p.setBackground(Color.WHITE);

        gbc.anchor = GridBagConstraints.WEST;

        //nom
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        WebLabel nomLabel = new WebLabel(article.getMarque() + " " + article.getDesignation() + " - " + article.getCategorie());
        nomLabel.setFont(Constantes.POLICE_MOYENNE_20);

        p.add(nomLabel, gbc);

        //prix
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);
        WebLabel prixLabel = new WebLabel(String.valueOf(article.getPrix()) + " FCFA");
        prixLabel.setFont(Constantes.POLICE_MOYENNE_25);
        prixLabel.setForeground(Constantes.COULEUR_PRIX);
        p.add(prixLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        //OS
        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel label = new JLabel("OS :");
        label.setFont(Constantes.POLICE_MOYENNE_15);
        p.add(label, gbc);

        gbc.gridy = 2;
        gbc.gridx = 1;
        WebLabel seLabel = new WebLabel(article.getCarArticle().getOs());
        seLabel.setFont(Constantes.POLICE_MOYENNE_15);
        p.add(seLabel, gbc);

        //cpu
        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel label1 = new JLabel("Processeur :");
        label1.setFont(Constantes.POLICE_MOYENNE_15);
        p.add(label1, gbc);

        gbc.gridy = 3;
        gbc.gridx = 1;
        WebLabel cpuLabel = new WebLabel(article.getCarArticle().getMarqueCPU() + " " + article.getCarArticle().getCadenceCPU() + " Ghz");
        cpuLabel.setFont(Constantes.POLICE_MOYENNE_15);
        p.add(cpuLabel, gbc);

        //ram
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridx = 0;
        JLabel label2 = new JLabel("RAM : ");
        label2.setFont(Constantes.POLICE_MOYENNE_15);
        p.add(label2, gbc);

        //gbc.gridy =4;
        gbc.gridx = 1;
        WebLabel ramlLabel = new WebLabel(String.valueOf(article.getCarArticle().getTailleRam()) + " Go");
        ramlLabel.setFont(Constantes.POLICE_MOYENNE_15);
        p.add(ramlLabel, gbc);

        //dd
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridx = 0;
        JLabel label3 = new JLabel("Stockage : ");
        label3.setFont(Constantes.POLICE_MOYENNE_15);
        p.add(label3, gbc);

        // gbc.gridy =5;
        WebLabel ddLabel = new WebLabel(String.valueOf(String.valueOf(article.getCarArticle().getTaillDD()) + " Go"));
        ddLabel.setFont(Constantes.POLICE_MOYENNE_15);
        gbc.gridx = 1;
        p.add(ddLabel, gbc);

        //ecran
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridx = 0;

        JLabel label4 = new JLabel("Ecran : ");
        label4.setFont(Constantes.POLICE_MOYENNE_15);
        p.add(label4, gbc);

        //  gbc.gridy =6;
        gbc.gridx = 1;
        WebLabel tailleEcrLabel = new WebLabel(article.getCarArticle().getTailleEcran() + "  Pouce(s)");
        tailleEcrLabel.setFont(Constantes.POLICE_MOYENNE_15);
        p.add(tailleEcrLabel, gbc);

        //wifi
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridx = 0;
        JLabel label5 = new JLabel("Wifi :");
        label5.setFont(Constantes.POLICE_MOYENNE_15);
        p.add(label5, gbc);

        // gbc.gridy =7;
        gbc.gridx = 1;
        WebLabel wiLabel = new WebLabel(article.getCarArticle().isWifi() ? "Oui" : "Non");
        wiLabel.setFont(Constantes.POLICE_MOYENNE_15);
        p.add(wiLabel, gbc);

        //bluetooth
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridx = 0;
        JLabel label6 = new JLabel("Bluetooth:");
        label6.setFont(Constantes.POLICE_MOYENNE_15);
        p.add(label6, gbc);

        // gbc.gridy =7;
        gbc.gridx = 1;
        WebLabel blueLabel = new WebLabel(article.getCarArticle().isBluetooth() ? "Oui" : "Non");
        blueLabel.setFont(Constantes.POLICE_MOYENNE_15);
        p.add(blueLabel, gbc);

        if (article.getCategorie().equalsIgnoreCase("smartphone") || article.getCategorie().equalsIgnoreCase("tablette") || article.getCategorie().equalsIgnoreCase("phablette")) {
            //camera
            gbc.gridy = GridBagConstraints.RELATIVE;
            gbc.gridx = 0;
            JLabel label7 = new JLabel("Camera: ");
            label7.setFont(Constantes.POLICE_MOYENNE_15);
            p.add(label7, gbc);

            gbc.gridx = 1;
            WebLabel camLabel = new WebLabel(article.getCarArticle().isCamera() ? String.valueOf(article.getCarArticle().getResolutionCam()) + " Mpixel(s)" : "Non");
            camLabel.setFont(Constantes.POLICE_MOYENNE_15);
            p.add(camLabel, gbc);
        } else {

        }
        //webcam

        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridx = 0;
        JLabel label8 = new JLabel("Webcam");

        if (article.getCategorie().equalsIgnoreCase("smartphone") || article.getCategorie().equalsIgnoreCase("tablette") || article.getCategorie().equalsIgnoreCase("phablette")) {
            label8 = new JLabel("Caméra frontale: ");
        }

        label8.setFont(Constantes.POLICE_MOYENNE_15);
        p.add(label8, gbc);

        gbc.gridx = 1;
        WebLabel webcamLabel = new WebLabel(article.getCarArticle().isWebcam() ? article.getCarArticle().getResolutionWeb() + " Mpixel(s)" : "Non");
        webcamLabel.setFont(Constantes.POLICE_MOYENNE_15);
        p.add(webcamLabel, gbc);

        return p;
    }
}



