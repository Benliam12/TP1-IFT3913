import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TassertTest {

    @Test
    public void test1(){
        CustomAssertEquals("assertEquals(\"hehe, ohoho\")","ohoh");
        assertEquals("assertEquals(\"hehe, ohoho\")","ohoh");
        AssertEqualsCustom("assertEquals(\"hehe, ohoho\")","ohoh");
    }

    public void CustomAssertEquals(String test, String tes2){

    }

    public void AssertEqualsCustom(String test, String tes2){

    }

    void test3(){
        assertEquals
                ("assertEquals(\"hehe, ohoho\")","ohoh")
        ;
    }
}
