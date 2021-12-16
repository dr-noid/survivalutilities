package com.ebkir.survivalutilities;

import com.ebkir.survivalutilities.utils.ClassCounter;
import org.junit.Test;

public class SurvivalUtilitiesTest {

    @Test
    public void testReflectionLibrary() {
        var x = ClassCounter.findAllClassesUsingReflection(
                "com.ebkir.survivalutilities.commands.homev2");

        assert x != null;
        System.out.println(x.size());
    }
}
