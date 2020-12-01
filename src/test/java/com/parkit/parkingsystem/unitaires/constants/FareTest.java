package com.parkit.parkingsystem.unitaires.constants;

import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.unitaires.service.FareCalculatorServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.lang.reflect.Modifier;
import java.lang.reflect.Field;


import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FareTest extends FareCalculatorServiceTest {

    @ParameterizedTest( name = "{index} => {0} x CAR_RATE_PER_HOUR doit être égal à {0} * 1.5" )
    @ValueSource( ints = {1, 2, 42, 101, 50} )
    @DisplayName( "Calculate fare are set correctly for a car" )
    public void VerifyFareAreSetCorretlyForACarTest(int arg) {
        double result = arg * Fare.CAR_RATE_PER_HOUR;

        Assertions.assertEquals(arg * 1.5, result);
    }
    @ParameterizedTest( name = "{index} => {0} x CAR_RATE_PER_HOUR doit être égal à {0} * 1" )
    @ValueSource( ints = {1, 2, 42, 101, 50} )
    @DisplayName( "Calculate Fare for a bike are set correctly " )
    public void VerifyFareAreSetCorretlyForABikeTest(int arg) {
        double result = arg * Fare.BIKE_RATE_PER_HOUR;

        Assertions.assertEquals(arg * 1, result);
    }
    @ParameterizedTest( name = "{index} => {0} x CAR_RATE_PER_HOUR doit être égal à {0} * 0" )
    @ValueSource( ints = {1, 2, 42, 101, 50} )
    @DisplayName( "Calculate Free Fare for a bike " )
    public void VerifyFreeFareAreSetCorretlyForABikeTest(int arg) {
        double result = arg * Fare.FREE_BIKE_RATE_PER_HOUR;

        Assertions.assertEquals(0, result);
    }
    @ParameterizedTest( name = "{index} => {0} x CAR_RATE_PER_HOUR doit être égal à {0} * 0" )
    @ValueSource( ints = {1, 2, 42, 101, 50} )
    @DisplayName( "Calculate Free Fare for a car " )
    public void VerifyFreeFareAreSetCorretlyForACarTest(int arg) {
        double result = arg * Fare.FREE_CAR_RATE_PER_HOUR;

        Assertions.assertEquals(0, result);
    }
    @Test
    public void verifyFareConstantArePublicStaticFinalTest() throws Exception{
        Class cls = Fare.class;
        Field field = cls.getField("CAR_RATE_PER_HOUR");
        Field field1 = cls.getField("BIKE_RATE_PER_HOUR");
        Field field2= cls.getField("FREE_CAR_RATE_PER_HOUR");
        Field field3 = cls.getField("FREE_CAR_RATE_PER_HOUR");

        int modifiers = field.getModifiers();
        int modifiers1 = field1.getModifiers();
        int modifiers2= field2.getModifiers();
        int modifiers3 = field3.getModifiers();

        assertTrue(Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers));
        assertTrue(Modifier.isPublic(modifiers1) && Modifier.isStatic(modifiers1) && Modifier.isFinal(modifiers1));
        assertTrue(Modifier.isPublic(modifiers2) && Modifier.isStatic(modifiers2) && Modifier.isFinal(modifiers2));
        assertTrue(Modifier.isPublic(modifiers3) && Modifier.isStatic(modifiers3) && Modifier.isFinal(modifiers3));
    }

}
