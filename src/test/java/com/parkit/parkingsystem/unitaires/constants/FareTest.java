package com.parkit.parkingsystem.unitaires.constants;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.unitaires.service.FareCalculatorServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
}
