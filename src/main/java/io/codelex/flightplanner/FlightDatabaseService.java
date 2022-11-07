package io.codelex.flightplanner;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "flight", name="mode", havingValue = "database")
public class FlightDatabaseService {
}
