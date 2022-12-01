package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightDatabaseRepository extends JpaRepository<Flight, String> {
    @Query("SELECT f FROM Flight f WHERE f.from.airport = :from " +
            "AND f.to.airport = :to " +
            "AND f.carrier = :carrier " +
            "AND f.departureTime = :departureTime " +
            "AND f.arrivalTime = :arrivalTime")
    List<Flight> matchingFlights(
            @Param("from") String from,
            @Param("to") String to,
            @Param("carrier") String carrier,
            @Param("departureTime") String departureTime,
            @Param("arrivalTime") String arrivalTime
    );

    @Query("SELECT f FROM Flight f WHERE f.from.airport = :from " +
            "AND f.to.airport = :to " +
            "AND f.departureTime LIKE :departureDate% ")
    List<Flight> searchFlights(
            @Param("from") String from,
            @Param("to") String to,
            @Param("departureDate") String departureDate
    );
}
