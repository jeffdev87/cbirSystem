package main.java.com.cbir;

/**
*
* @author Jefferson William Teixeira
*/
public class ImageProcTexture extends ImageProc {
    
    public final static int VET_DIM_QTZ = 16;
    public final static int QTZ_FACT = 15;
    public final static int N_HAR_DESC = 16;
    public final static int VET_CARACT_DIM = 64;

    public ImageProcTexture() {
        super();
        initMembers();
    }
    
    public ImageProcTexture(ImageHandler img) {
        super(img);
        initMembers();
    }
    
    private void initMembers() {
        mHistDim = HIST_CINZA_DIM;
        mCaractDim = VET_CARACT_DIM;

        mHistograma = new double[mHistDim];
        mCaracteristicas = new double[mCaractDim];
        
        initVetCaract();
        initHist();        
    }
    
    public boolean calcCaracteristicas()
    {
        return (extDescritoresHaralick());
    }

    public boolean calcHistograma() {
        if (mImage == null) {
            return (false);
        }

        initHist();
        try {
            for (int i = 0; i < mImage.getHeight(); i++) {
                for (int j = 0; j < mImage.getWidth(); j++) {
                    int[] pixelValue = mImage.getPixel(i, j);
                    if (pixelValue != null) {
                        mHistograma[pixelValue[0]]++;
                    }
                }
            }
        } catch (IndexOutOfBoundsException iOBEx) {
            System.err.println("Histogram calc error: " + iOBEx.getMessage());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return (true);
    }
    
    private void initMatCoo(double matCoocorrencia[][]) {
        for (int i = 0; i < VET_DIM_QTZ; i++) {
            for (int j = 0; j < VET_DIM_QTZ; j++) {
                matCoocorrencia[i][j] = 0;
            }
        }
    }

    private void normMatCoo(double matCoocorrencia[][], int tot) {

        for (int i = 0; i < VET_DIM_QTZ; i++) {
            for (int j = 0; j < VET_DIM_QTZ; j++) {
                matCoocorrencia[i][j] = (double) matCoocorrencia[i][j] / tot;
            }
        }
    }

    private int calcMatCoo(double matCoocorrencia[][], int di, int dj) {

        double fatQtz = (double) QTZ_FACT / 255;
        int nTot = 0;
        for (int i = 0; i < mImage.getHeight(); i++) {
            for (int j = 0; j < mImage.getWidth(); j++) {

                int[] pixelValue1 = mImage.getPixel(i, j);
                int[] pixelValue2 = mImage.getPixel(i + di, j + dj);

                if ((pixelValue1 != null) && (pixelValue2 != null)) {
                    int px1 = (int) (pixelValue1[0] * fatQtz);
                    int px2 = (int) (pixelValue2[0] * fatQtz);
                    
                    if (px1 >= 0 && px1 < VET_DIM_QTZ &&
                        px2 >= 0 && px2 < VET_DIM_QTZ) {
                        matCoocorrencia[px1][px2]++;
                        //matCoocorrencia[px2][px1]++;
                        nTot++;
                    }
                }
            }
        }
        
        return nTot;
    }

    private boolean extDescritoresHaralick() {
        if (mImage == null)
            return (false);

        initVetCaract();

        double matCoocorrencia[][] = new double[VET_DIM_QTZ][VET_DIM_QTZ];

        int index = 0;
        //4 (matrizes) * 4 (descritores) -> 16 valores no vet de caract
        for (int k = 1; k <= 7; k += 2) {
            // Matrizes de Co-ocorrencia para angulo 0 e distancias k
            initMatCoo(matCoocorrencia);
            int tot = calcMatCoo(matCoocorrencia, 0, k);            
            normMatCoo(matCoocorrencia, tot);
            setVetCaractData(matCoocorrencia, index);
        }

        index+=N_HAR_DESC;
        for (int k = 1; k <= 7; k += 2) {
            // Matrizes de Co-ocorrencia para angulo 45 e distancias k
            initMatCoo(matCoocorrencia);
            int tot = calcMatCoo(matCoocorrencia, -k, k);
            normMatCoo(matCoocorrencia, tot);
            setVetCaractData(matCoocorrencia, index);
        }        
        
        index+=N_HAR_DESC;//+ 16
        for (int k = 1; k <= 7; k += 2) {
            // Matrizes de Co-ocorrencia para angulo 90 e distancias k
            initMatCoo(matCoocorrencia);
            int tot = calcMatCoo(matCoocorrencia, k, 0);
            normMatCoo(matCoocorrencia, tot);
            setVetCaractData(matCoocorrencia, index);
        }
                
        index+=N_HAR_DESC;
        for (int k = 1; k <= 7; k += 2) {
            // Matrizes de Co-ocorrencia para angulo 135 e distancias k
            initMatCoo(matCoocorrencia);
            int tot = calcMatCoo(matCoocorrencia, k, k);
            normMatCoo(matCoocorrencia, tot);
            setVetCaractData(matCoocorrencia, index);
        }
        
        return (true);
    }

    private void setVetCaractData(double matCoocorrencia[][], int index) {
        try {
            mCaracteristicas[index++] = this.calcHomogeneidade(matCoocorrencia);
            mCaracteristicas[index++] = this.calcEnergia(matCoocorrencia);
            mCaracteristicas[index++] = this.calcVariancia(matCoocorrencia);
            mCaracteristicas[index++] = this.calcEntropia(matCoocorrencia);
        }catch (IndexOutOfBoundsException iob_ex) {
            System.err.println(iob_ex.getMessage());
        }catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    private double calcVariancia(double matCoocorrencia[][]) {
        double variancia = 0;
        for (int i = 0; i < VET_DIM_QTZ; i++) {
            for (int j = 0; j < VET_DIM_QTZ; j++) {
                variancia += (double) ((Math.pow(2, (i - j))) * matCoocorrencia[i][j]);
            }
        }
        return variancia;
    }

    private double calcEnergia(double matCoocorrencia[][]) {
        double energia = 0;
        for (int i = 0; i < VET_DIM_QTZ; i++) {
            for (int j = 0; j < VET_DIM_QTZ; j++) {
                energia += (double) (Math.pow(2, matCoocorrencia[i][j]));
            }
        }
        return energia;
    }

    private double calcEntropia(double matCoocorrencia[][]) {
        double entropia = 0;
        double log2 = (double) Math.log(2);
        for (int i = 0; i < VET_DIM_QTZ; i++) {
            for (int j = 0; j < VET_DIM_QTZ; j++) {
                if (matCoocorrencia[i][j] != 0) {
                    entropia += (double) ((Math.log(matCoocorrencia[i][j]) / log2) * matCoocorrencia[i][j]);
                }
            }
        }
        return entropia;
    }

    private double calcHomogeneidade(double matCoocorrencia[][]) {
        double homoge = 0;
        for (int i = 0; i < VET_DIM_QTZ; i++) {
            for (int j = 0; j < VET_DIM_QTZ; j++) {
                if (matCoocorrencia[i][j] != 0) {
                    homoge += (double) ((matCoocorrencia[i][j]) / (1 + Math.abs(i - j)));
                }
            }
        }
        return homoge;
    }
}
