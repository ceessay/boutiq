
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.BackOffice_Admin;

//~--- non-JDK imports --------------------------------------------------------
import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import com.alee.utils.SwingUtils;

import sn.ceessay.samaBoutiq.FrontOffice_Boutiq.ChampsFocusListener;
import sn.ceessay.samaBoutiq.Classes.Constantes;

//~--- JDK imports ------------------------------------------------------------
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author ceessay
 * 
 */

/*Cette classe est un panel contenant les label et les  champs necessaire pour saisir ou afficher
les caracteristiques techniques d'un article. elle fournit aussi une ethode de verification de la saisie
*/

public class CaracteristiquesArticlePanel extends WebPanel {

    private String archis[] = new String[]{null, "Non définie", "ARM", "32 bits", "64 bits"};
    private ArrayList<String> valeursChamps;
    private WebTextField osField, marqueCPUField, cadenceCPUField, tailleRAMField, tailleEcranField, tailleDDField,
            resolutionWebField, resolutionCamField;
    private WebComboBox archCPUBox;
    private WebCheckBox wifiCheckBox, webcamCheckBox, bluetoothCheckBox, cameraCheckBox;
    private WebButton ajouterButton;

    public CaracteristiquesArticlePanel() {
        buidGUI();
    }

    public void buidGUI() {
        valeursChamps = new ArrayList<>();
        setLayout(new GridBagLayout());
        
       // definir la bordure
        setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 1, false), "Caracteristiques de l'article",
                TitledBorder.CENTER, TitledBorder.TOP, null, null));

        JLabel osLabel = new JLabel("Systeme d'exploitation", JLabel.TRAILING);

        osField = new WebTextField(16);

        JLabel marqueCPULabel = new JLabel("Marque du processeur", JLabel.TRAILING);

        marqueCPUField = new WebTextField();

        JLabel cadenceCPULabel = new JLabel("Fréquence du processeur (GHz)", JLabel.TRAILING);

        cadenceCPUField = new WebTextField();

        JLabel archiCPULabel = new JLabel("Architecture du Processeur", JLabel.TRAILING);

        archCPUBox = new WebComboBox(archis);

        JLabel tailleRAMLabel = new JLabel("Taille de la RAM (Go)", JLabel.TRAILING);

        tailleRAMField = new WebTextField();

        JLabel tailleDDLabel = new JLabel("Memoire interne (Go)", JLabel.TRAILING);

        tailleDDField = new WebTextField();

        JLabel tailleEcranLabel = new JLabel("Taille de l'ecran (pouces)", JLabel.TRAILING);

        tailleEcranField = new WebTextField();

        JLabel webLabel = new JLabel("Webcam/Camera frontale", JLabel.TRAILING);

        webcamCheckBox = new WebCheckBox();

        JLabel resolWebLabel = new JLabel("Résolution Webcam/Camera frontale (Mpixels)", JLabel.TRAILING);

        resolutionWebField = new WebTextField(5);
        resolutionWebField.setEnabled(false);

        JLabel camLabel = new JLabel("Photo/Caméra", JLabel.TRAILING);

        cameraCheckBox = new WebCheckBox();

        JLabel resolCamLabel = new JLabel("Résolution photo/caméra (Mpixels)", JLabel.TRAILING);

        resolutionCamField = new WebTextField();
        resolutionCamField.setEnabled(false);

        JLabel blueLabel = new JLabel("Bluetooth", JLabel.TRAILING);

        bluetoothCheckBox = new WebCheckBox();

        JLabel wifiLabel = new JLabel("Wifi", JLabel.TRAILING);

        wifiCheckBox = new WebCheckBox();
        webcamCheckBox.addActionListener(new checkBoxListener(webcamCheckBox, resolutionWebField));
        cameraCheckBox.addActionListener(new checkBoxListener(cameraCheckBox, resolutionCamField));

        JPanel j1 = new JPanel(/*new GridLayout(7, 2, 20, 10)*/);
        j1.setLayout(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();

        gbc1.anchor = GridBagConstraints.LINE_START;
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.insets = new Insets(3, 3, 3, 3);

        gbc1.gridy = gbc1.gridx = 0;
        j1.add(osLabel, gbc1);

        gbc1.gridx = 1;
        j1.add(osField, gbc1);

        gbc1.gridy = 1;
        gbc1.gridx = 0;
        j1.add(marqueCPULabel, gbc1);

        gbc1.gridy = 1;
        gbc1.gridx = 1;
        j1.add(marqueCPUField, gbc1);

        gbc1.gridy = 2;
        gbc1.gridx = 0;
        j1.add(cadenceCPULabel, gbc1);

        gbc1.gridy = 2;
        gbc1.gridx = 1;
        j1.add(cadenceCPUField, gbc1);

        gbc1.gridy = 3;
        gbc1.gridx = 0;
        j1.add(archiCPULabel, gbc1);

        gbc1.gridy = 3;
        gbc1.gridx = 1;
        j1.add(archCPUBox, gbc1);

        gbc1.gridy = 4;
        gbc1.gridx = 0;
        j1.add(tailleRAMLabel, gbc1);

        gbc1.gridy = 4;
        gbc1.gridx = 1;

        j1.add(tailleRAMField, gbc1);

        gbc1.gridy = 5;
        gbc1.gridx = 0;
        j1.add(tailleDDLabel, gbc1);

        gbc1.gridy = 5;
        gbc1.gridx = 1;
        j1.add(tailleDDField, gbc1);

        gbc1.gridy = 6;
        gbc1.gridx = 0;
        j1.add(tailleEcranLabel, gbc1);

        gbc1.gridy = 6;
        gbc1.gridx = 1;
        j1.add(tailleEcranField, gbc1);

        
        WebPanel j2 = new WebPanel();
        //j2.setMinimumWidth(700);
        j2.setLayout(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        
        gbc2.anchor = GridBagConstraints.LINE_END;
        
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        
        gbc2.insets = new Insets(3, 3, 3, 3);

        gbc2.gridy = gbc2.gridx = 0;
        j2.add(wifiLabel,gbc2);

        gbc2.gridx = 1;
        j2.add(wifiCheckBox, gbc2);

        gbc2.gridy = 1;
        gbc2.gridx = 0;
        j2.add(blueLabel, gbc2);

        gbc2.gridy = 1;
        gbc2.gridx = 1;
        j2.add(bluetoothCheckBox, gbc2);
        
        
        gbc2.gridy = 2;
        gbc2.gridx = 0;
        j2.add(webLabel, gbc2);
        
        
        gbc2.gridy = 2;
        gbc2.gridx = 1;
        j2.add(webcamCheckBox, gbc2);
        gbc2.gridy = 3;
        gbc2.gridx = 0;
        j2.add(resolWebLabel, gbc2);
        

        gbc2.gridy = 3;
        gbc2.gridx = 1;
        j2.add(resolutionWebField, gbc2);

        gbc2.gridy = 4;
        gbc2.gridx = 0;
        j2.add(camLabel, gbc2);

        gbc2.gridy = 4;
        gbc2.gridx = 1;
        j2.add(cameraCheckBox, gbc2);

        gbc2.gridy = 5;
        gbc2.gridx = 0;
        j2.add(resolCamLabel, gbc2);

        gbc2.gridy = 5;
        gbc2.gridx = 1;
        j2.add(resolutionCamField, gbc2);

        gbc2.gridy = 6;
        gbc2.gridx = 0;
        j2.add(new JLabel(), gbc2);

        gbc2.gridy = 6;
        gbc2.gridx = 1;
        j2.add(new JPanel(), gbc2);

        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        //gbc.insets = new Insets(7, 7, 7, 7);
        
        gbc.gridy = gbc.gridx =0;
        add(j1, gbc);
        
        
        gbc.gridx=1;
        add(j2,gbc);

//      add(new JPanel());
//      add(ajouterButton);
    }

    public boolean verifierChamps() {
        
        
        valeursChamps.clear();
        
        
        /* verification des different champs. 
        si la saisie est bonne, la valeur du champ est ajoutée a l'arraylist valeursChamps.
        sinon le champ est colorie en rouge. on ajoute un listener qui permer de remettre le champs
        en blanc lorsqu'il regagne le focus.
        a */ 

        int nberreurs = 0;

        // os
        if (osField.getText().length() < 2) {
            nberreurs++;
            osField.setBackground(Constantes.COULEUR_ERR_CHAMPS);
            osField.addFocusListener(new ChampsFocusListener(osField));
        } else {
            valeursChamps.add(osField.getText());
        }

        // marqueCPU
        if (marqueCPUField.getText().length() < 2) {
            nberreurs++;
            marqueCPUField.setBackground(Constantes.COULEUR_ERR_CHAMPS);
            marqueCPUField.addFocusListener(new ChampsFocusListener(marqueCPUField));
        } else {
            valeursChamps.add(marqueCPUField.getText());
        }

        // cadence
        boolean aB = false;
        double nD = 0;

        try {
            nD = Double.valueOf(cadenceCPUField.getText());
            aB = true;
        } catch (NumberFormatException e) {
            aB = false;
        }

        if ((!aB) || (nD <= 0)) {
            nberreurs++;
            cadenceCPUField.setBackground(Constantes.COULEUR_ERR_CHAMPS);
            cadenceCPUField.addFocusListener(new ChampsFocusListener(cadenceCPUField));
        } else {
            valeursChamps.add(cadenceCPUField.getText());
        }

        // archibox
        if (archCPUBox.getSelectedItem() == null) {
            nberreurs++;
        } else {
            valeursChamps.add((String) archCPUBox.getSelectedItem());
        }

        // taille ram
        boolean b = false;
        double n = 0;

        try {
            n = Double.valueOf(tailleRAMField.getText());
            b = true;
        } catch (NumberFormatException e) {
            b = false;
        }

        if ((!b) || (n <= 0)) {
            nberreurs++;
            tailleRAMField.setBackground(Constantes.COULEUR_ERR_CHAMPS);
            tailleRAMField.addFocusListener(new ChampsFocusListener(tailleRAMField));
        } else {
            valeursChamps.add(tailleRAMField.getText());
        }

        // taille dd
        double dm = 0;
        boolean dc = false;

        try {
            dm = Double.valueOf(tailleDDField.getText());
            dc = true;
        } catch (NumberFormatException e) {
            dc = false;
        }

        if ((!dc) || (dm <= 0)) {
            nberreurs++;
            tailleDDField.setBackground(Constantes.COULEUR_ERR_CHAMPS);
            tailleDDField.addFocusListener(new ChampsFocusListener(tailleDDField));
        } else {
            valeursChamps.add(tailleDDField.getText());
        }

        // taillecran
        double m = 0;
        boolean c = false;

        try {
            m = Double.valueOf(tailleEcranField.getText());
            c = true;
        } catch (NumberFormatException e) {
            c = false;
        }

        if ((!c) || (m <= 0)) {
            nberreurs++;
            tailleEcranField.setBackground(Constantes.COULEUR_ERR_CHAMPS);
            tailleEcranField.addFocusListener(new ChampsFocusListener(tailleEcranField));
        } else {
            valeursChamps.add(tailleEcranField.getText());
        }

        // wifi
        if (wifiCheckBox.isSelected()) {
            valeursChamps.add("true");
        } else {
            valeursChamps.add("false");
        }

        // bluetooth
        if (bluetoothCheckBox.isSelected()) {
            valeursChamps.add("true");
        } else {
            valeursChamps.add("false");
        }

        // webcam
        if (webcamCheckBox.isSelected()) {
            double z = 0;
            boolean f = false;

            try {
                z = Double.valueOf(resolutionWebField.getText());
                f = true;
            } catch (NumberFormatException e) {
                f = false;
            }

            if ((!f) || (z <= 0)) {
                nberreurs++;
                resolutionWebField.setBackground(Constantes.COULEUR_ERR_CHAMPS);
                resolutionWebField.addFocusListener(new ChampsFocusListener(resolutionWebField));
            } else {
                valeursChamps.add("true");
                valeursChamps.add(resolutionWebField.getText());
            }
        } else {
            valeursChamps.add("false");
            valeursChamps.add("-1");
        }

        // camera
        if (cameraCheckBox.isSelected()) {
            double az = 0;
            boolean bo = false;

            try {
                az = Double.valueOf(resolutionCamField.getText());
                bo = true;
            } catch (NumberFormatException e) {
                bo = false;
            }

            if ((!bo) || (az <= 0)) {
                nberreurs++;
                resolutionCamField.setBackground(Constantes.COULEUR_ERR_CHAMPS);
                resolutionCamField.addFocusListener(new ChampsFocusListener(resolutionCamField));
            } else {
                valeursChamps.add("true");
                valeursChamps.add(resolutionCamField.getText());
            }
        } else {
            valeursChamps.add("false");
            valeursChamps.add("-1");
        }

        return nberreurs == 0;
    }

    void voirsaisie() {
        System.out.println(osField.getText());
        System.out.println(marqueCPUField.getText());
        System.out.println(cadenceCPUField.getText());
        System.out.println((String) archCPUBox.getSelectedItem());
        System.out.println(tailleRAMField.getText());
        System.out.println(tailleEcranField.getText());
        System.out.println(tailleDDField.getText());
        System.out.println(resolutionWebField.getText());
        System.out.println(resolutionCamField.getText());
    }

    void voirsaisie2() {
        System.out.println("===============================");

        for (String string : valeursChamps) {
            System.out.println(string);
        }

        System.out.println("===============================");
    }

    public ArrayList<String> getValeursChamps() {
        return valeursChamps;
    }

    public void clearValeursChamps() {
        valeursChamps.clear();
    }

    public WebTextField getOsField() {
        return osField;
    }

    public WebTextField getMarqueCPUField() {
        return marqueCPUField;
    }

    public WebTextField getCadenceCPUField() {
        return cadenceCPUField;
    }

    public WebTextField getTailleRAMField() {
        return tailleRAMField;
    }

    public WebTextField getTailleEcranField() {
        return tailleEcranField;
    }

    public WebTextField getTailleDDField() {
        return tailleDDField;
    }

    public WebTextField getResolutionWebField() {
        return resolutionWebField;
    }

    public WebTextField getResolutionCamField() {
        return resolutionCamField;
    }

    public WebComboBox getArchCPUBox() {
        return archCPUBox;
    }

    public WebCheckBox getWifiCheckBox() {
        return wifiCheckBox;
    }

    public WebCheckBox getWebcamCheckBox() {
        return webcamCheckBox;
    }

    public WebCheckBox getBluetoothCheckBox() {
        return bluetoothCheckBox;
    }

    public WebCheckBox getCameraCheckBox() {
        return cameraCheckBox;
    }

    private class checkBoxListener implements ActionListener {

        private final WebCheckBox c;
        private final WebTextField t;

        public checkBoxListener(WebCheckBox c, WebTextField t) {
            this.c = c;
            this.t = t;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (c.isSelected()) {
                t.setEnabled(true);
            } else {
                t.setEnabled(false);
            }
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
