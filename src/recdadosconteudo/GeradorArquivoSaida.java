package recdadosconteudo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GeradorArquivoSaida {

    //Arquivo para escrita
    private FileWriter writer = null;

    //Arquivo de saída
    private PrintWriter saida = null;

    private String fileName = null;

    public GeradorArquivoSaida (String fileName){
        this.fileName = fileName;
    }

    /**
     *
     * Método responsável por criar o arquivo de saída.
     *
     */
    public void criaArquivo() {
        try {
            writer = new FileWriter(new File(this.fileName), true);
            saida = new PrintWriter(writer);
        } catch (IOException ex) {
            System.err.println("Erro ao criar o arquivo " + this.fileName + "!");
            System.err.println(ex.getMessage());
        }
    }

    /**
     *
     * Método responsável por adicionar um registro ao arquivo.
     *
     */
    public boolean adicionaRegistro(String str) {
        if (saida == null)
            return (false);
        else
        {
            saida.println(str);
            saida.flush();
        }
        return (true);
    }

    /**
     *
     * Método responsável por fechar o arquivo de saída.
     *
     */
    public void fechaArquivo() {
        try {
            saida.close();
            writer.close();
        } catch (IOException ex) {
            System.err.println("Erro ao fechar o arquivo " + this.fileName + "!");
            System.err.println(ex.getMessage());
        }
    }
}

