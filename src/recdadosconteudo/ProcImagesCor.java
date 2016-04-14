/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package recdadosconteudo;

/**
 *
 * @author William
 */
public class ProcImagesCor extends ProcImages {
    
    public final static int VET_CARACT_DIM = 4;
    public final static int QTZ_FACTOR = 64;
    
    public ProcImagesCor() {
        super();
        initMembers();
    }    

    public ProcImagesCor(ManipuladorImagem img) {
        super(img);
        initMembers();
    }
    
    private void initMembers() {
        mHistDim = HIST_COR_QTZ_DIM;
        mCaractDim = VET_CARACT_DIM;
        
        mHistograma = new double[mHistDim];
        mCaracteristicas = new double[mCaractDim];
        
        initVetCaract();
        initHist();        
    } 
    
    @Override
    public boolean calcHistograma() {
        if (mImage == null) {
            return (false);
        }

        initHist();

        for (int i = 0; i < mImage.getHeight(); i++) {
            for (int j = 0; j < mImage.getWidth(); j++) {
                int[] pixelValue = mImage.getPixel(i, j);
                if (pixelValue != null) {
                    int cR = (pixelValue[0] & 192) >> 2;// extrai MSBs de R e move p/ o final 
                    int cG = (pixelValue[1] & 192) >> 4;// extrai MSBs de G e move p/ apos C1
                    int cB = (pixelValue[2] & 192) >> 6;// extrai MSBs de B e move p/ apos C2

                    int cQ = cR | cG | cB;
                    try {                    
                        mHistograma[cQ]++;
                    } catch (IndexOutOfBoundsException iOBEx) {
                        System.err.println("Histogram quantization error: " + iOBEx.getMessage());
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }                        
                }
            }
        }        
        normalizeHist(1);
        
        return true;
    }

    @Override
    public boolean calcCaracteristicas() {
        int index = 0;
        calcHistograma();
        mCaracteristicas[index++] = calcMomentoMedia();
        for (int i = 2; i <= mCaractDim; i++) {
            mCaracteristicas[index++] = calcMomentosD(i);
        }
        return true;
    }      
    
    private void normalizeHist(int factor) {
        double max = 0, sum = 0, e = 0.01;
        
        for (int i = 0; i < mHistDim ; i++) {
            sum+= mHistograma[i] * mHistograma[i];
            max = (mHistograma[i] > max) ? mHistograma[i] : max;
        } 
        
        if (factor == 1)
            for (int i = 0; i < mHistDim ; i++)
                mHistograma[i] =(double) Math.sqrt((double) (mHistograma[i]/((double) sum + e)));
        else if (factor > 1)
            for (int i = 0; i < mHistDim ; i++)
                mHistograma[i] = (double) (((double) mHistograma[i]/max)*factor);
        
    }
    
    private double calcMomentoMedia() {
        double media = 0;
        int nPixels = mImage.getHeight() * mImage.getWidth();
        for (int i = 0; i < mHistDim; i++)
            media+= (double) mHistograma[i];
        
        media = (double) (media/nPixels);
        
        return media;
    }
    
    private double calcMomentosD(int d) {
        double sum_dif = 0;
        double media = calcMomentoMedia();
        
        int nPixels = mImage.getHeight() * mImage.getWidth();
        
        for (int i = 0; i < mImage.getHeight(); i++) {
            for (int j = 0; j < mImage.getWidth(); j++) {
                int[] pixelValue = mImage.getPixel(i, j);
                if (pixelValue != null) {
                    int cR = (pixelValue[0] & 192) >> 2;// extrai MSBs de R e move p/ o final 
                    int cG = (pixelValue[1] & 192) >> 4;// extrai MSBs de G e move p/ apos C1
                    int cB = (pixelValue[2] & 192) >> 6;// extrai MSBs de B e move p/ apos C2

                    int cQ = cR | cG | cB;
                    sum_dif+= (double) Math.pow(cQ - media, d);        
                }
            }
        }
        
        double moment = (double) Math.pow((double) (sum_dif/nPixels), (double) (1/d));
        
        return moment;
    }
}
