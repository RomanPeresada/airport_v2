package academy.itcloud.roman.db.dao.factory;

import academy.itcloud.roman.db.implementsDAO.sqlite.factory.DatabaseDAOFactory;
import academy.itcloud.roman.db.dao.flights.FlightsDAO;
import academy.itcloud.roman.db.dao.passengers.PassengersDAO;
import academy.itcloud.roman.db.dao.prices.PricesDAO;

public interface FactoryDAO {
    enum Type {
        DATABASE
    }

    FlightsDAO getFlights();

    PassengersDAO getPassenger();

    PricesDAO getPrices();

    static FactoryDAO getInstance(Type type) {
        switch (type) {
            case DATABASE:
                return new DatabaseDAOFactory();
            default:
                throw new IllegalArgumentException("type is not exists");
        }
    }

}
