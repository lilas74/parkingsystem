package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * The type Parking spot DAOIT.
 * @author lilas
 */
class ParkingSpotDAOIT {
    private static DataBasePrepareService dataBasePrepareService;
    private static ParkingSpotDAO parkingSpotDAO;

    @AfterAll
    private static void tearDown() {
        dataBasePrepareService.clearDataBaseEntries();
    }

    /**
     * Sets up per test.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUpPerTest() throws Exception {
        parkingSpotDAO = new ParkingSpotDAO();
        dataBasePrepareService = new DataBasePrepareService();
        parkingSpotDAO.dataBaseConfig = new DataBaseTestConfig();
        dataBasePrepareService.clearDataBaseEntries();
    }

    /**
     * Gets next available car slot test.
     */
    @DisplayName( "Test to verify the nextAvailable spot with a car" )
    @Test
    void getNextAvailableCarSpotTest() {
        /*
        Given a car park in spot 1
         */
        ParkingType car = ParkingType.CAR;
        ParkingSpot parkingSpot = new ParkingSpot(1, car, false);
        /*
        When the parking is update
         */
        parkingSpotDAO.updateParking(parkingSpot);
          /*
        Then verify the first parking spot available for a car is the spot 2
         */
        assertEquals(2, parkingSpotDAO.getNextAvailableSlot(car));
    }

    /**
     * Gets next available car slot empty data test.
     */
    @DisplayName( "Test to verify the initial next available spot with empty data with a car" )
    @Test
    void getNextAvailableCarSpotEmptyDataTest() {
         /*
        Given
         */
        /*
        When
         */
        /*
        Then verify the first parking spot available for a car is the spot 1
         */
        assertEquals(1, parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));
    }

    /**
     * Gets next available bike slot empty data test.
     */
    @DisplayName( "Test to verify the initial next available bike slot with empty data" )
    @Test
    void getNextAvailableBikeSpotEmptyDataTest() {
        /*
        Given
         */
        /*
        When
         */
        /*
        Then verify the first parking spot available for a bike is the spot 4
         */
        assertEquals(4, parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE));
    }

    /**
     * Gets next available bike slot test.
     */
    @DisplayName( "Test to verify the nextAvailable slot with a bike" )
    @Test
    void getNextAvailableBikeSpotTest() {
        /*
        Given a bike park in spot 4
         */
        ParkingType bike = ParkingType.BIKE;
        ParkingSpot parkingSpot = new ParkingSpot(4, bike, false);
        /*
        When the parking is update
         */
        parkingSpotDAO.updateParking(parkingSpot);
         /*
        Then verify the first parking spot available for a bike is the spot 5
         */
        assertEquals(5, parkingSpotDAO.getNextAvailableSlot(bike));
    }

    /**
     * Gets next available bike slot error test.
     */
    @DisplayName( "Test to Gets next available bike slot error test." )
    @Test
    void getNextAvailableBikeSpotErrorTest() {
         /*
        Given a bike park full
         */
        ParkingType bike = ParkingType.BIKE;
        ParkingSpot parkingSpot = new ParkingSpot(4, bike, false);
        ParkingSpot parkingSpot1 = new ParkingSpot(5, bike, false);
        /*
        When the parking is update
         */
        parkingSpotDAO.updateParking(parkingSpot);
        parkingSpotDAO.updateParking(parkingSpot1);
         /*
        Then verify no parkingspot available is returned
         */
        assertEquals(0, parkingSpotDAO.getNextAvailableSlot(bike));

    }

    /**
     * Gets next available car spot error test.
     */
    @DisplayName( "Test to get next available car spot error test" )
    @Test
    void getNextAvailableCarSlotErrorTest() {
        /*
        Given a car park full
         */
        ParkingType car = ParkingType.CAR;
        ParkingSpot parkingSpot = new ParkingSpot(1, car, false);
        ParkingSpot parkingSpot1 = new ParkingSpot(2, car, false);
        ParkingSpot parkingSpot2 = new ParkingSpot(3, car, false);
         /*
        When the parking is update
         */
        parkingSpotDAO.updateParking(parkingSpot);
        parkingSpotDAO.updateParking(parkingSpot1);
        parkingSpotDAO.updateParking(parkingSpot2);
        /*
        Then verify no parkingspot available is returned
         */
        assertEquals(0, parkingSpotDAO.getNextAvailableSlot(car));

    }

    /**
     * Update parking car test.
     */
    @DisplayName( " Update parking car test." )
    @Test
    void updateParkingCarTest() {
        /*
        Given a parkingSpot
         */
        ParkingType car = ParkingType.CAR;
        ParkingSpot parkingSpot = new ParkingSpot(1, car, false);
        /*
        When the parking is update
         */
        boolean result = parkingSpotDAO.updateParking(parkingSpot);
        /*
        Then verify is update and next available parking spot is 2
         */
        assertTrue(result);
        assertEquals(2, parkingSpotDAO.getNextAvailableSlot(car));
    }

    /**
     * Update parking bike test.
     */
    @DisplayName( "Update parking bike test" )
    @Test
    void updateParkingBikeTest() {
        /*
        Given a parkingSpot
         */
        ParkingType bike = ParkingType.BIKE;
        ParkingSpot parkingSpot = new ParkingSpot(4, bike, false);
        /*
        When the parking is update
         */
        boolean result = parkingSpotDAO.updateParking(parkingSpot);
        /*
        Then verify is update and next available parking spot is 2
         */
        assertTrue(result);
        assertEquals(5, parkingSpotDAO.getNextAvailableSlot(bike));
    }
}