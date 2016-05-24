
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.FrontOffice_Boutiq;

//~--- non-JDK imports --------------------------------------------------------

import com.alee.laf.spinner.WebSpinner;

import sn.ceessay.samaBoutiq.Classes.LigneDeCommande;

//~--- JDK imports ------------------------------------------------------------

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author ceessay
 */
public class SpinnerListener implements ChangeListener {
    private WebSpinner      spinner;
    private LigneDeCommande ldc;
    private PanierPanel     panierPanel;

    public SpinnerListener(WebSpinner s, LigneDeCommande ldc, PanierPanel p) {
        this.spinner     = s;
        this.ldc         = ldc;
        this.panierPanel = p;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int n;

        n = (Integer) spinner.getModel().getValue();
        ldc.setNombreUnites(n);
        Model.PANIER.setValeurCmd();
        panierPanel.build();
        Model.PANIER.afficherCommande();
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
