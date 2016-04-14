/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package recdadosconteudo;

import java.awt.image.BufferedImage;

/**
 *
 * @author william
 */
public class ProcImagesFactory {
    
    public static ProcImages procImageFactoryMethod(ManipuladorImagem img) {
        ProcImages procImg;
 
        int imageType = img.getImageType();
        
        switch (imageType) {
            case BufferedImage.TYPE_BYTE_GRAY:
                procImg = new ProcImagesTextura(img);
                break;
            case BufferedImage.TYPE_USHORT_GRAY:
                procImg = new ProcImagesTextura(img);
                break;
            default:
                procImg = new ProcImagesCor(img);
                break;     
        }
        return procImg;
    }     
}
