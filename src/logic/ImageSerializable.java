package logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class ImageSerializable implements Serializable {
    private byte[] imageBytes;

    public ImageSerializable(BufferedImage bufferedImage) {
        this.imageBytes = setImageToByteArray(bufferedImage);
    }

    private byte[] setImageToByteArray(BufferedImage bufferedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpg", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    private BufferedImage getImageFromByteArray(byte[] imageBytes){
        try {
            if (imageBytes != null && (imageBytes.length > 0)) {
                BufferedImage im = ImageIO.read(new ByteArrayInputStream(imageBytes));
                return im;
            }
            return null;
        } catch (IOException e) {
            //throw new IllegalArgumentException(e.toString());
            return null;
        }
    }

    public BufferedImage getBufferedImage(){
        return getImageFromByteArray(imageBytes);
    }
}
