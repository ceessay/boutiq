
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.FrontOffice_Boutiq;

//~--- JDK imports ------------------------------------------------------------

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author ceessay
 */
public class ChampsFocusListener implements FocusListener {
    JTextField jtf;
    JTextArea  jta;

    public ChampsFocusListener(JTextField jtf) {
        this.jtf = jtf;
        jtf.setBackground(Color.white);
    }



    @Override
    public void focusGained(FocusEvent e) {
        jtf.setBackground(Color.WHITE);
        
    }

    @Override
    public void focusLost(FocusEvent e) {}
}

