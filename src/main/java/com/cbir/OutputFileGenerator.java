package main.java.com.cbir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
*
* @author Jefferson William Teixeira
*/
public class OutputFileGenerator {

    private FileWriter writer = null;
    private PrintWriter output = null;
    private String fileName = null;

    public OutputFileGenerator (String fileName){
        this.fileName = fileName;
    }

    public void createFile() {
        try {
            writer = new FileWriter(new File(this.fileName), true);
            output = new PrintWriter(writer);
        } catch (IOException ex) {
            System.err.println("Erro ao criar o arquivo " + this.fileName + "!");
            System.err.println(ex.getMessage());
        }
    }

    public boolean addEntry(String str) {
        if (output == null)
            return (false);
        else
        {
            output.println(str);
            output.flush();
        }
        return (true);
    }

    public void closeFile() {
        try {
            output.close();
            writer.close();
        } catch (IOException ex) {
            System.err.println("Erro ao fechar o arquivo " + this.fileName + "!");
            System.err.println(ex.getMessage());
        }
    }
}