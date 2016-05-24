
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.FrontOffice_Boutiq;

//~--- non-JDK imports --------------------------------------------------------

import sn.ceessay.samaBoutiq.Classes.DBConnect;

//~--- JDK imports ------------------------------------------------------------

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author ceessay
 */
public class ValiderCommandeListener implements ActionListener {
    private final PanierPanel p;

    public ValiderCommandeListener(PanierPanel p) {
        this.p = p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Model.PANIER.getListeLignesDeCommande().isEmpty()) {
            Notificateur.Erreur("Votre panier est vide");

            return;
        }

        if (Model.CLIENT.getIdClient() == null) {
            Notificateur.Erreur("Vous devez d'abord vous connecter pour valider votre commande");
        } else {
            if (DBConnect.enregistrerCommande()) {
                Notificateur.infoCommande();
                Model.PANIER.getListeLignesDeCommande().clear();
                Model.PANIER.setValeurCmd();
            }

            this.p.build();
        }
    }
}


