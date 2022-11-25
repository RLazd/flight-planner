package io.codelex.flightplanner.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Flight {
    private String id;
    private Airport from;
    private Airport to;
    private String carrier;
    private String departureTime;
    private String arrivalTime;
    private final DateTimeFormatter FORMATTER_WITH_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final DateTimeFormatter FORMATTER_WITHOUT_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Flight(Airport from, Airport to, String carrier, String departureTime, String arrivalTime) {
        this.id = UUID.randomUUID().toString();
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return from.equals(flight.from) && to.equals(flight.to) && carrier.equalsIgnoreCase(flight.carrier) && departureTime.equals(flight.departureTime) && arrivalTime.equals(flight.arrivalTime);
    }

    public String getId() {
        return id;
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

    public String getDepartureDate() {
        LocalDateTime departure = LocalDateTime.parse(departureTime, FORMATTER_WITH_TIME);
        return departure.format(FORMATTER_WITHOUT_TIME);
    }


}
