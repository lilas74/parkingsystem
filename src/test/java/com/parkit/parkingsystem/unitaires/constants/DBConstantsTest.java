package com.parkit.parkingsystem.unitaires.constants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.DBConstants;

/**
 * The Class DBConstantsTest.
 */
public class DBConstantsTest {

    /**
     * Verify DB constant are set correctly for get next parking slot request.
     */
    @DisplayName("Verify DBConstants are set corretly")
    @Test
    public void VerifyDBConstantAreSetCorrectlyForGetNextParkingSlotRequest() {
	String result = "select min(PARKING_NUMBER) from parking where AVAILABLE = true and TYPE = ?";

	Assertions.assertEquals(result, DBConstants.GET_NEXT_PARKING_SPOT);
    }

    /**
     * Verify DB constant are set correctly for update parking request.
     */
    @DisplayName("Verify DBConstants are set corretly")
    @Test
    public void VerifyDBConstantAreSetCorrectlyForUpdateParkingRequest() {
	String result = DBConstants.UPDATE_PARKING_SPOT;
	// "update parking set available = ? where PARKING_NUMBER = ?";

	Assertions.assertEquals("update parking set available = ? where PARKING_NUMBER = ?", result);
    }

    /**
     * Verify DB constant are set correctly for update ticket request.
     */
    @DisplayName("Verify DBConstants are set corretly")
    @Test
    public void VerifyDBConstantAreSetCorrectlyForUpdateTicketRequest() {
	String result = "update ticket set PRICE=?, OUT_TIME=? where OUT_TIME IS NULL  and ID=?";

	Assertions.assertEquals(result, DBConstants.UPDATE_TICKET2);
    }

    /**
     * Verify DB constant are set correctly for get ticket request.
     */
    @DisplayName("Verify DBConstants are set corretly")
    @Test
    public void VerifyDBConstantAreSetCorrectlyForGetTicketRequest() {
	String result = "SELECT t.PARKING_NUMBER, t.ID, t.PRICE, t.IN_TIME, t.OUT_TIME, p.TYPE from ticket t,parking p where p.parking_number = t.parking_number and t.VEHICLE_REG_NUMBER=? order by t.OUT_TIME ASC limit 1";

	Assertions.assertEquals(result, DBConstants.GET_TICKET);
    }

    /**
     * Verify DB constant are set correctly for get recurent user request.
     */
    @DisplayName("Verify DBConstants are set corretly")
    @Test
    public void VerifyDBConstantAreSetCorrectlyForGetRecurentUserRequest() {
	String result = "SELECT COUNT(VEHICLE_REG_NUMBER) from ticket where VEHICLE_REG_NUMBER = ? AND OUT_TIME IS NOT NULL";

	Assertions.assertEquals(result, DBConstants.GET_RECURRENT_USER);
    }

    /**
     * Verify DB constant are set correctly for save ticket request.
     */
    @DisplayName("Verify DBConstants are set corretly")
    @Test
    public void VerifyDBConstantAreSetCorrectlyForSaveTicketRequest() {
	String result = "insert into ticket(PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME) values(?,?,?,?,?)";

	Assertions.assertEquals(result, DBConstants.SAVE_TICKET);
    }
}
