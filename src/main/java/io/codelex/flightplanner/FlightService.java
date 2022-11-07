package io.codelex.flightplanner;

import org.springframework.validation.Errors;

import java.util.List;

public interface FlightService {

    void addFlight(Flight flight, Errors errors);

    void validateAirports(Flight flight);

    void validateDepartureAndArrivalDates(Flight flight);

    void deleteFlight(String id);

    void clearAllFlights();

    Flight fetchFlight(String id);

    List<Airport> searchAirports(String search);

    Flight findFlightById(String id);

    PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest);


}
