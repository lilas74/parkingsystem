package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.mockito.Mockito.*;

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
            when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
            Ticket ticket = new Ticket();
            ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
            ticket.setParkingSpot(parkingSpot);
            ticket.setVehicleRegNumber("ABCDEF");

            when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);

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
        /**
         * Given: an user want to exit his vehicle from the parking
         */
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

/**
 * When a ticket is set
 */
        Ticket ticket = new Ticket();
        ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");
        when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
        when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
        parkingService.processExitingVehicle();

        /**
         * Then verify the system update parking spot available
         */
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
    }


    /**
     * Process incoming car test.
     */
    @DisplayName( "Test the incoming car process to verify if the parkingSpot is update and the ticket saved" )
    @Test
    public void processIncomingCarTest() {
        /**
         * Given: an user want to park his car in the parking
         */
        when(inputReaderUtil.readSelection()).thenReturn(1);

        /**
         * When the same user get any spot and the parking process for incoming car is done
         */
        when(parkingSpotDAO.getNextAvailableSlot((ParkingType.CAR))).thenReturn(1);
        when(ticketDAO.saveTicket(any())).thenReturn(true);
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
        Ticket ticket = new Ticket();
        ticket.setInTime(inTime);
        parkingService.processIncomingVehicle();

        /**
         * Then verify the parkingSpot is update and the ticket saved in DAO
         */
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
        verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));
    }

    /**
     * Process incoming bike test.
     */
    @DisplayName( "Test of the incoming bike process to verify if the parkingSpot is update and the ticket saved" )
    @Test
    public void processIncomingBikeTest() {
        /**
         * Given: an user want to park his bike in the parking
         */
        when(inputReaderUtil.readSelection()).thenReturn(2);
        when(parkingSpotDAO.getNextAvailableSlot((ParkingType.BIKE))).thenReturn(4);

        /**
         * When the same user get any spot and the parking process for incoming car is done
         */
        parkingService.processIncomingVehicle();

        /**
         * Then verify the parkingSpot is update and the ticket saved in DAO
         */
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
        verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));
    }
}


