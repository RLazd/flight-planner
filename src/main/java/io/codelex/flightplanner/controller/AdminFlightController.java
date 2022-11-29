package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dto.AddFlightRequest;
import io.codelex.flightplanner.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin-api")
public class AdminFlightController {

    private FlightService flightService;

    public AdminFlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PutMapping(path = "/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@Valid @RequestBody AddFlightRequest request) {
        Flight flight;
        try {
            flight = new Flight(request.getFrom(), request.getTo(), request.getCarrier(),
                    request.getDepartureTime(), request.getArrivalTime());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "failed to add", new Exception());
        }

        flightService.addFlight(flight);
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
