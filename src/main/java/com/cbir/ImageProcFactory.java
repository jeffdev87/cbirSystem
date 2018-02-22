package main.java.com.cbir;

import java.awt.image.BufferedImage;

/**
*
* @author Jefferson William Teixeira
*/
public class ImageProcFactory {
    
    public static ImageProc procImageFactoryMethod(ImageHandler img) {
        ImageProc procImg;
 
        int imageType = img.getImageType();
        
        switch (imageType) {
            case BufferedImage.TYPE_BYTE_GRAY:
                procImg = new ImageProcTexture(img);
                break;
            case BufferedImage.TYPE_USHORT_GRAY:
                procImg = new ImageProcTexture(img);
                break;
            default:
                procImg = new ImageProcColor(img);
                break;     
        }
        return procImg;
    }     
}
