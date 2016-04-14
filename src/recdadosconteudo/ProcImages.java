/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package recdadosconteudo;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * @author William
 */
public abstract class ProcImages {
    //Constantes
    public final static int HIST_CINZA_DIM = 256;
    public final static int HIST_COR_QTZ_DIM = 64;
    public final static int TYPE_DESC_HARA = 0;
    public final static int TYPE_DESC_HIST = 1;    
    public final static int TYPE_DESC_MOMEN = 2;

    //Variaveis de classe
    protected int mHistDim = 0;
    protected int mCaractDim = 0;
    protected ManipuladorImagem mImage;
    protected double mHistograma[];
    protected double mCaracteristicas[];

    public ProcImages() {
        mImage = null;
    } 

    public ProcImages(ManipuladorImagem img) {
        mImage = img;
    } 
    
    public abstract boolean calcHistograma();
    public abstract boolean calcCaracteristicas();

    public boolean setImage(String imagePath) {
        try {
            mImage = new ManipuladorImagem(imagePath);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return (false);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return (false);
        }
        return (true);
    }

    public void setImage(ManipuladorImagem image) {
        mImage = image;
    }    
    
    protected final void initVetCaract() {
        for (int i = 0; i < mCaractDim; i++) {
            this.mCaracteristicas[i] = 0;
        }
    }

    protected final void initHist() {
        for (int i = 0; i < mHistDim; i++) {
            this.mHistograma[i] = 0;
        }
    }

    public BufferedImage getImage() {
        return (this.mImage.getImage());
    }
    
    public int getHistDim() {
        return mHistDim;
    }
    
    public int getCaractDim() {
        return mCaractDim;
    }
    
    public String getImageName() {
        return (this.mImage.getImageName());
    }

    public double[] getHistograma() {
        return (this.mHistograma);
    }

    public double[] getVetCaracteristicas() {
        return (this.mCaracteristicas);
    }

    public String getHistogramaString(){
        String histStr = "";
        
        for (int i = 0; i < mHistDim; i++)
            histStr = histStr.concat(String.valueOf(mHistograma[i]) + " ");

        return (histStr);
    }

    public String getVetCaractString(){
        String caracStr = "";

        for (int i = 0; i < mCaractDim; i++)
            caracStr = caracStr.concat(String.valueOf(mCaracteristicas[i]) + " ");

        return (caracStr);
    }

    public static String getWekaHeader(String relationName, int type) {
        String header = "@RELATION " + relationName + "\n\n",
               atributes = "";
        
        switch (type) {
            case TYPE_DESC_HARA:
                for (int a = 0; a <= 135; a+=45) {
                    for (int d = 1; d <= 7; d+=2) {
                        String atr = "ang" + a + "d" + d;

                        atributes+= "@ATTRIBUTE " + atr + "_homog " + "REAL\n";
                        atributes+= "@ATTRIBUTE " + atr + "_energ " + "REAL\n";
                        atributes+= "@ATTRIBUTE " + atr + "_varia " + "REAL\n";
                        atributes+= "@ATTRIBUTE " + atr + "_entro " + "REAL\n";
                    }
                }                
                break;
                
            case TYPE_DESC_HIST:
                for (int i = 0; i < HIST_COR_QTZ_DIM; i++) {
                    atributes+= "@ATTRIBUTE bin" + i + " REAL\n";
                }                
                break;
                
            case TYPE_DESC_MOMEN:
                atributes+= "@ATTRIBUTE media REAL\n";
                for (int i = 2; i <= ProcImagesCor.VET_CARACT_DIM; i++) {
                    atributes+= "@ATTRIBUTE momen" + i + " REAL\n";
                }                 
                break; 
                
            default:
                break;
        }
        
        return header + atributes + "\n";
    }    
}
