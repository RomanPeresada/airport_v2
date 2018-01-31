package academy.itcloud.roman.db.implementsDAO.sqlite.passengers;

import academy.itcloud.roman.db.dao.passengers.PassengersDAO;
import academy.itcloud.roman.db.implementsDAO.sqlite.flights.FlightsDAOImplements;
import academy.itcloud.roman.db.implementsDAO.sqlite.queries.Queries;
import academy.itcloud.roman.model.Flight;
import academy.itcloud.roman.model.Passenger;
import javafx.util.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;

import static academy.itcloud.roman.db.create.db.CreateDB.getConnection;

public class PassengersDAOImplements implements PassengersDAO {
//    {
//        try {
//            addPassenger(new Passenger("Andrey", "Alekseev", "GRE", "21445UU",
//                    LocalDate.of(1984, 4, 23), Passenger.Gender.MALE, "4325PQ", Flight.FlightClass.BUSINESS));
//
//            addPassenger(new Passenger("Maria", "Morginson", "UK", "4258IO",
//                    LocalDate.of(1990, 6, 21), Passenger.Gender.FEMALE, "XX2435", Flight.FlightClass.BUSINESS));
//
//            addPassenger(new Passenger("Ivan", "Ivanov", "RUS", "6432QE",
//                    LocalDate.of(1994, 8, 12), Passenger.Gender.MALE, "2147AA", Flight.FlightClass.ECONOMY));
//
//            addPassenger(new Passenger("Pavel", "Kisel", "RUS", "5932PO",
//                    LocalDate.of(1976, 2, 7), Passenger.Gender.MALE, "2147AA", Flight.FlightClass.BUSINESS));
//
//            addPassenger(new Passenger("Marina", "Borisova", "UKR", "7845UU",
//                    LocalDate.of(1995, 10, 12), Passenger.Gender.FEMALE, "1234AA", Flight.FlightClass.BUSINESS));
//
//
//            addPassenger(new Passenger("Alex", "Lord", "BL", "3677NE",
//                    LocalDate.of(2000, 7, 9), Passenger.Gender.MALE, "1AQe23", Flight.FlightClass.ECONOMY));
//
//            addPassenger(new Passenger("John", "Arersio", "US", "5366WW",
//                    LocalDate.of(1997, 12, 30), Passenger.Gender.MALE, "1AQe23", Flight.FlightClass.BUSINESS));
//
//            addPassenger(new Passenger("Larisa", "Ivanova", "UKR", "43761OW",
//                    LocalDate.of(1997, 12, 30), Passenger.Gender.FEMALE, "2147AA", Flight.FlightClass.ECONOMY));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }

    @Override
    public boolean addPassenger(Passenger passenger) throws SQLException {
        if (searchPassengerByPassportNumber(passenger.getPassportNumber()) != null) {
            return false;
        }
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.INSERT_PASSENGER)) {
            setPreparedStatementForPassenger(preparedStatement, passenger);
            List<String> numbersOfFlight = getFlightNumber(passenger.getFlights());
            if (preparedStatement.executeUpdate() > 0) {
                int a = -1;
                try (PreparedStatement ps2 = getConnection().prepareStatement(Queries.INSERT_PASSENGER_INTO_RELATIONS)) {
                    for (int i = 0; i < numbersOfFlight.size(); i++) {
                        ps2.setString(1, passenger.getPassportNumber());
                        ps2.setString(2, numbersOfFlight.get(i));
                        ps2.setString(3, passenger.getMap().get(FlightsDAOImplements.findFlight(numbersOfFlight.get(i))).toString());
                        a = ps2.executeUpdate();
                    }
                    if (a > 0) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    @Override
    public boolean deletePassenger(String passportNumber, String flightNumber) throws SQLException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.DELETE_PASSENGER)) {
            preparedStatement.setString(1, passportNumber);
            preparedStatement.setString(2, flightNumber);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updatePassenger(String passportNumber, Passenger newPassenger) throws SQLException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.UPDATE_PASSENGER)) {
            setPreparedStatementForPassenger(preparedStatement, newPassenger);
            preparedStatement.setString(7, passportNumber);
            List<String> numbers = getFlightNumber(newPassenger.getFlights());
            if (preparedStatement.executeUpdate() > 0) {
                int a = -1;
                try (PreparedStatement ps2 = getConnection().prepareStatement(Queries.UPDATE_RELATIONS_FROM_PASSENGERS)) {
                    for (int i = 0; i < numbers.size(); i++) {
                        ps2.setString(1, newPassenger.getPassportNumber());
                        ps2.setString(2, numbers.get(i));
                        ps2.setString(3, newPassenger.getMap().get(FlightsDAOImplements.findFlight(numbers.get(i))).toString());
                        ps2.setString(4, passportNumber);
                        a = ps2.executeUpdate();
                    }
                    if (a > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean addAFlightToThePassenger(String passportNumber, String flightNumber, Flight.FlightClass flightClass) throws SQLException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.INSERT_PASSENGER_INTO_RELATIONS)) {
            preparedStatement.setString(1, passportNumber);
            preparedStatement.setString(2, flightNumber);
            preparedStatement.setString(3, flightClass.toString());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<Passenger> searchPassengersByFlightNumber(String flightNumber) throws SQLException {
        Collection<Passenger> passengers;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.SEARCH_PASSENGERS_BY_FLIGHT_NUMBER)) {
            preparedStatement.setString(1, flightNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            passengers = createPassengersCollection(resultSet);
            resultSet.close();
        }
        return passengers;
    }

    @Override
    public Collection<Passenger> searchPassengersByFirstAndLastName(String firstName, String lastName) throws SQLException {
        Collection<Passenger> passengers;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.SEARCH_PASSENGERS_BY_FIRST_AND_LAST_NAME)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            ResultSet resultSet = preparedStatement.executeQuery();
            passengers = createPassengersCollection(resultSet);
            resultSet.close();
        }
        return passengers;
    }

    @Override
    public Pair<String, String> searchFirstAndSecondNameByPassportNumber(String numberOfPassport) throws SQLException {
        String fn = "";
        String ln = "";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.SEARCH_FIRST_AND_LAST_NAME_BY_PASSPORT_NUMBER)) {
            preparedStatement.setString(1, numberOfPassport);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                fn = resultSet.getString("firstName");
                ln = resultSet.getString("lastName");
            }
            resultSet.close();
        }
        return new Pair<>(fn, ln);
    }

    @Override
    public Passenger searchPassengerByPassportNumber(String passportNumber) throws SQLException {
        Passenger passenger = null;
        Map<Flight, Flight.FlightClass> flights = searchFlightsForPassengerByPassportNumber(passportNumber);
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.SEARCH_PASSENGER_BY_PASSPORT_NUMBER)) {
            preparedStatement.setString(1, passportNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Passenger.Gender gender = Passenger.Gender.valueOf(resultSet.getString("gender"));
                passenger = new Passenger(resultSet.getString("firstName"), resultSet.getString("lastName"),
                        resultSet.getString("nationality"), resultSet.getString("passportNumber"),
                        getLocalDate(resultSet.getString("birthdate")), gender,
                        flights);
            }
            resultSet.close();
        }
        return passenger;
    }

    @Override
    public Collection<Passenger> searchPassengersByDeparture(String departure) throws SQLException {
        Collection<Passenger> passengers;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.SEARCH_PASSENGERS_BY_DEPARTURE)) {
            preparedStatement.setString(1, departure);
            ResultSet resultSet = preparedStatement.executeQuery();
            passengers = createPassengersCollection(resultSet);
            resultSet.close();
        }
        return passengers;
    }

    @Override
    public Collection<Passenger> searchPassengersByArrival(String arrival) throws SQLException {
        Collection<Passenger> passengers;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.SEARCH_PASSENGERS_BY_ARRIVAL)) {
            preparedStatement.setString(1, arrival);
            ResultSet resultSet = preparedStatement.executeQuery();
            passengers = createPassengersCollection(resultSet);
            resultSet.close();

        }
        return passengers;
    }

    @Override
    public Collection<Passenger> viewPassengersList(String numberOfFlight) throws SQLException {
        Collection<Passenger> passengers;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.VIEW_PASSENGERS_BY_THE_FLIGHT_NUMBER)) {
            preparedStatement.setString(1, numberOfFlight);
            ResultSet resultSet = preparedStatement.executeQuery();
            passengers = createPassengersCollection(resultSet);
            for (Passenger passenger : passengers) {
                passenger.addFlight(FlightsDAOImplements.findFlight(numberOfFlight), searchFlightClass(passenger.getPassportNumber(), numberOfFlight));
            }
            resultSet.close();
        }
        return passengers;
    }

    @Override
    public Collection<Passenger> getAllPassengers() throws SQLException {
        Collection<Passenger> passengers;
        try (Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(Queries.VIEW_ALL_PASSENGERS);
            passengers = createPassengersCollection(resultSet);
            resultSet.close();
        }
        return passengers;
    }


    //*******************************************************************************************


    private void setPreparedStatementForPassenger(PreparedStatement preparedStatement, Passenger passenger) throws SQLException {
        preparedStatement.setString(1, passenger.getFirstName());
        preparedStatement.setString(2, passenger.getLastName());
        preparedStatement.setString(3, passenger.getNationality());
        preparedStatement.setString(4, passenger.getPassportNumber());
        preparedStatement.setString(5, passenger.getBirthDate().toString());
        preparedStatement.setString(6, passenger.getGender().toString());
    }

    private Map<Flight, Flight.FlightClass> searchFlightsForPassengerByPassportNumber(String passportNumber) throws SQLException {
        Map<Flight, Flight.FlightClass> flights = new HashMap<>();
        try (PreparedStatement ps = getConnection().prepareStatement(Queries.VIEW_ALL_RELATIONS)) {
            ps.setString(1, passportNumber);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String flightNumber = resultSet.getString("flightNumber");
                flights.put(FlightsDAOImplements.findFlight(flightNumber), searchFlightClass(passportNumber, flightNumber));
            }
            resultSet.close();
        }
        return flights;
    }

    private Flight.FlightClass searchFlightClass(String passportNumber, String flightNumber) throws SQLException {
        Flight.FlightClass flightClass = null;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.SEARCH_FLIGHT_CLASS_BY_PASSPORT_AND_FLIGHT)) {
            preparedStatement.setString(1, passportNumber);
            preparedStatement.setString(2, flightNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                flightClass = Flight.FlightClass.valueOf(resultSet.getString("flightClass"));
            }
            resultSet.close();
        }
        return flightClass;
    }

    private LocalDate getLocalDate(String localDate) {
        String db = localDate;
        String[] data = db.split("-");
        LocalDate localDate1 = LocalDate.of(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
        return localDate1;
    }

    private Collection<Passenger> createPassengersCollection(ResultSet resultSet) throws SQLException {
        Collection<Passenger> passengers = new ArrayList<>();

        while (resultSet.next()) {
            String passportNumber = resultSet.getString("passportNumber");
            Map<Flight, Flight.FlightClass> flights = searchFlightsForPassengerByPassportNumber(passportNumber);
            Passenger.Gender gender = Passenger.Gender.valueOf(resultSet.getString("gender"));
            Passenger passenger = new Passenger(resultSet.getString("firstName"), resultSet.getString("lastName"),
                    resultSet.getString("nationality"), resultSet.getString("passportNumber"),
                    getLocalDate(resultSet.getString("birthdate")), gender,
                    flights);
            passengers.add(passenger);
        }
        return passengers;
    }

    private List<String> getFlightNumber(Set<Flight> flights) {
        List<String> numbers = new ArrayList<>();
        for (Flight flight : flights) {
            numbers.add(flight.getNumber());
        }
        return numbers;
    }
}
