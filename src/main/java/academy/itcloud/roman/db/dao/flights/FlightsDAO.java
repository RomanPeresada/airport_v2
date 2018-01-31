package academy.itcloud.roman.db.dao.flights;

import academy.itcloud.roman.model.Flight;

import java.sql.SQLException;
import java.util.Collection;

public interface FlightsDAO {

    boolean addFlight(Flight flight) throws SQLException;

    boolean deleteFlight(String flightNumber) throws SQLException;

    boolean updateFlight(String numberOfOldFlight, Flight newFlight) throws SQLException;

    Flight searchFlight(String number) throws SQLException;

    Collection<Flight> getAllFlights() throws SQLException;

    Collection<Flight> viewDepartures(String cityOfDeparture) throws SQLException;

    Collection<Flight> viewArrivals(String cityOfArrival) throws SQLException;

    String searchFlightNumber(String cityOfDeparture, String cityOfArrival) throws SQLException;

}
