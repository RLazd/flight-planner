package io.codelex.flightplanner.dto;

import io.codelex.flightplanner.domain.Airport;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

public class AddFlightRequest {
    @Valid
    @NotNull
    private Airport from;

    @Valid
    @NotNull
    private Airport to;

    @NotEmpty
    private String carrier;

    @NotEmpty
    private String departureTime;

    @NotEmpty
    private String arrivalTime;

    public AddFlightRequest(Airport from, Airport to, String carrier, String departureTime, String arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Airport getFrom() {
        return from;
    }

    public Airport getTo() {
        return to;
    }

    public String getCarrier() {
        return carrier;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

}
