package test.java.com.cbir;

import static org.junit.Assert.*;
import org.junit.Test;
import main.java.com.cbir.*;

public class DistanceFunctionsTest {
	
	@Test
	public void testDistEuclidiana() {
		double vet1[] = {2, 2, 2, 2};
		double vet2[] = {1, 1, 1, 1};

		double expectedValue = 2.0;
		double returnedValue = DistanceFunctions.distEuclidiana(vet1, vet2, vet1.length);
			
		assertEquals("Euclidian distance ", expectedValue, returnedValue, 0);
	}

	@Test
	public void testDistManhatan() {
		double vet1[] = {2, 2, 2, 2};
		double vet2[] = {1, 1, 1, 1};

		double expectedValue = 4.0;
		double returnedValue = DistanceFunctions.distManhatan(vet1, vet2, vet1.length);
			
		assertEquals("Manhatan distance ", expectedValue, returnedValue, 0);
	}
}
