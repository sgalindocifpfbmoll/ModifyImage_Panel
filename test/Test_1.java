import Modificator.Modifications;
import Helper.Matrix2D;
import com.modificar.imagen.Image;

import javax.swing.*;
import java.awt.*;

public class Test_1 {

    public static void main(String[] args) {
        // Create a frame
        Frame frame = new Frame();
        frame.add(new CustomPaintComponent());
        int frameWidth = 1366;
        int frameHeight = 768;
        //frame.setSize(frameWidth, frameHeight);
        frame.setVisible(true);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Set the X to close window
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  // Open window on maximized
    }

    static class CustomPaintComponent extends Component {

        public void paint(Graphics g2) {
            // Cast graphics to 2D
            Graphics2D g = (Graphics2D) g2;

            // Location of image to introduce
            String imageURL = "subaru.jpg";
            // Instance new image to be modified
            Image originalImage = new Image(imageURL, 3);
            //originalImage.resize(200, 200);
            //Image resizedImage = originalImage.getResized(200, 200);
            // Instances of modified images - Blur, Sharp and Negative
            Image negativeImage = originalImage.getModified(new Matrix2D(Modifications.NEGATIVE));
            Image rgbImage = originalImage.getModified(130, 100, 100, 25);
            // Insert the images to an array
            Image[] images= new Image[]{originalImage, negativeImage, rgbImage};

            //Initial position of the image
            int x = 0, y = 0;

            // Paint the images from array
            for(Image image: images){
                image.paint(g, x, y);
                x += image.getWidthImage();
            }
            //originalImage.paint(g, 0, 0);

        }

    }
}
