package recdadosconteudo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.Vector;

public class OutputFileManager {

    private Scanner input;
    private String fileName = null;

    public OutputFileManager(String fileName) {
        this.fileName = fileName;
        this.input = null;
    }

    public void openFile() {
        try {
            this.input = new Scanner(new File(this.fileName));
        } catch (FileNotFoundException fEx) {
            System.err.println("Erro ao abrir o arquivo " + this.fileName + "!");
            System.err.println(fEx.getMessage());
        }
    }

    public boolean fileExists() {
        if (this.input != null) {
            return (true);
        }
        return (false);
    }

    public boolean emptyFile() {
        if (!this.input.hasNext()) {
            return (true);
        }
        return (false);
    }

    public Set<String> readAllEntriesFromFile() {
        Set<String> listaNomesArqs = new HashSet<String>();
        
        try {
            while (this.input.hasNextLine()) {
                String line = this.input.nextLine();
                listaNomesArqs.add(line.substring(0, line.indexOf(":")));
            }
        } catch (NoSuchElementException elementException) {
            System.err.println("Arquivo mal formado!");
            System.err.println(elementException.getMessage());

            this.input.close();
        } catch (IllegalStateException stateException) {
            System.err.println("Erro ao ler do arquivo!");
            System.err.println(stateException.getMessage());
            
            this.input.close();
        }catch (Exception exp) {
            System.err.println("Erro ao ler do arquivo!");
            System.err.println(exp.getMessage());

            this.input.close();
        }
        return (listaNomesArqs);
    }

    
    
    public String[] encontrarImgNoArquivo(String queryImageName, int dimCarac) {
        if (!fileExists())
            return (null);

        String[] vetAux = null;

        try {
            boolean found = false;
            while (this.input.hasNextLine() && !found) {
                String line = this.input.nextLine();
                int endOfName = line.indexOf(":");

                if (queryImageName.equals(line.substring(0, endOfName))) {
                    vetAux = line.substring(endOfName + 1).split(" ");
                    found = true;
                }
            }
        } catch (NoSuchElementException exp) {
            System.err.println("Arquivo mal formado!");
            System.err.println(exp.getMessage());

        } catch (IllegalStateException exp) {
            System.err.println("Erro ao ler do arquivo!");
            System.err.println(exp.getMessage());
            
        } catch (Exception exp) {
            System.err.println("Erro ao ler do arquivo!");
            System.err.println(exp.getMessage());
        }

        return (vetAux);
    }
 
    public Vector<Distance> calcDistancias(double vetCaracRef[],
                                                String imgNameRef,
                                                TiposDist optDist) {
        if (!fileExists() || vetCaracRef == null)
            return (null);

        Vector<Distance> vetAux = new Vector<Distance>();

        try {
            while (this.input.hasNextLine()) {
                String line = this.input.nextLine(),
                       imgNameBase = line.substring(0, line.indexOf(":"));

                int endOfName = line.indexOf(":");

                if (!imgNameRef.equals(imgNameBase)) {
                    String vetString[] = line.substring(endOfName + 1).split(" ");
                    
                    double vetCarac[] =
                            Distancias.parseSringToDouble (vetString,
                                                           vetString.length);
                    
                    Distance estDist = new Distance(imgNameRef,
                                                              imgNameBase);
                    switch (optDist) {
                        case EUCLIDIAN:
                            estDist.dist =
                                    Distancias.distEuclidiana(vetCaracRef,
                                                              vetCarac,
                                                              vetString.length);
                            break;
                        case MANHATAN:
                            estDist.dist =
                                    Distancias.distManhatan(vetCaracRef,
                                                            vetCarac,
                                                            vetString.length);
                            break;
                            
                        default:
                            break;
                    }
                    vetAux.add(estDist);
                }
            }
        } catch (NoSuchElementException ex) {
            System.err.println("Arquivo mal formado!");
            System.err.println(ex.getMessage());
        } catch (IllegalStateException ex) {
            System.err.println("Erro ao ler do arquivo!");
            System.err.println(ex.getMessage());
        } catch (NullPointerException ex) {
            System.err.println("Erro ao ler do arquivo!");
            System.err.println(ex.getMessage());
        }catch (Exception ex) {
            System.err.println("Erro ao ler do arquivo!");
            System.err.println(ex.getMessage());
        }
        return (vetAux);
    }

    public void closeFile() {
        if (fileExists()) {
            this.input.close();
        }
    }
}
