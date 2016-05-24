package sn.ceessay.samaBoutiq.FrontOffice_Boutiq;


//package sn.ceessay.samaboutiq;
//
//
//import com.alee.extended.button.WebSplitButton;
//import com.alee.extended.painter.Painter;
//import com.alee.extended.panel.WebButtonGroup;
//import com.alee.laf.button.WebButtonUI;
//import com.alee.laf.button.WebToggleButton;
//import com.alee.laf.menu.WebMenu;
//import com.alee.laf.menu.WebMenuItem;
//import com.alee.laf.menu.WebPopupMenu;
//import java.awt.Color;
//import java.awt.FlowLayout;
//import java.awt.Font;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.Insets;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import javax.swing.BoxLayout;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.border.EmptyBorder;
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
///**
// *
// * @author ceessay
// */
//public class NavigationPanel extends JPanel{
//    
//    WebToggleButton ordiPanel;
//    WebToggleButton smartPanel;
//    WebToggleButton tabletPanel;
//    WebToggleButton phabletPanel;
//    WebSplitButton panierButton; 
//    Font f = Constants.POLICE_MOYENNE_20;
//
//    
//    public NavigationPanel() {
//        
//        setBackground(Constants.COULEUR_TRANPARENTE);
//        
//        ordiPanel = new WebToggleButton("Ordinateurs");
//        ordiPanel.setFont(f);
//        ordiPanel.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                
//                    SamaBoutiq.setVitrinePanel("ordinateurs");
//        
//            }
//        });
//        
//
//        smartPanel = new WebToggleButton("Smartphones");
//        smartPanel.setFont(f);
//        smartPanel.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                    SamaBoutiq.setVitrinePanel("smartphones");
//               
//            }
//        });
//        
//        
//        tabletPanel = new WebToggleButton("Tablettes");
//        tabletPanel.setFont(f);
//        tabletPanel.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                    SamaBoutiq.setVitrinePanel("tablettes");
//             
//            }
//        });
//        phabletPanel = new WebToggleButton("Phablettes");
//        phabletPanel.setFont(f);
//        phabletPanel.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                    SamaBoutiq.setVitrinePanel("phablettes");
//               
//            }
//        });
//        WebButtonGroup menuGroup = new WebButtonGroup(true, ordiPanel, smartPanel, tabletPanel, phabletPanel);
//        menuGroup.setButtonsLeftRightSpacing(15);
//        menuGroup.setButtonsDrawFocus(true);
//        
//         panierButton = new WebSplitButton("Panier");
//         panierButton.setIcon(new ImageIcon("shopping_basket green.png"));
//         panierButton.setFont(f);
//         
//         WebPopupMenu popupMenu = new WebPopupMenu();
//         
//         
//         WebMenuItem voirPanier = new WebMenuItem("Voir mon panier");
//         voirPanier.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new PanierFrame();
//            }
//        });
//         
//         WebMenuItem validerCmd = new WebMenuItem("Valider ma commande");
//         validerCmd.addActionListener(new ValiderCommandeListener());
//         
//         
//         WebMenuItem viderPanier = new WebMenuItem("Vider mon panier");
//         viderPanier.addActionListener(new ViderPanierListener());
//         
//         
//         popupMenu.add(voirPanier);
//         
//         popupMenu.addSeparator();
//         popupMenu.add(validerCmd);
//         popupMenu.addSeparator();
//         popupMenu.add(viderPanier);
//         panierButton.setPopupMenu(popupMenu);
//        
//        setLayout(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        
//        gbc.gridx = gbc.gridy = 0;
//        add(menuGroup, gbc);
//        
//        gbc.gridx =GridBagConstraints.RELATIVE;
//        gbc.insets = new Insets(0, 50, 0, 0);
//        add(panierButton,gbc);
//        
//       
//
//    }
//    
//    
//    
//}


//~ Formatted by Jindent --- http://www.jindent.com
