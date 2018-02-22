/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package recdadosconteudo;

import java.util.Comparator;

/**
 *
 * @author Jefferson
 */
public class DistanceComparator implements Comparator<Distance> {

	public int compare(Distance o1, Distance o2) {
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
