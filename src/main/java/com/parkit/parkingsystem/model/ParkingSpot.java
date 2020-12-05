package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.constants.ParkingType;

/**
 * The type Parking spot.
 */
public class ParkingSpot {

    /** The number. */
    private int number;

    /** The parking type. */
    private ParkingType parkingType;

    /** The is available. */
    private boolean isAvailable;

    /**
     * Instantiates a new Parking spot.
     *
     * @param number      the number
     * @param parkingType the parking type
     * @param isAvailable the is available
     */
    public ParkingSpot(int number, ParkingType parkingType, boolean isAvailable) {
	this.number = number;
	this.parkingType = parkingType;
	this.isAvailable = isAvailable;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
	return number;
    }

    /**
     * Sets id.
     *
     * @param number the number
     */
    public void setId(int number) {
	this.number = number;
    }

    /**
     * Gets parking type.
     *
     * @return the parking type
     */
    public ParkingType getParkingType() {
	return parkingType;
    }

    /**
     * Sets parking type.
     *
     * @param parkingType the parking type
     */
    public void setParkingType(ParkingType parkingType) {
	this.parkingType = parkingType;
    }

    /**
     * Is available boolean.
     *
     * @return the boolean
     */
    public boolean isAvailable() {
	return isAvailable;
    }

    /**
     * Sets available.
     *
     * @param available the available
     */
    public void setAvailable(boolean available) {
	isAvailable = available;
    }

    /**
     * Equals.
     *
     * @param o the o
     * @return true, if successful
     */
    @Override
    public boolean equals(Object o) {
	if (this == o) {
        return true;
    }
	if (o == null || getClass() != o.getClass()) {
        return false;
    }
	ParkingSpot that = (ParkingSpot) o;
	return number == that.number;
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
	return number;
    }
}
