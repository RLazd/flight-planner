package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportDatabaseRepository extends JpaRepository<Airport, String> {

    @Query("SELECT a FROM Airport a WHERE " +
            "LOWER(a.country) like :phrase% " +
            "OR LOWER(a.city) like :phrase% " +
            "OR LOWER(a.airport) like :phrase% ")
    List<Airport> findByCountryOrCityOrAirportStartsWith(
            @Param("phrase") String phrase
    );

}
