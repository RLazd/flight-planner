package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.domain.Flight;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FlightInMemoryRepository {
    private List<Flight> flights;

    public FlightInMemoryRepository(List<Flight> flights) {
        this.flights = flights;
    }

    public void saveFlight(Flight flight) {
        flights.add(flight);
    }

    public void clearAllFlights() {
        flights.clear();
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void remove(Flight flight) {
        flights.remove(flight);
    }

}
