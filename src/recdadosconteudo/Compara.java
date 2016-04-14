/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package recdadosconteudo;

import java.util.Comparator;

/**
 *
 * @author Va
 */
public class Compara implements Comparator {

    public int compare(Object arg1, Object arg2) {
        EstruturaDist o1 = (EstruturaDist) arg1,
                o2 = (EstruturaDist) arg2;
        if (o1.dist < o2.dist) {
            return -1;
        } else {
            if (o1.dist > o2.dist) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
