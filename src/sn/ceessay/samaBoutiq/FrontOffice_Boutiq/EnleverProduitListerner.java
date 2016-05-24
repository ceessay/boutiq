
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.FrontOffice_Boutiq;

//~--- non-JDK imports --------------------------------------------------------

import sn.ceessay.samaBoutiq.Classes.LigneDeCommande;

//~--- JDK imports ------------------------------------------------------------

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author ceessay
 */
public class EnleverProduitListerner implements ActionListener {
    private LigneDeCommande ldc;
    private PanierPanel     j;

    public EnleverProduitListerner(LigneDeCommande ldc, PanierPanel j) {
        this.ldc = ldc;
        this.j   = j;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (LigneDeCommande l : Model.PANIER.getListeLignesDeCommande()) {
            if (l.getArticle().compareTo(ldc.getArticle()) == 0) {
                Model.PANIER.retirerLigneDeCommande(ldc);
                Model.PANIER.afficherCommande();

                break;
            }
        }

        j.build();
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
