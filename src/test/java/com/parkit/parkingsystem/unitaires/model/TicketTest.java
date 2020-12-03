package com.parkit.parkingsystem.unitaires.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

/**
 * The type Ticket test.
 *
 * @author lilas
 * @see Ticket
 * @see ParkingSpot The type Ticket test.
 */
@DisplayName("TicketModelTest")
public class TicketTest {
	/**
	 * The Ticket.
	 */
	Ticket ticket = null;
	/**
	 * The Parking spot.
	 */
	ParkingSpot parkingSpot;

	/**
	 * Test ticket.
	 */
	@Test
	public void testTicket() {
		/**
		 * Given Initialized Ticket.
		 */
		ticket = new Ticket();
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();

		/**
		 * When a ticket is set
		 */
		ticket.setRecurrentUser(false);
		ticket.setId(1234);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setPrice(12.23);
		ticket.setVehicleRegNumber("abcdef");
		ticket.setParkingSpot(parkingSpot);
		/**
		 * Then verify ticket has the right information in all the getMethod()
		 */
		assertEquals(ticket.getRecurrentUser(), false);
		assertEquals(ticket.getId(), 1234);
		assertEquals(ticket.getVehicleRegNumber(), "abcdef");
		assertEquals(ticket.getInTime(), inTime);
		assertEquals(ticket.getOutTime(), outTime);
		assertEquals(ticket.getPrice(), 12.23);
		assertEquals(ticket.getParkingSpot(), parkingSpot);

	}

}