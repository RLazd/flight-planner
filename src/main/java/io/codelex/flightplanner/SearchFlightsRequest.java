package io.codelex.flightplanner;

import javax.validation.constraints.NotEmpty;

public class SearchFlightsRequest {
    @NotEmpty
    private String from;
    @NotEmpty
    private String to;
    @NotEmpty
    private String departureDate;

    public SearchFlightsRequest(String from, String to, String departureDate) {
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getDepartureDate() {
        return departureDate;
    }

}
