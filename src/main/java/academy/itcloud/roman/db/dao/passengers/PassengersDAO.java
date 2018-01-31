package academy.itcloud.roman.db.dao.passengers;

import academy.itcloud.roman.model.Flight;
import academy.itcloud.roman.model.Passenger;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface PassengersDAO {

    boolean addPassenger(Passenger passenger) throws SQLException;

    boolean deletePassenger(String passportNumber, String flightNumber) throws SQLException;

    boolean updatePassenger(String passportNumber, Passenger newPassenger) throws SQLException;

    boolean addAFlightToThePassenger(String passportNumber, String flightNumber, Flight.FlightClass flightClass) throws SQLException;

    Collection<Passenger> searchPassengersByFlightNumber(String flightNumber) throws SQLException;

    Collection<Passenger> searchPassengersByFirstAndLastName(String firstName, String lastName) throws SQLException;

    Pair<String, String> searchFirstAndSecondNameByPassportNumber(String numberOfPassport) throws SQLException;

    Passenger searchPassengerByPassportNumber(String passportNumber) throws SQLException;

    Collection<Passenger> searchPassengersByDeparture(String departure) throws SQLException;

    Collection<Passenger> searchPassengersByArrival(String arrival) throws SQLException;

    Collection<Passenger> viewPassengersList(String numberOfFlight) throws SQLException;

    Collection<Passenger> getAllPassengers() throws SQLException;
}
