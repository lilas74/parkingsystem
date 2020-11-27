package com.parkit.parkingsystem.integration;


import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The type Parking data base it.
 * @author lilas
 */
@ExtendWith( MockitoExtension.class )
public class ParkingDataBaseIT {

    private static  DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();


    private static DataBasePrepareService dataBasePrepareService;
    @Mock
    private static InputReaderUtil inputReaderUtil;
    @Spy
    private static TicketDAO ticketDAO;
    @Spy
    private static ParkingSpotDAO parkingSpotDAO;
    /**
     * The Parking service.
     */
    ParkingService parkingService;

    @BeforeAll
    private static void setUp() throws Exception {
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @AfterAll
    private static void tearDown() {
        dataBasePrepareService.clearDataBaseEntries();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        dataBasePrepareService.clearDataBaseEntries();
    }

    /**
     * Test parking a car.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParkingACar() throws Exception {
        /**
         * Given a new parking service
         */
         parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

        /**
         * When an incoming vehicle process is called
         */
        parkingService.processIncomingVehicle();

        /**
         * Then : check that a ticket is actually saved in DB and Parking table is updated with availability
         */

        /*******************Ticket****/
        Ticket ticket = ticketDAO.getTicket(inputReaderUtil.readVehicleRegistrationNumber());
        assertEquals(inputReaderUtil.readVehicleRegistrationNumber(), ticket.getVehicleRegNumber());
        assertNotNull(ticket.getParkingSpot());
        assertFalse(ticket.getParkingSpot().isAvailable());
        assertNotNull(ticket);//generated ticket
        assertNotNull(ticket.getInTime());//getInTime saved
        verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));

        /********************Parking Spot****/
        ParkingSpot parkingSpot = ticket.getParkingSpot();
        assertNotNull(parkingSpot);
        assertEquals(parkingSpot.isAvailable(), false);
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));

        //TODO: check that a ticket is actually saved in DB and Parking table is updated with availability
    }

    /**
     * Test parking lot exit.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParkingLotExit() throws Exception {
        /**
         * Given a new parking service
         */
        testParkingACar();
         parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        /**
         * When the exiting process is called
         */
        Thread.sleep(1000);
        parkingService.processExitingVehicle();
        /**
         * Then : check that the fare generated and out time are populated correctly in the database
         */
        Ticket ticket = ticketDAO.getTicket(inputReaderUtil.readVehicleRegistrationNumber());
        ticket.setPrice(Fare.CAR_RATE_PER_HOUR);
        /*******************Fare****/
        ticketDAO.updateTicket(ticket);
        assertEquals(Fare.CAR_RATE_PER_HOUR, ticket.getPrice());
        /*******************Time-Out****/
        assertNotNull(ticket.getOutTime());


        //TODO: check that the fare generated and out time are populated correctly in the database


    }
    @Test
    public void testReccurentUserExiting_true() {}

    @Test
    public void testReccurentUserExiting_false(){}
}
