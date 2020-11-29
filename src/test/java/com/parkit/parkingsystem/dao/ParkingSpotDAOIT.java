package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ParkingSpotDAOIT{
   private static DataBasePrepareService dataBasePrepareService;
   private static ParkingSpotDAO parkingSpotDAO;


    @BeforeEach
    void setUpPerTest() throws Exception {
        parkingSpotDAO = new ParkingSpotDAO();
        dataBasePrepareService = new DataBasePrepareService();
        parkingSpotDAO.dataBaseConfig = new DataBaseTestConfig();
        dataBasePrepareService.clearDataBaseEntries();
    }
    @AfterAll
    private static void tearDown(){
        dataBasePrepareService.clearDataBaseEntries();
    }
    @Test
    void getNextAvailableCarSlotTest() {
        ParkingType car = ParkingType.CAR;
        ParkingSpot parkingSpot = new ParkingSpot(1,car,false);
       parkingSpotDAO.dataBaseConfig = new DataBaseTestConfig();
        parkingSpotDAO.updateParking(parkingSpot);
        assertEquals(2,parkingSpotDAO.getNextAvailableSlot(car));
    }
    @Test
    void getNextAvailableCarSlotEmptyDataTest() {

        assertEquals(1,parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));
    }
    @Test
    void getNextAvailableBikeSlotEmptyDataTest() {

        assertEquals(4,parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE));
    }
    @Test
    void getNextAvailableBikeSlotTest() {
        ParkingType bike = ParkingType.BIKE;
        ParkingSpot parkingSpot = new ParkingSpot(4,bike,false);
       parkingSpotDAO.dataBaseConfig = new DataBaseTestConfig();
        parkingSpotDAO.updateParking(parkingSpot);
        assertEquals(5,parkingSpotDAO.getNextAvailableSlot(bike));
    }
    @Test
    void getNextAvailableBikeSlotErrorTest() {
        ParkingType bike = ParkingType.BIKE;
        ParkingSpot parkingSpot = new ParkingSpot(4,bike,false);
        ParkingSpot parkingSpot1 = new ParkingSpot(5,bike,false);
        parkingSpotDAO.dataBaseConfig = new DataBaseTestConfig();
        parkingSpotDAO.updateParking(parkingSpot);
        parkingSpotDAO.updateParking(parkingSpot1);
        assertEquals(0, parkingSpotDAO.getNextAvailableSlot(bike));

    }
    @Test
    void updateParkingCarTest() {
        ParkingType car = ParkingType.CAR;
        ParkingSpot parkingSpot = new ParkingSpot(1,car,false);
        boolean result =  parkingSpotDAO.updateParking(parkingSpot);
        assertTrue(result);
        assertEquals(2,parkingSpotDAO.getNextAvailableSlot(car));
    }
    @Test
    void updateParkingBikeTest() {
        ParkingType bike = ParkingType.BIKE;
        ParkingSpot parkingSpot = new ParkingSpot(4,bike,false);
        boolean result =  parkingSpotDAO.updateParking(parkingSpot);
        assertTrue(result);
        assertEquals(5,parkingSpotDAO.getNextAvailableSlot(bike));
    }
}