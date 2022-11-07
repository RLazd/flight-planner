package io.codelex.flightplanner;

import io.codelex.flightplanner.Flight;
import io.codelex.flightplanner.FlightRequest;
import io.codelex.flightplanner.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.InputMismatchException;

@RestController
@RequestMapping("/admin-api")
public class AdminFlightController {

    private FlightService flightService;

    public AdminFlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PutMapping(path = "/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@Valid @RequestBody FlightRequest request, Errors errors) {
        Flight flight = new Flight(request.getFrom(), request.getTo(), request.getCarrier(),
                request.getDepartureTime(), request.getArrivalTime());

        flightService.addFlight(flight, errors);

        return flight;
    }

    @DeleteMapping("/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFlight(@PathVariable("id") String id) {
        flightService.deleteFlight(id);
    }

    @GetMapping("/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flight fetchFlight(@PathVariable("id") String id) {
        return flightService.fetchFlight(id);
    }


}
