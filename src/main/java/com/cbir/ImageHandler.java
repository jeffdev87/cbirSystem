package main.java.com.cbir;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
*
* @author Jefferson William Teixeira
*/
public class ImageHandler {

    private BufferedImage image;
    private String imageName;
    private int w, h;
    private int imageType;

    public ImageHandler(String path) throws IOException {
        try {
            
            File f = new File(path);
            BufferedImage temp = ImageIO.read(f);

            this.imageName = f.getName();

            this.w = temp.getWidth();
            this.h = temp.getHeight();
            this.imageType = temp.getType();

            image = new BufferedImage(this.w, this.h, this.imageType);
            image.getGraphics().drawImage(temp, 0, 0, this.w,  this.h, null);

        }catch (IOException ioex)
        {
            System.err.println("Erro: " + ioex.getMessage());
        }catch (Exception ex)
        {
            System.err.println("Erro: " + ex.getMessage());
        }
    }

    public BufferedImage getImage () {
        return (this.image);
    }

    public int getWidth() {
        return (this.w);
    }

    public String getImageName() {
        return (this.imageName);
    }

    public int getHeight() {
        return (this.h);
    }

    public int getImageType() {
        return (this.imageType);
    }
    public int[] getPixel(int x, int y) {
        if (this.image != null) {
            if (x >= 0 && y >= 0 && x < this.getWidth() && y < this.getHeight())
                return getPixelValue(x, y);
        }
        return (null);
    }

    private int[] getPixelValue(int x, int y) {
        int[] rgb;
        int gray, argb;
        
        switch (this.imageType) {
            case BufferedImage.TYPE_BYTE_GRAY:
                gray = image.getRaster().getSample(x, y, 0);
                rgb = new int[] {gray, gray, gray};
                break;
            case BufferedImage.TYPE_USHORT_GRAY:
                gray = image.getRaster().getSample(x, y, 0)/257;
                rgb = new int[] {gray, gray, gray};
                break;
            default:
                argb = image.getRGB(x, y);
                rgb = new int[] { (argb >> 16) & 0xff, //r
                                  (argb >>  8) & 0xff, //g
                                  (argb      ) & 0xff, //b
                                };
                break;
        }
        return rgb;
    }
}
