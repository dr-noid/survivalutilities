package com.ebkir.survivalutilities;

import org.junit.Test;

public class SurvivalUtilitiesTest {

    @Test
    public void testReflectionLibrary() {
        var x = SurvivalUtilities.findAllClassesUsingReflection(
                "com.ebkir.survivalutilities.commands.homev2");

        assert x != null;
        System.out.println(x.size());
    }
}
