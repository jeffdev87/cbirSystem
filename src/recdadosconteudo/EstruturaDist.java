/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package recdadosconteudo;

/**
 *
 * @author Va
 */
public class EstruturaDist {

    String imageNameRef, imageNameBase;
    double dist;

    public EstruturaDist (String ref, String base) {
        imageNameRef = ref;
        imageNameBase = base;
        dist = 0;
    }

    public void printDados() {
        System.out.println("imgRef: " + imageNameRef + " imgBase: " + imageNameBase + " dist: " + dist);
    }
}
