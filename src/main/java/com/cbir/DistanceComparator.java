package main.java.com.cbir;

import java.util.Comparator;

/**
 *
 * @author Jefferson William Teixeira
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
