package com.parkit.parkingsystem.model;

import java.util.Date;

/**
 * The type Ticket.
 */
public class Ticket {

    /** The is recurrent user. */
    private boolean isRecurrentUser;

    /** The id. */
    private int id;

    /** The parking spot. */
    private ParkingSpot parkingSpot;

    /** The vehicle reg number. */
    private String vehicleRegNumber;

    /** The price. */
    private double price;

    /** The in time. */
    private Date inTime;

    /** The out time. */
    private Date outTime;

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
	return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
	this.id = id;
    }

    /**
     * Gets parking spot.
     *
     * @return the parking spot
     */
    public ParkingSpot getParkingSpot() {
	return parkingSpot;
    }

    /**
     * Sets parking spot.
     *
     * @param parkingSpot the parking spot
     */
    public void setParkingSpot(ParkingSpot parkingSpot) {
	this.parkingSpot = parkingSpot;
    }

    /**
     * Gets vehicle reg number.
     *
     * @return the vehicle reg number
     */
    public String getVehicleRegNumber() {
	return vehicleRegNumber;
    }

    /**
     * Sets vehicle reg number.
     *
     * @param vehicleRegNumber the vehicle reg number
     */
    public void setVehicleRegNumber(String vehicleRegNumber) {
	this.vehicleRegNumber = vehicleRegNumber;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public double getPrice() {
	return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(double price) {
	this.price = Math.round(price * 100.0) / 100.0;
    }

    /**
     * Gets in time.
     *
     * @return the in time
     */
    public Date getInTime() {
	return inTime;
    }

    /**
     * Sets in time.
     *
     * @param inTime the in time
     */
    public void setInTime(Date inTime) {
	this.inTime = inTime;
    }

    /**
     * Gets out time.
     *
     * @return the out time
     */
    public Date getOutTime() {
	return outTime;
    }

    /**
     * Sets out time.
     *
     * @param outTime the out time
     */
    public void setOutTime(Date outTime) {
	this.outTime = outTime;
    }

    /**
     * Gets recurrent user.
     *
     * @return the recurrent user
     */
    public boolean getRecurrentUser() {
	return isRecurrentUser;
    }

    /**
     * Sets recurrent user.
     *
     * @param recurrentUser the recurrent user
     */
    public void setRecurrentUser(boolean recurrentUser) {
	this.isRecurrentUser = recurrentUser;
    }
}
