/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.BackOffice_Admin;

import com.alee.extended.layout.TableLayout;
import com.alee.extended.panel.CenterPanel;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;
import com.alee.utils.SwingUtils;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;
import sn.ceessay.samaBoutiq.FrontOffice_Boutiq.ChampsFocusListener;
import sn.ceessay.samaBoutiq.Classes.Constantes;
import sn.ceessay.samaBoutiq.Classes.DBConnect;
//import sn.ceessay.samaBoutiq.FrontOffice_Boutiq.BoutiqMain;

/**
 *
 * @author ceessay
 */

public class BackOfficeMain extends JFrame {

    WebTabbedPane contentPane;
    AdminArticlePanel articlePanel;
    AdminClientsPanel clientPanel;
    AdminCommandesPanel commandePanel;

    public BackOfficeMain() throws HeadlessException {
        
        buildGUI();
       
    }

    private void buildGUI() {

        contentPane = new WebTabbedPane();
        articlePanel = new AdminArticlePanel();
        clientPanel = new AdminClientsPanel();
        commandePanel = new AdminCommandesPanel();

        contentPane.add(articlePanel, "Articles");
        contentPane.add(clientPanel, "Clients");
        contentPane.add(commandePanel, "Commandes");

        setContentPane(contentPane);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocation(200, 100);

        setSize(1000, 600);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                String mes = "Voulez-vous vraiment quitter";

                int n = JOptionPane.showConfirmDialog(null, mes);
                if (n == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        setTitle("Boutiq (Back Office)");
        setIconImage(getAppIcon());
        setVisible(true);
    }
    
    private Image getAppIcon() {
        URL myurl = this.getClass().getResource("../Images/icobackO.png");

        try {
            return ImageIO.read(myurl);
        } catch (IOException ex) {
          
            return null;
        }
    }

    private static void authenticate() {
       final WebDialog dialog = new WebDialog();
        
       final WebLabel l = new WebLabel("Entrez votre login et votre mot de passe");
        l.setFont(Constantes.POLICE_CONN);
       
        

        WebLabel loginLabel = new WebLabel("Login",WebLabel.TRAILING);
        loginLabel.setFont(Constantes.POLICE_CONN);

        WebLabel passlabel = new WebLabel("Mot de passe", WebLabel.TRAILING);
        passlabel.setFont(Constantes.POLICE_CONN);

        final WebTextField login = new WebTextField();
        login.addFocusListener(new ChampsFocusListener(login));
        login.setPreferredWidth(200);
        login.setInputPrompt("Login");
        login.setFont(Constantes.POLICE_CONN);

        final WebPasswordField pass = new WebPasswordField();
        pass.addFocusListener(new ChampsFocusListener(pass));
        pass.setPreferredWidth(200);
        pass.setInputPrompt("Mot de passe");
        pass.setFont(Constantes.POLICE_CONN);
  
        
         ActionListener connListener = new ActionListener() {

           @Override
           public void actionPerformed(ActionEvent e) {
                               if(login.getText().equals("") || pass.getText().equals("")){
                    l.setText("Login et/ou mot de passe incorrect");
                    login.setBackground(Constantes.COULEUR_ERR_CHAMPS);
                    pass.setBackground(Constantes.COULEUR_ERR_CHAMPS);
                    l.setForeground(Color.RED);
                    login.setText(null);
                    pass.setText(null);
                }else{
                    String log = login.getText(), mdp =pass.getText() ;
                ResultSet rs = DBConnect.SELECT("SELECT * FROM Admin WHERE pseudo = '" + log
                        + "' AND motdepass = '" + mdp+"'");
                

                try {
                    if (rs.next()) {
                        dialog.dispose();
                        //WebLookAndFeel.setDecorateDialogs(false);
                        new BackOfficeMain();
                    }else{
                        l.setText("Login et/ou mot de passe incorrect");
                        login.setBackground(Constantes.COULEUR_ERR_CHAMPS);
                        pass.setBackground(Constantes.COULEUR_ERR_CHAMPS);
                        l.setForeground(Color.RED);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(BackOfficeMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
           }
       };

         pass.addActionListener(connListener);
         
        WebButton conButton = new WebButton("Se connecter");
        conButton.setFont(Constantes.POLICE_CONN);
        conButton.addActionListener(connListener);
       
        
        
        WebButton fermerButton = new WebButton("Quitter");
        fermerButton.setFont(Constantes.POLICE_CONN);

        fermerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        conButton.addActionListener(null);

        
        TableLayout layout = new TableLayout ( new double[][]{ { TableLayout.PREFERRED, TableLayout.FILL },
                    { TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED } } );
            layout.setHGap ( 5 );
            layout.setVGap ( 10 );
            WebPanel container = new WebPanel ( layout );
            container.setMargin ( 15, 30, 15, 30 );
            container.setOpaque ( false );

            
            
            container.add ( loginLabel, "0,0" );
            container.add ( login, "1,0" );

            container.add ( passlabel, "0,1" );
            container.add ( pass, "1,1" );
           
            
             container.add ( new CenterPanel ( new GroupPanel ( 5, conButton, fermerButton ) ), "0,2,1,2" );
            SwingUtils.equalizeComponentsWidths ( conButton, fermerButton );

        dialog.add(container);
        dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                    System.exit(0);
            }
});
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setVisible(true);
        
        

        fermerButton.requestFocus();
        
    }

    public static void main(String[] args) {
        WebLookAndFeel.install();
       // WebLookAndFeel.setDecorateFrames(true);
        //WebLookAndFeel.setDecorateDialogs(true);
        DBConnect.ON();
        //authenticate();
        new BackOfficeMain();
       
    }

}
