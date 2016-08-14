import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;


public class Test {
    public BufferedImage getTransparentScaledImage(BufferedImage originalImage, int finalWidth, int finalHeight) {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        int newWidth;
        int newHeight;
        if (originalWidth == 0 || originalHeight == 0
                || (originalWidth == finalWidth && originalHeight == finalHeight)) {
            return originalImage;
        }

        double aspectRatio = (double) originalWidth / (double) originalHeight;
        double boundaryAspect = (double) finalWidth / (double) finalHeight;

        if (aspectRatio > boundaryAspect) {
            newWidth = finalWidth;
            newHeight = (int) Math.round(newWidth / aspectRatio);
        } else {
            newHeight = finalHeight;
            newWidth = (int) Math.round(aspectRatio * newHeight);
        }

        int xOffset = (finalWidth - newWidth) / 2;
        int yOffset = (finalHeight - newHeight) / 2;

        BufferedImage intermediateImage = new BufferedImage(finalWidth, finalHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D gi = intermediateImage.createGraphics();
        gi.setComposite(AlphaComposite.SrcOver);
        gi.setColor(Color.WHITE);
        gi.fillRect(0, 0, finalWidth, finalHeight);
        gi.drawImage(originalImage, xOffset, yOffset, xOffset + newWidth, yOffset + newHeight, 0, 0, originalWidth, originalHeight, Color.WHITE, null);
        gi.dispose();

        //if image from db already had a transparent background, it becomes black when drawing it onto another
        //even if we draw it onto a transparent image
        //so we set it to a specific color, in this case white
        //now we have to set that white background transparent
        Image intermediateWithTransparentPixels = makeColorTransparent(intermediateImage, Color.WHITE);

        //finalize the transparent image
        BufferedImage finalImage = new BufferedImage(finalWidth, finalHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gf = finalImage.createGraphics();
        gf.setComposite(AlphaComposite.SrcOver);
        gf.setColor(new Color(0, 0, 0, 0));
        gf.fillRect(0, 0, finalWidth, finalHeight);
        gf.drawImage(intermediateWithTransparentPixels, 0, 0, finalWidth, finalHeight, new Color(0, 0, 0, 0), null);
        gf.dispose();

        return finalImage;
    }

    public static Image makeColorTransparent(Image im, final Color color) {
        ImageFilter filter = new RGBImageFilter() {
            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = color.getRGB() | 0xFF000000;

            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

    public static void main(String[] args) throws IOException {
        /*String patternDATE = "([0-2]\\d|3[01])\\.(0\\d|1[012])\\.(\\d{4})(([0,1][0-9])|(2[0-3])):[0-5][0-9]";

        String input = "<ID>1</ID><TEXT>askdnas</TEXT><DATE>24.05.2016</DATE>\n<ID>2</ID><TEXT>asasfsdfwdnas</TEXT><DATE>21.05.2016</DATE>";

        String REGEX_1 = "(?<=<TEXT>)(.*?)(?=(</TEXT>))";//"(<TEXT>).*(</TEXT>)";
        String REGEX_2 = "(?<=<DATE>)(.*?)(?=(</DATE>))";
        String REGEX_3 = "(?<=(<[A-Z]{1,4}>))(.*?)(?=(</[A-Z]{1,4}>))";

        Matcher matcher1 = Pattern.compile(REGEX_1).matcher(input);
        Matcher matcher2 = Pattern.compile(REGEX_2).matcher(input);
        Matcher matcher3 = Pattern.compile(REGEX_3).matcher(input);

        while (matcher1.find() && matcher2.find()) {
            System.out.println(matcher1.group() + " " + matcher2.group());
        }

        System.out.println();
        System.out.println(matcher3.matches());

        while (matcher3.find()) {
            System.out.println(matcher3.group());
        }*/


        Test test = new Test();
        BufferedImage bufferedImage = test.getTransparentScaledImage(ImageIO.read(new File("images/avatarBIG.png")), 100, 100);
        File outputfile = new File("image.png");
        ImageIO.write(bufferedImage, "png", outputfile);
    }

}
