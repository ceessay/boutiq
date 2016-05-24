
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.FrontOffice_Boutiq;

//~--- non-JDK imports --------------------------------------------------------

import sn.ceessay.samaBoutiq.Classes.Article;

//~--- JDK imports ------------------------------------------------------------

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

/**
 *
 * @author ceessay
 */
public class RechercheListener implements ActionListener {
    ArrayList<Article> resutList;
    String             req;

    public RechercheListener(String string) {
        this.req = string.toLowerCase();
        System.out.println(req);
        resutList = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

//      if(req.length()<3)
//          Notificateur.Info("Vous devez saisir au moins lettre pour effectuer un recherche");
//      else{
        Rechercher();

        // }
    }

    private void Rechercher() {
        String regex = ".*" + req + ".*";

        System.out.println(req);

        for (Article article : Model.tousLesArticleList) {
            if (article.getDesignation().toLowerCase().matches(regex)) {
                resutList.add(article);
                System.out.println("Trouvé =>" + article.getDesignation());
            }
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
