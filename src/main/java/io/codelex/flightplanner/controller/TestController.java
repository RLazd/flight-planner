package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.FlightService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

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
