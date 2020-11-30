package com.parkit.parkingsystem.unitaires.service;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.integration.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

/**
 * The type Parking Service test.
 *
 * @author lilas
 * @see ParkingService
 */
@ExtendWith( MockitoExtension.class )
@DisplayName( "Test for the parking Service" )
public class ParkingServiceTest {

    private static ParkingService parkingService;

    @Mock
    private static InputReaderUtil inputReaderUtil;
    @Mock
    private static ParkingSpotDAO parkingSpotDAO;
    @Mock
    private static TicketDAO ticketDAO;

    @BeforeEach
    private void setUpPerTest() {


        try {
            Mockito.when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
            Ticket ticket = new Ticket();
            ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
            ticket.setParkingSpot(parkingSpot);
            ticket.setVehicleRegNumber("ABCDEF");

            Mockito.when(parkingSpotDAO.updateParking(ArgumentMatchers.any(ParkingSpot.class))).thenReturn(true);

            parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to set up test mock objects");
        }
    }

    /**
     * Process exiting vehicle test.
     */
    @DisplayName( "Test the exiting process of any Vehicle" )
    @Test
    public void processExitingVehicleTest() {
        /*
         * Given: an user want to exit his vehicle from the parking
         */
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

        /*
         * When a ticket is set
         */
        Ticket ticket = new Ticket();
        ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");
        Mockito.when(ticketDAO.getTicket(ArgumentMatchers.anyString())).thenReturn(ticket);
        Mockito.when(ticketDAO.updateTicket(ArgumentMatchers.any(Ticket.class))).thenReturn(true);
        parkingService.processExitingVehicle();

        /*
         * Then verify the system update parking spot available
         */
        Mockito.verify(parkingSpotDAO, Mockito.times(1)).updateParking(ArgumentMatchers.any(ParkingSpot.class));
    }


    /**
     * Process incoming car test.
     */
    @DisplayName( "Test the incoming car process to verify if the parkingSpot is update and the ticket saved" )
    @Test
    public void processIncomingCarTest() {
        /*
         * Given: an user want to park his car in the parking
         */
        Mockito.when(inputReaderUtil.readSelection()).thenReturn(1);

        /*
         * When the same user get any spot and the parking process for incoming car is done
         */
        Mockito.when(parkingSpotDAO.getNextAvailableSlot((ParkingType.CAR))).thenReturn(1);
        Mockito.when(ticketDAO.saveTicket(ArgumentMatchers.any())).thenReturn(true);
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
        Ticket ticket = new Ticket();
        ticket.setInTime(inTime);
        parkingService.processIncomingVehicle();

        /*
         * Then verify the parkingSpot is update and the ticket saved in DAO
         */
        Mockito.verify(parkingSpotDAO, Mockito.times(1)).updateParking(ArgumentMatchers.any(ParkingSpot.class));
        Mockito.verify(ticketDAO, Mockito.times(1)).saveTicket(ArgumentMatchers.any(Ticket.class));
    }

    /**
     * Process incoming bike test.
     */
    @DisplayName( "Test of the incoming bike process to verify if the parkingSpot is update and the ticket saved" )
    @Test
    public void processIncomingBikeTest() {
        /*
         * Given: an user want to park his bike in the parking
         */
        Mockito.when(inputReaderUtil.readSelection()).thenReturn(2);
        Mockito.when(parkingSpotDAO.getNextAvailableSlot((ParkingType.BIKE))).thenReturn(4);

        /*
         * When the same user get any spot and the parking process for incoming car is done
         */
        parkingService.processIncomingVehicle();

        /*
         * Then verify the parkingSpot is update and the ticket saved in DAO
         */
        Mockito.verify(parkingSpotDAO, Mockito.times(1)).updateParking(ArgumentMatchers.any(ParkingSpot.class));
        Mockito.verify(ticketDAO, Mockito.times(1)).saveTicket(ArgumentMatchers.any(Ticket.class));
    }
}


