package gui;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoadImage {
    public static void resizeImage(String path, int width, int height) throws IOException {
        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(new File(path));
        } catch (IOException e) {
        }
        BufferedImage scaled = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaled.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();

        ImageIO.write(scaled, "JPG", new File("1.jpg"));
        //return scaled;
    }

    public static void main(String[] args) throws IOException {
        //LoadImage.resizeImage("lambo.jpg");
    }
}
