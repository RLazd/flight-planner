package io.codelex.flightplanner.service;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dto.PageResult;
import io.codelex.flightplanner.dto.SearchFlightsRequest;
import io.codelex.flightplanner.repository.AirportDatabaseRepository;
import io.codelex.flightplanner.repository.FlightDatabaseRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@ConditionalOnProperty(prefix = "flight", name = "mode", havingValue = "database")
public class FlightDatabaseService implements FlightService {
    private FlightDatabaseRepository flightRepository;
    private AirportDatabaseRepository airportRepository;
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public FlightDatabaseService(FlightDatabaseRepository flightRepository, AirportDatabaseRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    @Override
    public void addFlight(Flight flight) {
        saveAirportInRepoIfNotPresent(flight.getFrom());
        saveAirportInRepoIfNotPresent(flight.getTo());

        if (!flightRepository.matchingFlights(flight.getFrom().getAirport(), flight.getTo().getAirport(), flight.getCarrier(),
                flight.getDepartureTime(), flight.getArrivalTime()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "failed to add");
        }

        validateAirports(flight);
        validateDepartureAndArrivalDates(flight);

        flightRepository.save(flight);
    }

    @Override
    public void validateAirports(Flight flight) {
        if (flight.getFrom().equals(flight.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Arrival and departure airports are the same");
        }
    }

    @Override
    public void validateDepartureAndArrivalDates(Flight flight) {
        LocalDateTime departure = LocalDateTime.parse(flight.getDepartureTime(), FORMATTER);
        LocalDateTime arrival = LocalDateTime.parse(flight.getArrivalTime(), FORMATTER);

        if (arrival.isBefore(departure) || arrival.isEqual(departure)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private void saveAirportInRepoIfNotPresent(Airport airport) {
        Optional<Airport> maybeAirport = airportRepository.findById(airport.getAirport());
        if (maybeAirport.isEmpty()) {
            airportRepository.save(airport);
        }
    }

    @Override
    public void deleteFlight(String id) {
        if (flightRepository.existsById(id)) {
            flightRepository.deleteById(id);
        }
    }

    @Override
    public void clearAllFlights() {
        flightRepository.deleteAll();
    }

    @Override
    public Flight fetchFlight(String id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));
    }

    @Override
    public List<Airport> searchAirports(String phrase) {
        return airportRepository.findByCountryOrCityOrAirportStartsWith(phrase);
    }

    @Override
    public Flight findFlightById(String id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID does not exist"));
    }

    @Override
    public PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest) {
        if (searchFlightsRequest.getFrom().equals(searchFlightsRequest.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Arrival and departure can't match");
        }

        List<Flight> flightsThatFitSearchRequestCriteria = flightRepository.searchFlights(searchFlightsRequest.getFrom(),
                searchFlightsRequest.getTo(), searchFlightsRequest.getDepartureDate());

        return new PageResult<>(0, flightsThatFitSearchRequestCriteria.size(), flightsThatFitSearchRequestCriteria);
    }
}
