package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class)
@DisplayName("Test for the Ticket DAO Integration")
class TicketDAOIT {

    private static TicketDAO ticketDAO;
    private static DataBasePrepareService databasePrepareService;
    private static ParkingSpotDAO parkingSpotDAO;

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeEach
    void setUpPerTest() throws Exception {

        ticketDAO = new TicketDAO();

        databasePrepareService = new DataBasePrepareService();
        databasePrepareService.clearDataBaseEntries();
    }

    @AfterEach
    void tearDown() throws Exception{
        databasePrepareService = new DataBasePrepareService();
        databasePrepareService.clearDataBaseEntries();

    }
    @DisplayName("Test the save method to verify the values are correctly registered")
    @Test
    void saveTicketTest() {
        ticketDAO.dataBaseConfig = new DataBaseTestConfig();
        /*
        *Given a new ticketDAO instance and a ticket saved
         */
        Ticket ticket = new Ticket();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

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

       assertEquals(1,getTicketSaved.getParkingSpot().getId());
       assertEquals(ParkingType.CAR,getTicketSaved.getParkingSpot().getParkingType());
       assertEquals(false,getTicketSaved.getParkingSpot().isAvailable());
       assertEquals(0.0,getTicketSaved.getPrice());
       assertNotNull(getTicketSaved.getInTime());
       assertNotNull(getTicketSaved.getOutTime());

    }

    @Test
    void getTicketTest() throws Exception{
//ARRANGE
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        parkingSpotDAO = new ParkingSpotDAO();
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

        // ACT
        parkingService.processIncomingVehicle();
        Ticket ticket = ticketDAO.getTicket("ABCDEF");

        // ASSERT
        assertEquals(ticket.getVehicleRegNumber(), "ABCDEF");
        assertEquals(ticket.getRecurrentUser(), false);
        assertEquals(ticket.getOutTime(), null);
        assertEquals(ticket.getPrice(), 0);
        assertNotNull(ticket.getInTime());

    }

    @Test
    void updateTicketTest() {
    }
}