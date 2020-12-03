package com.parkit.parkingsystem.integration.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;

/**
 * The type Ticket daoit.
 *
 * @author lilas The type Ticket DAO IT.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Test for the Ticket DAO Integration")
class TicketDAOIT {

	private static TicketDAO ticketDAO;
	private static DataBasePrepareService databasePrepareService;
	private static ParkingSpotDAO parkingSpotDAO;

	@Mock
	private static InputReaderUtil inputReaderUtil;

	/**
	 * Sets up per test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeEach
	void setUpPerTest() throws Exception {

		ticketDAO = new TicketDAO();

		databasePrepareService = new DataBasePrepareService();
		databasePrepareService.clearDataBaseEntries();
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		databasePrepareService = new DataBasePrepareService();
		databasePrepareService.clearDataBaseEntries();

	}

	/**
	 * Save ticket test .
	 */
	@DisplayName("Test the save method to verify the values are correctly registered")
	@Test
	void saveTicketTest_true() {
		ticketDAO.dataBaseConfig = new DataBaseTestConfig();
		/*
		 * Given a new ticketDAO instance and a ticket saved
		 */
		Ticket ticket = new Ticket();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setParkingSpot(parkingSpot);
		ticket.setVehicleRegNumber("ABCDEF");
		ticket.setPrice(0.0D);
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setRecurrentUser(false);
		/*
		 * When the ticket is saved by the TicketDAO
		 */

		ticketDAO.saveTicket(ticket);
		/*
		 * Then verify the ticket was saved successfully and the parking spot
		 */
		Ticket getTicketSaved = ticketDAO.getTicket("ABCDEF");

		assertEquals(1, getTicketSaved.getParkingSpot().getId());
		assertEquals(ParkingType.CAR, getTicketSaved.getParkingSpot().getParkingType());
		assertEquals(false, getTicketSaved.getParkingSpot().isAvailable());
		assertEquals(0.0, getTicketSaved.getPrice());
		assertNotNull(getTicketSaved.getInTime());
		assertNotNull(getTicketSaved.getOutTime());

	}

	/**
	 * Save ticket test .
	 */
	@DisplayName("Test the save method to verify the values are correctly registered")
	@Test
	void saveTicketTest_false() {
		ticketDAO.dataBaseConfig = new DataBaseTestConfig();
		/*
		 * Given a new ticketDAO instance with a wrong parkingSpot
		 */
		Ticket ticket = new Ticket();
		ParkingSpot parkingSpot = new ParkingSpot(4, ParkingType.CAR, false);

		ticket.setParkingSpot(parkingSpot);
		ticket.setVehicleRegNumber("ABCDEF");
		ticket.setPrice(0.0D);
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setRecurrentUser(false);
		/*
		 * When the ticket is saved by the TicketDAO
		 */

		boolean isSaved = ticketDAO.saveTicket(ticket);
		/*
		 * Then verify the ticket was not saved successfully
		 */

		assertFalse("This test should return false", isSaved);
		assertEquals(0, ticket.getId(), "this test should return 0 because the ticket should not be saved");

	}

	/**
	 * Update ticket test.
	 *
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	@DisplayName("Test UpdateTicket method to set the value  ")
	@Test
	void updateTicketTest() throws InterruptedException {
		ticketDAO.dataBaseConfig = new DataBaseTestConfig();
		/*
		 * Given a new ticketDAO instance and a ticket saved
		 */
		Ticket ticket = new Ticket();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setParkingSpot(parkingSpot);
		ticket.setVehicleRegNumber("ABCDEF");
		ticket.setPrice(0.0D);

		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));

		ticket.setInTime(inTime);
		ticket.setOutTime(null);
		ticket.setRecurrentUser(false);
		ticket.setPrice(0);
		boolean savedticketDAO = ticketDAO.saveTicket(ticket);
		Thread.sleep(100);
		Date outTime = new Date();
		ticket.setOutTime(outTime);
		ticket.setPrice(Fare.CAR_RATE_PER_HOUR);

		/*
		 * When the ticket is updated by the TicketDAO
		 */
		boolean ticketUpdated = ticketDAO.updateTicket(ticket);
		Ticket ticketUp = ticketDAO.getTicket("ABCDEF");

		/*
		 * Then verify the ticket was saved successfully and the parking spot
		 */
		assertNotNull(ticket.getOutTime());
		assertEquals(1.5, ticket.getPrice(), "the price should be updated to Fare.CAR_RATE_PER_HOUR");
		assertTrue(ticketUpdated);
	}

	/**
	 * Is recurrent user test.
	 */
	@DisplayName("Test to verify isRecurrentUser process")
	@Test
	void isRecurrentUserTest() {
		ticketDAO.dataBaseConfig = new DataBaseTestConfig();
		/*
		 * Given a new USER instance and a ticket saved
		 */
		/****************************************
		 * FIRST TIME ***************************
		 ***************************************/
		Ticket ticket_first_time = new Ticket();
		ParkingSpot parkingSpot_first_time = new ParkingSpot(1, ParkingType.CAR, false);

		ticket_first_time.setParkingSpot(parkingSpot_first_time);
		ticket_first_time.setVehicleRegNumber("ABCDEF");
		ticket_first_time.setPrice(0.0D);

		Date inTime_first = new Date();
		inTime_first.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime_first = new Date();
		ticket_first_time.setInTime(inTime_first);
		ticket_first_time.setOutTime(outTime_first);
		ticketDAO.saveTicket(ticket_first_time);

		/****************************************
		 * SECOND TIME **************************
		 ***************************************/
		Ticket ticket_second_time = new Ticket();
		ParkingSpot parkingSpot_second_time = new ParkingSpot(1, ParkingType.CAR, false);

		ticket_second_time.setParkingSpot(parkingSpot_second_time);
		ticket_second_time.setVehicleRegNumber("ABCDEF");
		ticket_second_time.setPrice(0.0D);

		Date inTime_second = new Date();
		inTime_first.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime_second = new Date();
		ticket_second_time.setInTime(inTime_second);
		ticket_second_time.setOutTime(outTime_second);
		ticketDAO.saveTicket(ticket_second_time);

		/*
		 * When we try to get the TicketDAO
		 */
		boolean recurrentUserTest = ticketDAO.isRecurrentUser("ABCDEF");

		/*
		 * Then verify the ticket was saved successfully and the parking spot
		 */

		assertTrue("This test should return true to isRecurrentUser", recurrentUserTest);

	}

	/**
	 * Gets ticket test.
	 */
	@DisplayName("Test the save method to verify the values are correctly registered and getted")
	@Test
	void getTicketTest() {
		ticketDAO.dataBaseConfig = new DataBaseTestConfig();
		/*
		 * Given a new USER instance and a ticket saved
		 */
		Ticket ticket = new Ticket();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setParkingSpot(parkingSpot);
		ticket.setVehicleRegNumber("ABCDEF");
		ticket.setPrice(0.0D);

		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticketDAO.saveTicket(ticket);
		/*
		 * When we try to get the TicketDAO
		 */
		Ticket getTicket = ticketDAO.getTicket("ABCDEF");

		/*
		 * Then verify the ticket was saved successfully and the parking spot
		 */

		assertEquals(getTicket.getVehicleRegNumber(), "ABCDEF");
		assertEquals(getTicket.getRecurrentUser(), false);
		assertNotNull(getTicket.getOutTime());
		assertEquals(getTicket.getPrice(), 0);
		assertNotNull(getTicket.getInTime());

	}

}