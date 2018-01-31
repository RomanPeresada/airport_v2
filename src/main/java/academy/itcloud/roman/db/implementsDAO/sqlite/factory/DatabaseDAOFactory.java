package academy.itcloud.roman.db.implementsDAO.sqlite.factory;

import academy.itcloud.roman.db.dao.factory.FactoryDAO;
import academy.itcloud.roman.db.dao.flights.FlightsDAO;
import academy.itcloud.roman.db.dao.passengers.PassengersDAO;
import academy.itcloud.roman.db.dao.prices.PricesDAO;
import academy.itcloud.roman.db.implementsDAO.sqlite.flights.FlightsDAOImplements;
import academy.itcloud.roman.db.implementsDAO.sqlite.passengers.PassengersDAOImplements;
import academy.itcloud.roman.db.implementsDAO.sqlite.prices.PricesDAOImplements;

public class DatabaseDAOFactory implements FactoryDAO {
    @Override
    public FlightsDAO getFlights() {
        return new FlightsDAOImplements();
    }

    @Override
    public PassengersDAO getPassenger() {
        return new PassengersDAOImplements();
    }

    @Override
    public PricesDAO getPrices() {
        return new PricesDAOImplements();
    }
}
