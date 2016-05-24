
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.BackOffice_Admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.imgscalr.Scalr;

/**
 *
 * @author ceessay
 */

/*
ce panel presente a l'administrateur une iamge de l'article et un bouton de choix d'image
*/
public class ImageArticlePanel extends JPanel {

    private JLabel imageLabel = new JLabel();// label cotenant l'image
    JFileChooser jfc;
    File fichier;
    private JButton choisirButton;// bouton de choix d'une iamge
    BufferedImage img;

    public ImageArticlePanel() {
        buildGUI();
    }

    public void buildGUI() {
        imageLabel.setPreferredSize(new Dimension(230, 213));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

       
        //deéfinition de la bordure du panel
        setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 1, false), "Image de l'ariticle",
                TitledBorder.CENTER, TitledBorder.TOP, null, null));
        setPreferredSize(new Dimension(200, 350));
        
        
        choisirButton = new JButton("Choisir/Modifier l'image...");
        
        choisirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jfc = new JFileChooser(".");
                jfc.setFileFilter(new FileNameExtensionFilter("Fichiers image", "jpg", "png"));

                if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    fichier = jfc.getSelectedFile();
                    try {
                       
                        //redimensionner l'image
                       img = Scalr.resize(ImageIO.read(fichier),Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, 230, 213);
                       
                    } catch (IOException ex) {
                        Logger.getLogger(ImageArticlePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }

//
//                    try {
//                        ImageIO.write(img, "jpg", fichier);
//                    } catch (IOException ex) {
//                        Logger.getLogger(ImageArticlePanel.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                    setImageLabel(img);
                }
            }
        });

        gbc.gridy = gbc.gridx = 0;
        add(imageLabel, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 0, 0, 0);
        add(choisirButton, gbc);
    }

    public File getImage() {
        return fichier;
    }

    public void setImageLabel(Image image) {

        ImageIcon ico = new ImageIcon(image);
        imageLabel.setIcon(ico);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
    }

    // permet d'afficher l'image recupereé a partir de la base de donées
    void setImageLabel(Blob aBlob) {
        InputStream is = null;
        try {
            is = aBlob.getBinaryStream(1, aBlob.length());
            
            // redimensionnement de l'image
            BufferedImage imag = null;
            //BufferedImage imag =  Scalr.resize(ImageIO.read(is),Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, 230, 213);
             imag = ImageUtils.redimensionnerImage(imag, 230, 213, true);
            setImageLabel(imag);
            
            
            fichier = new File("temp");
            ImageIO.write(imag, "jpg", fichier);

        } catch (SQLException ex) {
            Logger.getLogger(ImageArticlePanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImageArticlePanel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(ImageArticlePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
