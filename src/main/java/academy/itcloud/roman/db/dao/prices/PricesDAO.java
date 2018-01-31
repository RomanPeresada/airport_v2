package academy.itcloud.roman.db.dao.prices;

import academy.itcloud.roman.model.Flight;
import academy.itcloud.roman.model.Price;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface PricesDAO {

    boolean addPrice(Price price) throws SQLException;

    boolean deletePrice(Price price) throws SQLException;

    boolean updatePrice(Price oldPrice, Price newPrice) throws SQLException;


    Map<Flight.FlightClass, Double> viewPriceList(String numberOfFlight) throws SQLException;

    List<Price> getAllPrices() throws SQLException;
}
