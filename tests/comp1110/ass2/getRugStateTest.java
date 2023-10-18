//package comp1110.ass2;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Timeout;
//
//import java.util.concurrent.TimeUnit;
//
//@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
//public class getRugStateTest {
//
//    @Test
//    public void getRugStateTest() {
//        Rugs rug = new Rugs('p', 01, 4, 4, 4, 5);
//
//        String test = rug.getRugState();
//
//        Assertions.assertEquals("p014445", test);
//    }
//
//    @Test
//    public void incorrectValue() {
//        Assertions.assertThrows(IllegalArgumentException.class, () -> {new Rugs('t', 01, 4, 4, 4, 5); },
//            "Illegal Argument Exception expected - No such colour connected to 't'");
//    }
//}
