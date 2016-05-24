
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.FrontOffice_Boutiq;

//~--- JDK imports ------------------------------------------------------------

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 *
 * @author ceessay
 */
public class ViderPanierListener implements ActionListener {
    private PanierPanel pp;

    public ViderPanierListener(PanierPanel pp) {
        this.pp = pp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Model.PANIER.getListeLignesDeCommande().isEmpty()) {
            Notificateur.Info("Votre panier est déja vide");
        } else {
            String[] options = new String[] { "Vider le panier", "Annuler" };
            int      choix   = JOptionPane.showOptionDialog(null, "Etes-vous sûr de vouloir vider le panier ?",    // String message,
                "Vider le du panier ?",             // String title
                JOptionPane.YES_NO_OPTION,          // int type d'option
                JOptionPane.INFORMATION_MESSAGE,    // int message
                null,                               // icone,
                options,                            // Object[] options,
                options[1]);                        // Object initialValue

            if (choix == 0) {
                Model.PANIER.getListeLignesDeCommande().clear();
                pp.removeAll();
                pp.build();
                Notificateur.Info("Votre panier est désormais vide");
            } else {}
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
