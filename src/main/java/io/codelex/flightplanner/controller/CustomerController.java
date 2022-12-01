package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.service.FlightService;
import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.dto.PageResult;
import io.codelex.flightplanner.dto.SearchFlightsRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {
    private FlightService flightService;

    public CustomerController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/airports")
    public List<Airport> searchAirports(String search) {
        return flightService.searchAirports(search.trim().toLowerCase());
    }

    @GetMapping("/flights/{id}")
    public Flight findFlightById(@PathVariable("id") String id) {
        return flightService.findFlightById(id);
    }

    @PostMapping("/flights/search")
    public PageResult<Flight> searchFlights(@Valid @RequestBody SearchFlightsRequest searchFlightsRequest) {
        return flightService.searchFlights(searchFlightsRequest);
    }
}
