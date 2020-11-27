package com.parkit.parkingsystem.dao;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TicketDAOIT {

    private static TicketDAO ticketDAO;
    private static DataBasePrepareService databasePrepareService;

    @BeforeEach
    void setUpPerTest() throws Exception {
        ticketDAO = new TicketDAO();
        databasePrepareService = new DataBasePrepareService();
        databasePrepareService.clearDataBaseEntries();
    }

    @AfterEach
    void tearDown() throws Exception{
        databasePrepareService.clearDataBaseEntries();

    }

    @Test
    void saveTicket() {
        ticketDAO.dataBaseConfig = new DataBaseTestConfig();
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

       ticketDAO.saveTicket(ticket);

       Ticket getTicketSaved = ticketDAO.getTicket("ABCDEF");

       assertEquals(1,getTicketSaved.getParkingSpot().getId());
       assertEquals(ParkingType.CAR,getTicketSaved.getParkingSpot().getParkingType());
       assertEquals(false,getTicketSaved.getParkingSpot().isAvailable());
       assertEquals(0.0,getTicketSaved.getPrice());
       assertNotNull(getTicketSaved.getInTime());
       assertNotNull(getTicketSaved.getOutTime());







    }

    @Test
    void getTicket() {
    }

    @Test
    void updateTicket() {
    }
}