package main.java.com.cbir;

/**
 *
 * @author Jefferson William Teixeira
 */
public class Distance {

    String imageNameRef, imageNameBase;
    double dist;

    public Distance (String ref, String base) {
        imageNameRef = ref;
        imageNameBase = base;
        dist = 0;
    }

    public void printDados() {
        System.out.println("imgRef: " + imageNameRef + " imgBase: " + imageNameBase + " dist: " + dist);
    }
}
