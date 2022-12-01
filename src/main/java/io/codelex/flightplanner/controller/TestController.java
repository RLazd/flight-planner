package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testing-api")
public class TestController {
    private FlightService flightService;

    public TestController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/clear")
    @ResponseStatus(HttpStatus.OK)
    public void clear() {
        flightService.clearAllFlights();
    }


}
