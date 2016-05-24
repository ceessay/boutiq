
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.BackOffice_Admin;

//~--- JDK imports ------------------------------------------------------------

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;

/**
 *
 * @author ceessay
 */


/*
Cette classe contient quelques methodes de manipulation des images
*/
public class ImageUtils {
    
    
    public static Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D    g2         = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }
    
      
    
    
    public static BufferedImage redimensionnerImage(Image originalImage, int scaledWidth, int scaledHeight, boolean preserveAlpha) {
        int imageType = preserveAlpha
                ? BufferedImage.TYPE_INT_RGB
                : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();

        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }

        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();

        return scaledBI;
    }
    
    
    public static BufferedImage resize(BufferedImage img, int width, int height){
        return Scalr.resize(img,Scalr.Method.QUALITY, Mode.AUTOMATIC, width, height);
    }
}


