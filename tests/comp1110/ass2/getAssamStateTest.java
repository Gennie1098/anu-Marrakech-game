package comp1110.ass2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
public class getAssamStateTest {
    @Test
    public void getAssamState() {
        Assam assam = new Assam("A12N");

        String test = assam.getAssamState();
        Assertions.assertEquals("A12N", test);
    }

    @Test
    public void incorrectOrientation() {
        int randomValue = (int) (Math.random() * 8);

        String randomAssamString = "A" + randomValue + randomValue + 'F';


        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Assam(randomAssamString);
        }, "Expected Illegal Argument Exception");
    }

    @Test
    public void outsideBounds() {

        int randomValue = (int) (Math.random() * 8);
        int randomValueTwo = 8 + (int) (Math.random() * 2);

        String randomAssamString = "A" + randomValue + randomValueTwo + 'N';

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            new Assam(randomAssamString);
        }, "Expected Index out of Bounds Exeception - Assam is out of bounds");
    }
}


