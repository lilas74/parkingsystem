package com.parkit.parkingsystem.unitaires.service;

import java.util.Date;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;

/**
 * The type Fare calculator service test.
 */
@Tag("FareCalculatorServiceTest")
@DisplayName("Successfully calculate Fare for Car Or Bike whatever the duration")
public class FareCalculatorServiceTest {

	private static FareCalculatorService fareCalculatorService;
	private Ticket ticket;

	@BeforeAll
	private static void setUp() {
		fareCalculatorService = new FareCalculatorService();
	}

	@BeforeEach
	private void setUpPerTest() {
		ticket = new Ticket();
	}

	/**
	 * Calculate fare carwith different hours.
	 *
	 * @param arg
	 *            the arg
	 */
	@ParameterizedTest(name = "{index} => {0} x CAR_RATE_PER_HOUR doit être égal à {0} * 1.5")
	@ValueSource(ints = {1, 2, 42, 101, 50})
	@DisplayName("Calculate Fare for a car with different hours")
	public void calculateFareCarwithDifferentHours(int arg) {
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * arg * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		Assertions.assertEquals(arg * Fare.CAR_RATE_PER_HOUR, ticket.getPrice());
	}

	/**
	 * Calculate fare bike with different hours.
	 *
	 * @param arg
	 *            the arg
	 */
	@ParameterizedTest(name = "{index} => {0} x CAR_RATE_PER_HOUR doit être égal à {0}")
	@ValueSource(ints = {1, 2, 42, 101, 50})
	@DisplayName("Calculate Fare for a bike with different hours")
	public void calculateFareBikeWithDifferentHours(int arg) {
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * arg * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		Assertions.assertEquals(arg * Fare.BIKE_RATE_PER_HOUR, ticket.getPrice());
	}

	/**
	 * Calculate fare null type.
	 */
	@Test
	@DisplayName("This test should return a null pointer Exception if parking type is null")
	public void calculateFareNullType() {
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, null, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		Assertions.assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));

	}

	/**
	 * Calculate fare bike with future in time.
	 */
	@Test
	@DisplayName("This test should return an illegal argument exception for a car if the out time is before the in time")
	public void calculateFareBikeWithFutureInTime() {
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() + (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		Assertions.assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
	}

	/**
	 * Calculate fare car with future in time.
	 */
	@Test
	@DisplayName("This test should return an illegal argument exception for a bike if the out time is before the in time")
	public void calculateFareCarWithFutureInTime() {
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() + (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		Assertions.assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
	}

	/**
	 * Calculate fare bike with less than one hour parking time.
	 */
	@Test
	@DisplayName("this test calculate fare for a bike with less than one hour but more than 30 minutes")
	public void calculateFareBikeWithLessThanOneHourParkingTime() {
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (45 * 60 * 1000));// 45 minutes parking time should give 3/4th
																		// parking fare
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		Assertions.assertEquals((0.75 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice());
	}

	/**
	 * Calculate fare car with less than one hour parking time.
	 */
	@Test
	@DisplayName("this test calculate fare for a car with less than one hour but more than 30 minutes")
	public void calculateFareCarWithLessThanOneHourParkingTime() {
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (45 * 60 * 1000));// 45 minutes parking time should give 3/4th
																		// parking fare
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		Assertions.assertEquals((Math.round(0.75 * Fare.CAR_RATE_PER_HOUR * 100.0) / 100.0), ticket.getPrice());
	}

	/**
	 * Calculate fare car with more than a day parking time.
	 */
	@Test
	@DisplayName("This test calculate fare for a 24 hours parking time for a car")
	public void calculateFareCarWithMoreThanADayParkingTime() {
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (24 * 60 * 60 * 1000));// 24 hours parking time should give 24 *
																			// parking fare per hour
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		Assertions.assertEquals((24 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice());
	}

	/**
	 * Calculate fare bike with more than a day parking time.
	 */
	@Test
	@DisplayName("This test calculate fare for a 24 hours parking time for a bike")
	public void calculateFareBikeWithMoreThanADayParkingTime() {
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (24 * 60 * 60 * 1000));// 24 hours parking time should give 24 *
																			// parking fare per hour
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		Assertions.assertEquals((24 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice());
	}

	/**
	 * Calculate free fare car with less than thirty minutes parking time.
	 */
	@Test
	@DisplayName("Calculate fare for a car with less than thirty minutes parking time should return 0")
	public void calculateFreeFareCarWithLessThanThirtyMinutesParkingTime() {
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (30 * 60 * 1000));// 30 minutes parking time should give 0
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		Assertions.assertEquals(Fare.FREE_CAR_RATE_PER_HOUR, ticket.getPrice());
	}

	/**
	 * Calculate free fare bike with less than thirty minutes parking time.
	 */
	@Test
	@DisplayName("Calculate fare for a bike with less than thirty minutes parking time should return 0")
	public void calculateFreeFareBikeWithLessThanThirtyMinutesParkingTime() {
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (30 * 60 * 1000));// 30 minutes parking time should give 0
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		Assertions.assertEquals(0.0, ticket.getPrice());
	}

	/**
	 * Calculate fare car regular user.
	 */
	@Test
	@DisplayName("Calculate fare for a recurrent user with a car should return a 5% Discount price")
	public void calculateFareCarRegularUser() {
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		ticket.setRecurrentUser(true);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		Assertions.assertEquals(Math.round(0.95 * Fare.CAR_RATE_PER_HOUR * 100.0) / 100.0, ticket.getPrice());

	}

	/**
	 * Calculate fare bike regular user.
	 */
	@Test
	@DisplayName("Calculate fare for a recurrent user with a bike should return a 5% Discount price")
	public void calculateFareBikeRegularUser() {
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);
		ticket.setRecurrentUser(true);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket);
		Assertions.assertEquals(0.95 * Fare.BIKE_RATE_PER_HOUR, ticket.getPrice());
	}

}
