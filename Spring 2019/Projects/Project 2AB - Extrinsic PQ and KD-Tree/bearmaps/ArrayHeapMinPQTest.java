package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ArrayHeapMinPQTest {

    @Test
    public void addRemoveTest(){
        /** Tests add and size.
         *
         */
        ArrayHeapMinPQ<String> x = new ArrayHeapMinPQ<>();
        x.add("Lol", 2);
        x.add("Huh", 1);
        assertEquals(2, x.size());

        /** Tests removeSmallest()
         *
         */
        String removed = x.removeSmallest();
        assertEquals("Huh", removed);
        assertEquals(1, x.size());
        assertFalse(x.contains("Huh"));

        String removed2 = x.removeSmallest();
        assertEquals("Lol", removed2);
        assertFalse(x.contains("Lol"));
    }
}
