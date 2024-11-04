
package lib;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author vvqua
 */
public class processImage {
    
        public static ImageIcon scaleImageIcon(ImageIcon originalIcon, int targetW, int targetH){
        Image x = originalIcon.getImage();
        x = x.getScaledInstance(targetW, targetH, Image.SCALE_SMOOTH);
        return new ImageIcon(x);
    }
}
