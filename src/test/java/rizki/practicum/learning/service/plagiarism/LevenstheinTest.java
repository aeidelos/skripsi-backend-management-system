package rizki.practicum.learning.service.plagiarism;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class LevenstheinTest {

    String origin;
    String comparator;

    Levensthein levensthein;

    @Before
    public void before() {
        origin = new String();
        comparator = new String();
        levensthein = new Levensthein();
    }


    @Test(expected = NullPointerException.class)
    public void LD_S1_ISNULL() {
        origin = null;
        comparator = "random text";
        levensthein.distance(origin, comparator);
    }

    @Test(expected = NullPointerException.class)
    public void LD_S2_ISNULL() {
        origin = "random text";
        comparator = null;
        levensthein.distance(origin, comparator);
    }

    @Test
    public void LD_S1_ISEQUALS_S2() {
        origin = "same text here";
        comparator = "same text here";
        int result = levensthein.distance(origin, comparator);
        Assert.assertEquals(origin.length(), comparator.length());
        Assert.assertEquals(result, 0);
    }

    @Test
    public void LD_S1_LENGTH_IS_ZERO() {
        origin = "";
        comparator = "random text";
        int result = levensthein.distance(origin, comparator);
        Assert.assertEquals(result, comparator.length());
    }

    @Test
    public void LD_S2_LENGTH_IS_ZERO() {
        origin = "random text";
        comparator = "";
        int result = levensthein.distance(origin, comparator);
        Assert.assertEquals(result, origin.length());
    }

    @Test
    public void LD_S1_S2_WITHOUT_LOOP() {
        origin = "a";
        comparator = "b";
        int result = levensthein.distance(origin,comparator);
        Assert.assertEquals(origin.length(), comparator.length());
        Assert.assertEquals(result, 1);
    }

    @Test
    public void LD_S1_S2_LOOP_NODE_12() {
        origin = "abc";
        comparator = "x";
        int result = levensthein.distance(origin, comparator);
        Assert.assertEquals(result, 3);
    }

    @Test
    public void LD_S1_S2_LOOP_NODE_13() {
        origin = "a";
        comparator = "bce";
        int result = levensthein.distance(origin, comparator);
        Assert.assertEquals(result,3);
    }

    @Test
    public void LD_S1_S2_LOOP_NODE_13_BYPASS_NODE_15() {
        origin = "ikr";
        comparator = "ikan";
        int result = levensthein.distance(origin, comparator);
        Assert.assertEquals(result, 2);
    }

}