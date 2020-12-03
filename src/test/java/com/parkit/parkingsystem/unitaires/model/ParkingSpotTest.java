package com.parkit.parkingsystem.unitaires.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;

/**
 * The type Parking spot test.
 *
 * @author lilas
 * @see ParkingType
 * @see ParkingSpot The type Parking spot test.
 */
@DisplayName("ParkingSpotTest")
public class ParkingSpotTest {
	/**
	 * The Parking spot.
	 */
	ParkingSpot parkingSpot = null;

	/**
	 * Test car parking spot.
	 */
	@Test
	public void testCarParkingSpot() {
		/**
		 * Given Initialized Car ParkingSpot
		 */
		parkingSpot = new ParkingSpot(1, ParkingType.CAR, true);

		/**
		 * When a parking spot is set
		 */
		parkingSpot.setId(1);
		parkingSpot.setParkingType(ParkingType.CAR);
		parkingSpot.setAvailable(false);

		/**
		 * Then verify ticket has the right information in all the getMethod()
		 */
		assertEquals(parkingSpot.getId(), 1);
		assertEquals(parkingSpot.getParkingType(), ParkingType.CAR);
		assertEquals(parkingSpot.isAvailable(), false);
	}

	/**
	 * Test bike parking spot.
	 */
	@Test
	public void testBikeParkingSpot() throws Exception {
		/**
		 * Given Initialized Bike ParkingSpot
		 */
		parkingSpot = new ParkingSpot(1, ParkingType.BIKE, true);

		/**
		 * When a parking spot is set
		 */
		parkingSpot.setId(4);
		parkingSpot.setParkingType(ParkingType.BIKE);
		parkingSpot.setAvailable(false);

		/**
		 * Then verify ticket has the right information in all the getMethod()
		 */
		assertEquals(parkingSpot.getId(), 4);
		assertEquals(parkingSpot.getParkingType(), ParkingType.BIKE);
		assertEquals(parkingSpot.isAvailable(), false);
	}
	@Test
	public void testHashMethod() {
		parkingSpot = new ParkingSpot(1, ParkingType.CAR, true);
		assertEquals(parkingSpot.hashCode(), 1);
	}
}