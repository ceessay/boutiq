
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.FrontOffice_Boutiq;

//~--- non-JDK imports --------------------------------------------------------
import com.alee.extended.panel.WebButtonGroup;
import com.alee.extended.panel.WebComponentPanel;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.spinner.WebSpinner;
import com.alee.laf.tooltip.WebToolTip;

import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.TooltipWay;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import sn.ceessay.samaBoutiq.Classes.Constantes;
import sn.ceessay.samaBoutiq.Classes.LigneDeCommande;

/**
 *
 * @author ceessay
 */
public class PanierPanel extends JPanel {

    JLabel titreLabel;
    JLabel totalLabel;
    private SpinnerNumberModel snm;

    public PanierPanel() {
        build();
    }

    public void build() {
        this.removeAll();
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        Font font = new Font("Raleway Thin", Font.PLAIN, 50);

        titreLabel = new JLabel("Panier");
        titreLabel.setFont(font);
        font = new Font("Raleway Thin", Font.PLAIN, 17);

        WebComponentPanel componentPanel = new WebComponentPanel();

        componentPanel.setReorderingAllowed(false);
        componentPanel.setBackground(Constantes.COULEUR_DE_FOND);

        JPanel entetePanel = new JPanel();

        entetePanel.setLayout(new GridLayout(1, 4, 10, 10));

        JLabel l = new JLabel("Article(s)");

        l.setPreferredSize(new Dimension(200, 30));
        l.setFont(font.deriveFont(Font.BOLD));
        entetePanel.add(l);
        l = new JLabel("Nombre d'unités");
        l.setFont(font.deriveFont(Font.BOLD));;
        entetePanel.add(l);
        l = new JLabel("Prix Unitaire (FCFA)");
        l.setFont(font.deriveFont(Font.BOLD));
        entetePanel.add(l);
        l = new JLabel("Valeur (FCFA)");
        l.setFont(font.deriveFont(Font.BOLD));
        entetePanel.setBackground(Constantes.COULEUR_DE_FOND);
        entetePanel.add(l);

        JPanel conten = new JPanel(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();

        gbc1.gridy = gbc1.gridx = 0;
        gbc1.anchor = GridBagConstraints.LINE_START;

      //  ArrayList <LigneDeCommande> a = Model.PANIER.getListeLignesDeCommande();

        if (Model.PANIER.getListeLignesDeCommande().isEmpty()) {
            JLabel paniervideLabel = new JLabel("Votre panier est vide!", JLabel.CENTER);

            paniervideLabel.setFont(font.deriveFont(Font.BOLD));
            paniervideLabel.setBackground(Constantes.COULEUR_DE_FOND);

            JPanel jpa = new JPanel();

            jpa.add(paniervideLabel);
            jpa.setBackground(Constantes.COULEUR_DE_FOND);
            jpa.setPreferredSize(new Dimension(500, 200));
            gbc.gridy = 1;
            add(jpa, gbc);
        } else {
            WebComponentPanel wcp = new WebComponentPanel();

            wcp.addElement(entetePanel);
            conten.add(wcp, gbc1);

            for (LigneDeCommande ldc : Model.PANIER.getListeLignesDeCommande()) {
                JPanel j = new JPanel();

                j.setLayout(new GridLayout(1, 4, 10, 10));

                JLabel label = new JLabel();

                label.setPreferredSize(new Dimension(200, 30));
                label.setBackground(Constantes.COULEUR_DE_FOND);
                label.setText(ldc.getArticle().getDesignation());
                label.setFont(font);
                j.add(label);

                int n = ldc.getNombreUnites();

                snm = new SpinnerNumberModel(n, 1, 5, 1);

                WebSpinner spinner = new WebSpinner(snm);

                spinner.addChangeListener(new SpinnerListener(spinner, ldc, this));
                label = new JLabel(String.valueOf(ldc.getNombreUnites()));
                label.setPreferredSize(new Dimension(200, 30));
                label.setBackground(Constantes.COULEUR_DE_FOND);
                label.setFont(font);

                JButton enleverArticleButton = new JButton();
                enleverArticleButton.setIcon(getlogoTrash());
                TooltipManager.setTooltip(enleverArticleButton, "Retirer du panier", TooltipWay.trailing, 500);

                enleverArticleButton.addActionListener(new EnleverProduitListerner(ldc, this));

                WebButtonGroup p = new WebButtonGroup(spinner, enleverArticleButton);

                j.add(p);
                label = new JLabel(String.valueOf(ldc.getPrixUnitaire()));
                label.setPreferredSize(new Dimension(200, 30));
                label.setBackground(Constantes.COULEUR_DE_FOND);
                label.setFont(font);
                j.add(label);

                label = new JLabel(String.valueOf(ldc.getValeur()));
                label.setPreferredSize(new Dimension(200, 30));
                label.setBackground(Constantes.COULEUR_DE_FOND);
                label.setFont(font);
                j.add(label);
                j.setBackground(Constantes.COULEUR_DE_FOND);
                componentPanel.addElement(j);
                componentPanel.setShowReorderGrippers(true);
                gbc1.gridy = 1;
                conten.add(componentPanel, gbc1);
            }
        }

        Model.PANIER.setValeurCmd();
        totalLabel = new JLabel("Total:  " + Model.PANIER.getValeurCmd() + "  FCFA");
        totalLabel.setFont(Constantes.POLICE_PPIX_TOTAL);
        totalLabel.setForeground(Color.red);
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.gridx = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(titreLabel, gbc);
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(conten, gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(totalLabel, gbc);
        setVisible(true);
        setBackground(Constantes.COULEUR_DE_FOND);
        this.repaint();
        this.revalidate();
    }

    private ImageIcon getlogoTrash() {
        try {
            URL myurl = this.getClass().getResource("../Images/remove.png");
            Toolkit tk = this.getToolkit();
            return new ImageIcon(tk.getImage(myurl));
        } catch (Exception e) {
            return null;
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
