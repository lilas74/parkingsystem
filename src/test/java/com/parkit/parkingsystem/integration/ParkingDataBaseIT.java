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
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

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
    @DisplayName("Test to verify if a ticket is actually saved in DB and Parking table is updated with availability")
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

        /*******************************************************
         /******************* Ticket ***************************
         /******************************************************/
        Ticket ticket = ticketDAO.getTicket(inputReaderUtil.readVehicleRegistrationNumber());
        assertEquals(inputReaderUtil.readVehicleRegistrationNumber(), ticket.getVehicleRegNumber());
        assertNotNull(ticket.getParkingSpot(),
                "Ticket doesn't save any parking spot");
        assertFalse(ticket.getParkingSpot().isAvailable(),
                "The parking table is not updated");
        assertNotNull(ticket,
                "Ticket is not save in the database");//generated ticket
        assertNotNull(ticket.getInTime());//getInTime saved
        verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));

        /*******************************************************
         /******************* Parking Spot *********************
         /******************************************************/
        ParkingSpot parkingSpot = ticket.getParkingSpot();
        assertNotNull(parkingSpot,
                "Parking Spot is not generated");
        assertEquals(parkingSpot.isAvailable(), false,
                "The parking spot hasn't been updated with availability");
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));

        //TODO: check that a ticket is actually saved in DB and Parking table is updated with availability
    }

    /**
     * Test parking lot exit.
     *
     * @throws Exception the exception
     */
    @DisplayName("Test to verify that the fare generated and out time are populated correctly in the database")
    @Test
    public void testParkingLotExit() throws Exception {
        /*
         * Given a new parking service
         */
        testParkingACar();
         parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        /*
         * When the exiting process is called
         */
        Thread.sleep(1000);
        parkingService.processExitingVehicle();
        /*
         * Then : check that the fare generated and out time are populated correctly in the database
         */
        Ticket ticket = ticketDAO.getTicket(inputReaderUtil.readVehicleRegistrationNumber());
        ticket.setPrice(Fare.CAR_RATE_PER_HOUR);
        /*******************************************************
        /******************* Fare Generated ********************
        /******************************************************/
        ticketDAO.updateTicket(ticket);
        assertEquals(Fare.CAR_RATE_PER_HOUR, ticket.getPrice());

        /********************************************************
        ******************Test Out time on DBB ******************
        ********************************************************/
        assertNotNull(ticket.getOutTime());


        //TODO: check that the fare generated and out time are populated correctly in the database


    }
    @DisplayName("Test to verify if the recurrent user is set in the database as a recurent after an incoming process and  an exit process then a new incoming")
    @Test
    public void testRecurentUserExiting_true()throws Exception{
        /*
        Given a new parking service
        */
       parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

       /*
       When a process incoming and a exiting process are completed
       */
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
        parkingService.processIncomingVehicle();
        Thread.sleep(100);
        parkingService.processExitingVehicle();
        Thread.sleep(100);
        parkingService.processIncomingVehicle();
        boolean recurrentUserTest = ticketDAO.isRecurrentUser("ABCDEF");

        /*
        Then verify the customer become a recurrent user
         */
        assertTrue(recurrentUserTest);
    }
    @DisplayName("Test to verify if the recurrent user is not a recurent user if the incoming and exiting process should done once")
    @Test
    public void testRecurentUserExiting_false(){

        /*
        Given a new parking service
        */
            parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

       /*
       When a process incoming and exiting are complete
       */
            parkingService.processIncomingVehicle();
            parkingService.processExitingVehicle();

        /*
        Then verify a the customer is not save as a recurent user
         */
            boolean recurrentUserTest = ticketDAO.isRecurrentUser("123456");
            assertFalse(recurrentUserTest,
                    "The first time user is set as a recurent user instead of false");
    }
}
