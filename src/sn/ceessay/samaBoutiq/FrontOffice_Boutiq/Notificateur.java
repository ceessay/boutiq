
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.FrontOffice_Boutiq;

//~--- non-JDK imports --------------------------------------------------------


//~--- JDK imports ------------------------------------------------------------

import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.WebNotificationPopup;
import javax.swing.JLabel;

/**
 *
 * @author ceessay
 */
public class Notificateur {
    public static void Info(String txt) {
         WebNotificationPopup notificationPopup = new WebNotificationPopup();

        notificationPopup.setDisplayTime(5000);
        notificationPopup.setIcon(NotificationIcon.information);
        notificationPopup.setContent(new JLabel(txt));
        NotificationManager.showNotification(notificationPopup);
        NotificationManager.showNotification(notificationPopup);
        
    }

    public static void Erreur(String txt) {
        final WebNotificationPopup notificationPopup = new WebNotificationPopup();

        notificationPopup.setDisplayTime(5000);
        notificationPopup.setIcon(NotificationIcon.error);
        notificationPopup.setContent(new JLabel(txt));
        NotificationManager.showNotification(notificationPopup);
    }

    public static void InfoInscription() {
        final WebNotificationPopup notificationPopup = new WebNotificationPopup();

        notificationPopup.setDisplayTime(5000);
        notificationPopup.setIcon(NotificationIcon.information);

        String txt = "<html><p>Votre inscription a été effectuée avec succés<br>"
                     + "Entrez votre pseudo et votre mot de passe pour vous connecter</html>";

        notificationPopup.setContent(new JLabel(txt));
        NotificationManager.showNotification(notificationPopup);
    }

    static void infoCommande() {
        final WebNotificationPopup notificationPopup = new WebNotificationPopup();

        notificationPopup.setDisplayTime(7000);
        notificationPopup.setIcon(NotificationIcon.information);

        String txt = "<html><p>Votre Commande a été effectuée avec succés "
                     + "<br>Apres confirmation par appel téléphonique <br> "
                     + "Elle vous sera livrée sous 24 heures</html>";

        notificationPopup.setContent(new JLabel(txt));
        NotificationManager.showNotification(notificationPopup);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
