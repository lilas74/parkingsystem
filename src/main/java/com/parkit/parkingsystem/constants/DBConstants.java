package com.parkit.parkingsystem.constants;

public class DBConstants {

    public static final String GET_NEXT_PARKING_SPOT = "select min(PARKING_NUMBER) from parking where AVAILABLE = true and TYPE = ?";
    public static final String UPDATE_PARKING_SPOT = "update parking set available = ? where PARKING_NUMBER = ?";

    public static final String SAVE_TICKET = "insert into ticket(PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME) values(?,?,?,?,?)";
    public static final String UPDATE_TICKET = "UPDATE ticket SET PRICE=?, OUT_TIME=? WHERE ID =?";
    public static final String UPDATE_TICKET2 = "update ticket set PRICE=?, OUT_TIME=? where OUT_TIME IS NULL  and ID=?";

    public static final String GET_TICKET = "SELECT t.PARKING_NUMBER, t.ID, t.PRICE, t.IN_TIME, t.OUT_TIME, p.TYPE from ticket t,parking p where p.parking_number = t.parking_number and t.VEHICLE_REG_NUMBER=? order by t.OUT_TIME ASC limit 1";
    public static final String GET_RECURRENT_USER = "SELECT COUNT(VEHICLE_REG_NUMBER) from ticket where VEHICLE_REG_NUMBER = ? AND OUT_TIME IS NOT NULL";
}
