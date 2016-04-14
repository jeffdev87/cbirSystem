package recdadosconteudo;

import java.awt.image.BufferedImage;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class HistogramChart {

    private JFreeChart chart;
    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private int altura = 240;
    private int largura = 300;
    private String histTitulo = "Histograma";
    private String eixo_x = "Escala de Cinza";
    private String eixo_y = "Freqüência";
    private PlotOrientation orientacao = PlotOrientation.VERTICAL;
    private boolean show = false;
    private boolean toolTips = false;
    private boolean urls = false;

    public HistogramChart(double[] histVet, int dim) {
        Integer i;
        String iS;

        for (i = 0; i < dim; i++) {
            iS = i.toString();
            dataset.setValue(histVet[i], "0", iS);
        }
    }

    public BufferedImage gerarHistPNG() {
        chart = ChartFactory.createBarChart(histTitulo, eixo_x, eixo_y, dataset, orientacao, show, toolTips, urls);
        return (chart.createBufferedImage(largura, altura));
    }
}
