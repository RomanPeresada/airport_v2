package academy.itcloud.roman.db.implementsDAO.sqlite.flights;

import academy.itcloud.roman.db.dao.flights.FlightsDAO;
import academy.itcloud.roman.db.implementsDAO.sqlite.queries.Queries;
import academy.itcloud.roman.model.Flight;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static academy.itcloud.roman.db.create.db.CreateDB.getConnection;

public class FlightsDAOImplements implements FlightsDAO {

    @Override
    public boolean addFlight(Flight flight) throws SQLException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.INSERT_FLIGHT)) {
            setPrepareStatementForFlight(preparedStatement, flight);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteFlight(String flightNumber) throws SQLException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.DELETE_FLIGHT)) {
            preparedStatement.setString(1, flightNumber);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateFlight(String numberOfOldFlight, Flight newFlight) throws SQLException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.UPDATE_FLIGHT)) {
            setPrepareStatementForFlight(preparedStatement, newFlight);
            preparedStatement.setString(9, numberOfOldFlight);
            if (preparedStatement.executeUpdate() > 0) {
                try (PreparedStatement ps2 = getConnection().prepareStatement(Queries.UPDATE_RELATIONS_FROM_FLIGHT)) {
                    ps2.setString(1, newFlight.getNumber());
                    ps2.setString(2, numberOfOldFlight);
                    if (ps2.executeUpdate() > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Flight searchFlight(String number) throws SQLException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.SEARCH_FLIGHT_BY_THE_NUMBER)) {
            preparedStatement.setString(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();
            Flight flight = null;
            while (resultSet.next()) {
                String departureTime = resultSet.getString("departureTime");
                String arrivalTime = resultSet.getString("arrivalTime");
                Flight.Status status = Flight.Status.valueOf(resultSet.getString("status"));

                flight = new Flight(resultSet.getString("number"), getDateAndTimeFromDB(departureTime), getDateAndTimeFromDB(arrivalTime),
                        resultSet.getString("cityOfDeparture"), resultSet.getString("cityOfArrival"),
                        resultSet.getString("terminal"), status, resultSet.getString("gate"));
            }
            resultSet.close();
            return flight;
        }
    }

    @Override
    public Collection<Flight> getAllFlights() throws SQLException {
        Collection<Flight> list = new ArrayList<>();
        try (Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(Queries.VIEW_ALL_FLIGHTS);
            list = createFlightsList(resultSet);
            resultSet.close();
        }
        return list;
    }

    @Override
    public Collection<Flight> viewDepartures(String cityOfDeparture) throws SQLException {
        Collection<Flight> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.VIEW_DEPARTURES)) {
            preparedStatement.setString(1, cityOfDeparture);
            ResultSet resultSet = preparedStatement.executeQuery();
            list = createFlightsList(resultSet);
            resultSet.close();
        }
        return list;
    }

    @Override
    public Collection<Flight> viewArrivals(String cityOfArrival) throws SQLException {
        Collection<Flight> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.VIEW_ARRIVALS)) {
            preparedStatement.setString(1, cityOfArrival);
            ResultSet resultSet = preparedStatement.executeQuery();
            list = createFlightsList(resultSet);
            resultSet.close();
        }
        return list;
    }

    @Override
    public String searchFlightNumber(String cityOfDeparture, String cityOfArrival) throws SQLException {
        String number = "";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.SEARCH_FLIGHT_NUMBER_BY_THE_CITIES)) {
            preparedStatement.setString(1, cityOfDeparture);
            preparedStatement.setString(2, cityOfArrival);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                number = resultSet.getString("number");
            }
        }
        return number;
    }

    //*****************************************************************************************


//    {
//        try {
//            addFlight(new Flight("1234AA",
//                    LocalDateTime.of(2017, 12, 31, 23, 12),
//                    LocalDateTime.of(2018, 1, 1, 1, 30),
//                    "Kiev", "Krakow", "A", Flight.Status.ARRIVED, "Open"));
//            addFlight(new Flight("5554AA",
//                    LocalDateTime.of(2017, 11, 25, 10, 55),
//                    LocalDateTime.of(2017, 11, 25, 11, 59),
//                    "Kiev", "Krakow", "B", Flight.Status.CANCELED, "Close"));
//            addFlight(new Flight("1AQe23",
//                    LocalDateTime.of(2017, 12, 31, 12, 12),
//                    LocalDateTime.of(2018, 1, 1, 7, 30),
//                    "Boston", "New York", "B", Flight.Status.GATE_CLOSED, "Close"));
//            addFlight(new Flight("2147AA",
//                    LocalDateTime.of(2017, 12, 31, 12, 12),
//                    LocalDateTime.of(2018, 1, 1, 7, 30),
//                    "Moscow", "Ufa", "A", Flight.Status.CHECK_IN, "Open"));
//            addFlight(new Flight("4325PQ",
//                    LocalDateTime.of(2017, 12, 31, 12, 12),
//                    LocalDateTime.of(2018, 1, 1, 7, 30),
//                    "Moscow", "Ufa", "D", Flight.Status.ARRIVED, "Open"));
//            addFlight(new Flight("XX2435",
//                    LocalDateTime.of(2018, 1, 3, 12, 12),
//                    LocalDateTime.of(2018, 1, 3, 15, 30),
//                    "Belgrad", "Praga", "A", Flight.Status.UNKNOWN, "Close"));
//
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
//    }

    public static Flight findFlight(String flightNumber) {
        Flight flight = null;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.SEARCH_FLIGHT_BY_THE_NUMBER)) {
            preparedStatement.setString(1, flightNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String departureTime = resultSet.getString("departureTime");
                String arrivalTime = resultSet.getString("arrivalTime");
                Flight.Status status = Flight.Status.valueOf(resultSet.getString("status"));

                flight = new Flight(resultSet.getString("number"), FlightsDAOImplements.getDateAndTimeFromDB(departureTime),
                        FlightsDAOImplements.getDateAndTimeFromDB(arrivalTime),
                        resultSet.getString("cityOfDeparture"), resultSet.getString("cityOfArrival"),
                        resultSet.getString("terminal"), status, resultSet.getString("gate"));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flight;
    }

    private static LocalDateTime getDateAndTimeFromDB(String dateTime) {
        int index = dateTime.indexOf(" ");

        String dateDeparture = dateTime.substring(0, index);
        String[] date = dateDeparture.split("-");

        String timeDeparture = dateTime.substring(index + 1, dateTime.length());
        String[] dataOfTime = timeDeparture.split(":");

        return LocalDateTime.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]),
                Integer.parseInt(date[2]), Integer.parseInt(dataOfTime[0]), Integer.parseInt(dataOfTime[1]));
    }

    private Collection<Flight> createFlightsList(ResultSet resultSet) throws SQLException {
        Collection<Flight> list = new ArrayList<>();
        while (resultSet.next()) {
            Flight flight;
            String departureTime = resultSet.getString("departureTime");
            String arrivalTime = resultSet.getString("arrivalTime");
            Flight.Status status = Flight.Status.valueOf(resultSet.getString("status"));

            flight = new Flight(resultSet.getString("number"), getDateAndTimeFromDB(departureTime), getDateAndTimeFromDB(arrivalTime),
                    resultSet.getString("cityOfDeparture"), resultSet.getString("cityOfArrival"),
                    resultSet.getString("terminal"), status, resultSet.getString("gate"));
            list.add(flight);
        }
        return list;
    }

    private void setPrepareStatementForFlight(PreparedStatement preparedStatement, Flight flight) throws SQLException {
        preparedStatement.setString(1, flight.getNumber());
        preparedStatement.setString(2, flight.getDepartureTime().toString().replaceAll("T", " "));
        preparedStatement.setString(3, flight.getArrivalTime().toString().replaceAll("T", " "));
        preparedStatement.setString(4, flight.getCityOfDeparture());
        preparedStatement.setString(5, flight.getCityOfArrival());
        preparedStatement.setString(6, flight.getTerminal());
        preparedStatement.setString(7, flight.getStatus().toString());
        preparedStatement.setString(8, flight.getGate());
    }

}
