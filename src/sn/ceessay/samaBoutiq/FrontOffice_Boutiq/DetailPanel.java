package sn.ceessay.samaBoutiq.FrontOffice_Boutiq;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package sn.ceessay.samaboutiq;
//
//import java.awt.BasicStroke;
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.RenderingHints;
//import javax.swing.JPanel;
//import org.jdesktop.swingx.graphics.ShadowRenderer;
//import org.jdesktop.swingx.util.GraphicsUtilities;
//
///**
// *
// * @author ceessay
// */
//public class DetailPanel extends JPanel {
//    public DetailPanel() {
//        setOpaque(false);
//    }
//    
//    
//    @Override
//protected void paintComponent(Graphics g) {
//    int x = 34;
//    int y = 34;
//    int w = getWidth() - 68;
//    int h = getHeight() - 68;
//    int arc = 30;
//
//    Graphics2D g2 = (Graphics2D) g.create();
//    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//            RenderingHints.VALUE_ANTIALIAS_ON);
//
//    g2.setColor(new Color(0, 0, 0, 220));
//    g2.fillRoundRect(x, y, w, h, arc, arc);
//
//    g2.setStroke(new BasicStroke(3f));
//    g2.setColor(Color.WHITE);
//    g2.drawRoundRect(x, y, w, h, arc, arc); 
//
//    g2.dispose();
//}
//
//@Override
//public void setBounds(int x, int y, int width, int height) {
//    super.setBounds(x, y, width, height);
//
//    int w = getWidth() - 68;
//    int h = getHeight() - 68;
//    int arc = 30;
//    int shadowSize = 20;
//
//    shadow = GraphicsUtilities.createCompatibleTranslucentImage(w, h);
//    Graphics2D g2 = shadow.createGraphics();
//    g2.setColor(Color.WHITE);
//    g2.fillRoundRect(0, 0, w, h, arc, arc);
//    g2.dispose();
//
//    ShadowRenderer renderer = new ShadowRenderer(shadowSize, 0.5f, Color.BLACK);
//    shadow = renderer.createShadow(shadow);
//}
//}