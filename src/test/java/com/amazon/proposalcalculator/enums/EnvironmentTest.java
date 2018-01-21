package com.amazon.proposalcalculator.enums;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EnvironmentTest {

    @Test
    public void isProd1(){

        boolean isProd = Environment.isEquivalentToProd("prod");

        assertTrue(isProd);
    }

    @Test
    public void isProd2(){

        boolean isProd = Environment.isEquivalentToProd("dr_full");

        assertTrue(isProd);
    }

    @Test
    public void isProd3(){

        boolean isProd = Environment.isEquivalentToProd("dr_optimized");

        assertTrue(isProd);
    }

    @Test
    public void isProd4(){

        boolean isProd = Environment.isEquivalentToProd("dr_inactive");

        assertTrue(isProd);
    }

    @Test
    public void isProd5(){

        boolean isProd = Environment.isEquivalentToProd("ha");

        assertTrue(isProd);
    }

    @Test
    public void isNonProd1(){

        boolean isProd = Environment.isEquivalentToProd("qa");

        assertFalse(isProd);
    }

    @Test
    public void isNonProd2(){

        boolean isProd = Environment.isEquivalentToProd("sandbox");

        assertFalse(isProd);
    }

    @Test
    public void isNonProd3(){

        boolean isProd = Environment.isEquivalentToProd("test");

        assertFalse(isProd);
    }

    @Test
    public void isNonProd4(){

        boolean isProd = Environment.isEquivalentToProd("non_prod");

        assertFalse(isProd);
    }

    @Test
    public void isNonProd5(){

        boolean isProd = Environment.isEquivalentToNonProd("qa");

        assertTrue(isProd);
    }

    @Test
    public void isNonProd6(){

        boolean isProd = Environment.isEquivalentToNonProd("sandbox");

        assertTrue(isProd);
    }

    @Test
    public void isNonProd7(){

        boolean isProd = Environment.isEquivalentToNonProd("test");

        assertTrue(isProd);
    }

    @Test
    public void isNonProd8(){

        boolean isProd = Environment.isEquivalentToNonProd("non_prod");

        assertTrue(isProd);
    }


}
