package io.codelex.flightplanner;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(prefix = "flight", name = "mode", havingValue = "memory")
public class FlightInMemoryService implements FlightService {
    private FlightInMemoryRepository repository;

    public FlightInMemoryService(FlightInMemoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public synchronized void addFlight(Flight flight, Errors errors) {

        if (errors.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "failed to add", new Exception());
        }

        if (repository.getFlights()
                .stream()
                .anyMatch(f -> f.equals(flight))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "failed to add");
        }

        validateAirports(flight);
        validateDepartureAndArrivalDates(flight);

        repository.saveFlight(flight);
    }

    @Override
    public void validateAirports(Flight flight) {
        if (flight.getFrom().equals(flight.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Arrival and departure airports are the same");
        }
    }

    @Override
    public void validateDepartureAndArrivalDates(Flight flight) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime departure = LocalDateTime.parse(flight.getDepartureTime(), formatter);
        LocalDateTime arrival = LocalDateTime.parse(flight.getArrivalTime(), formatter);

        if (arrival.isBefore(departure) || arrival.isEqual(departure)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public synchronized void deleteFlight(String id) {
        for (Flight flight : repository.getFlights()) {
            if (flight.getId().equalsIgnoreCase(id)) {
                repository.remove(flight);
                break;
            }
        }
    }

    @Override
    public void clearAllFlights() {
        repository.clearAllFlights();
    }

    @Override
    public Flight fetchFlight(String id) {
        try {
            return repository.getFlights().stream()
                    .filter(flight -> flight.getId().equals(id))
                    .toList()
                    .get(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found");
        }
    }

    @Override
    public List<Airport> searchAirports(String search) {
        return repository.getFlights()
                .stream()
                .map(Flight::getFrom)
                .filter(f -> (f.getCountry().toLowerCase().startsWith(search)
                        || f.getCity().toLowerCase().startsWith(search)
                        || f.getAirport().toLowerCase().startsWith(search)))
                .collect(Collectors.toList());
    }

    @Override
    public Flight findFlightById(String id) {
        return repository.getFlights()
                .stream()
                .filter(flight -> flight.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID does not exist"));
    }

    @Override
    public PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest) {
        if (searchFlightsRequest.getFrom().equals(searchFlightsRequest.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Arrival and departure can't match");
        }

        List<Flight> flightsThatFitSearchRequestCriteria = repository.getFlights().stream()
                .filter(flight -> flight.getFrom().getAirport().equals(searchFlightsRequest.getFrom()))
                .filter(flight -> flight.getTo().getAirport().equals(searchFlightsRequest.getTo()))
                .filter(flight -> flight.getDepartureDate().equals(searchFlightsRequest.getDepartureDate()))
                .toList();

        return new PageResult<>(0, flightsThatFitSearchRequestCriteria.size(), flightsThatFitSearchRequestCriteria);
    }

}
