package academy.itcloud.roman.db.implementsDAO.sqlite.prices;

import academy.itcloud.roman.db.dao.prices.PricesDAO;
import academy.itcloud.roman.db.implementsDAO.sqlite.queries.Queries;
import academy.itcloud.roman.model.Flight;
import academy.itcloud.roman.model.Price;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static academy.itcloud.roman.db.create.db.CreateDB.getConnection;

public class PricesDAOImplements implements PricesDAO {

//    {
//        try {
//            addPrice(new Price.Builder().setFlight("1234AA").setFlightClass(Flight.FlightClass.ECONOMY).setCost(89.5).build());
//            addPrice(new Price.Builder().setFlight("1234AA").setFlightClass(Flight.FlightClass.BUSINESS).setCost(120).build());
//
//            addPrice(new Price.Builder().setFlight("1AQe23").setFlightClass(Flight.FlightClass.ECONOMY).setCost(120).build());
//            addPrice(new Price.Builder().setFlight("1AQe23").setFlightClass(Flight.FlightClass.BUSINESS).setCost(150).build());
//
//            addPrice(new Price.Builder().setFlight("2147AA").setFlightClass(Flight.FlightClass.ECONOMY).setCost(78).build());
//            addPrice(new Price.Builder().setFlight("2147AA").setFlightClass(Flight.FlightClass.BUSINESS).setCost(98).build());
//
//            addPrice(new Price.Builder().setFlight("4325PQ").setFlightClass(Flight.FlightClass.ECONOMY).setCost(80).build());
//            addPrice(new Price.Builder().setFlight("4325PQ").setFlightClass(Flight.FlightClass.BUSINESS).setCost(100).build());
//
//            addPrice(new Price.Builder().setFlight("XX2435").setFlightClass(Flight.FlightClass.ECONOMY).setCost(79).build());
//            addPrice(new Price.Builder().setFlight("XX2435").setFlightClass(Flight.FlightClass.BUSINESS).setCost(120).build());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public boolean addPrice(Price price) throws SQLException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.INSERT_PRICE)) {
            preparedStatement.setString(1, price.getFlight().getNumber());
            preparedStatement.setString(2, price.getFlightClass().toString());
            preparedStatement.setDouble(3, price.getCost());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deletePrice(Price price) throws SQLException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.DELETE_PRICE)) {
            preparedStatement.setString(1, price.getFlight().getNumber());
            preparedStatement.setString(2, price.getFlightClass().toString());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updatePrice(Price oldPrice, Price newPrice) throws SQLException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.UPDATE_PRICE)) {
            preparedStatement.setString(1, newPrice.getFlightClass().toString());
            preparedStatement.setDouble(2, newPrice.getCost());
            preparedStatement.setString(3, oldPrice.getFlight().getNumber());
            preparedStatement.setString(4, oldPrice.getFlightClass().toString());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<Flight.FlightClass, Double> viewPriceList(String numberOfFlight) throws SQLException {
        Map<Flight.FlightClass, Double> map = new HashMap<>();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.SEARCH_PRICE_LIST_BY_THE_FLIGHT_NUMBER)) {
            preparedStatement.setString(1, numberOfFlight);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                map.put(Flight.FlightClass.valueOf(resultSet.getString("flightClass")),
                        resultSet.getDouble("cost"));
            }
            resultSet.close();
        }
        return map;
    }

    @Override
    public List<Price> getAllPrices() throws SQLException {
        List<Price> list = new ArrayList<>();
        try (Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(Queries.VIEW_ALL_PRICE_LIST);
            while (resultSet.next()) {
                Price price = new Price.Builder()
                        .setFlight(resultSet.getString("flightNumber"))
                        .setFlightClass(Flight.FlightClass.valueOf(resultSet.getString("flightClass")))
                        .setCost(resultSet.getDouble("cost"))
                        .build();
                list.add(price);
            }
        }
        return list;
    }



}