package io.codelex.flightplanner.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "airports")
public class Airport {
    @NotEmpty
    private String country;
    @NotEmpty
    private String city;
    @NotEmpty
    @Column(name = "id")
    @Id
    private String airport;

    public Airport() {
    }

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

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }
}
