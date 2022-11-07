package io.codelex.flightplanner;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class Airport {
    @NotEmpty
    private String country;

    @NotEmpty
    private String city;

    @NotEmpty
    private String airport;

    public Airport(String country, String city, String airport) {
        this.country = country;
        this.city = city;
        this.airport = airport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return country.equalsIgnoreCase(airport.country) && city.equalsIgnoreCase(airport.city) && this.airport.equalsIgnoreCase(airport.airport);
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAirport() {
        return airport;
    }

}
