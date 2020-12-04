package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

/**
 * The type Fare calculator service.
 * @author lilas
 */
public class FareCalculatorService {

    /**
     * Calculate fare.
     *
     * @param ticket the ticket
     */
    @SuppressWarnings("ConstantConditions")
    public void calculateFare(Ticket ticket) {
	if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
	    throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
	}

	long inHour = ticket.getInTime().getTime();
	long outHour = ticket.getOutTime().getTime();
	long convInHour = 60 * 60 * 1000;
	double coefReduction = 0.95;
	// TODO: Some tests are failing here. Need to check if this logic is correct
	double duration = (double) (outHour - inHour) / convInHour;

	switch (ticket.getParkingSpot().getParkingType()) {
	case CAR:
	    if (duration <= 0.5) {
		ticket.setPrice(duration * Fare.FREE_CAR_RATE_PER_HOUR);
	    } else if (ticket.getRecurrentUser()) {
		ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR * coefReduction);
	    } else {
		ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
	    }
	    break;
	case BIKE:
	    if (duration <= 0.5) {
		ticket.setPrice(duration * Fare.FREE_BIKE_RATE_PER_HOUR);
	    } else if (ticket.getRecurrentUser()) {
		ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR * coefReduction);
	    } else {
		ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
	    }

	    break;
	default:
	    throw new IllegalArgumentException("Unkown Parking Type");
	}
    }
}