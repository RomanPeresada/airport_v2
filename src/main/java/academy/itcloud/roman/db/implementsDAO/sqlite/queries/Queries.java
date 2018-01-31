package academy.itcloud.roman.db.implementsDAO.sqlite.queries;

public class Queries {
    public static final String INSERT_PASSENGER = "INSERT INTO passengers(firstName,lastName,nationality," +
            "passportNumber,birthdate,gender) VALUES (?,?,?,?,?,?);";
    public static final String INSERT_PASSENGER_INTO_RELATIONS = "INSERT INTO relations VALUES (?,?,?);";

    public static final String INSERT_FLIGHT = "INSERT INTO flights VALUES (?,?,?,?,?,?,?,?);";
    public static final String INSERT_PRICE = "INSERT INTO prices(flightNumber,flightClass,cost) VALUES (?,?,?);";

    public static final String UPDATE_PASSENGER = "UPDATE passengers SET firstName=?," +
            "lastName=?, nationality=?,passportNumber=?,birthdate=?,gender=?" +
            " WHERE passportNumber=?;";
    public static final String UPDATE_RELATIONS_FROM_PASSENGERS = "UPDATE relations SET passportNumber=?," +
            "flightNumber=?,flightClass=? WHERE passportNumber=?";


    public static final String UPDATE_FLIGHT = "UPDATE flights SET number=?,departureTime=?,arrivalTime=?,cityOfDeparture=?," +
            "cityOfArrival=?,terminal=?,status=?,gate=? WHERE number=?;";
    public static final String UPDATE_RELATIONS_FROM_FLIGHT = "UPDATE relations SET " +
            "flightNumber=? WHERE flightNumber=?";

    public static final String UPDATE_PRICE = "UPDATE prices SET flightClass=?,cost=? " +
            "WHERE flightNumber=? AND flightClass=?;";


    public static final String DELETE_PASSENGER = "DELETE FROM relations WHERE passportNumber=? and flightNumber=?;";
    public static final String DELETE_FLIGHT = "DELETE FROM flights WHERE number=?;";
    public static final String DELETE_PRICE = "DELETE FROM prices WHERE flightNumber=? AND flightClass=?;";


    public static final String SEARCH_FLIGHT_CLASS_BY_PASSPORT_AND_FLIGHT = "SELECT flightClass FROM passengers p JOIN relations r ON p.passportNumber=r.passportNumber " +
            "AND r.passportNumber=? AND r.flightNumber=?;";
    public static final String SEARCH_PASSENGERS_BY_FLIGHT_NUMBER = "SELECT * FROM passengers p JOIN relations r ON p.passportNumber=r.passportNumber AND flightNumber=?;";
    public static final String SEARCH_PASSENGERS_BY_FIRST_AND_LAST_NAME = "SELECT * FROM passengers WHERE firstName=? AND lastName=?;";
    public static final String SEARCH_FIRST_AND_LAST_NAME_BY_PASSPORT_NUMBER = "SELECT firstName,lastName FROM passengers WHERE passportNumber=?;";
    public static final String SEARCH_PASSENGER_BY_PASSPORT_NUMBER = "SELECT * FROM passengers WHERE passportNumber=?";
    public static final String SEARCH_PASSENGERS_BY_DEPARTURE = "SELECT * FROM passengers p JOIN relations r ON r.passportNumber=p.passportNumber JOIN flights f ON f.number=r.flightNumber AND f.cityOfDeparture=?;";
    public static final String SEARCH_PASSENGERS_BY_ARRIVAL = "SELECT * FROM passengers p JOIN relations r ON r.passportNumber=p.passportNumber JOIN flights f ON f.number=r.flightNumber AND f.cityOfArrival=?;";
    public static final String VIEW_PASSENGERS_BY_THE_FLIGHT_NUMBER = "SELECT * FROM passengers p JOIN relations r ON p.passportNumber=r.passportNumber AND flightNumber=?;";
    public static final String VIEW_ALL_PASSENGERS = "SELECT * FROM passengers";

    public static final String VIEW_ALL_RELATIONS = "SELECT * FROM RELATIONS WHERE passportNumber=?;";

    public static final String SEARCH_FLIGHT_BY_THE_NUMBER = "SELECT * FROM flights WHERE number=?;";
    public static final String VIEW_ALL_FLIGHTS = "SELECT * FROM flights";
    public static final String VIEW_DEPARTURES = "SELECT * FROM flights WHERE cityOfDeparture=?;";
    public static final String VIEW_ARRIVALS = "SELECT * FROM flights WHERE cityOfArrival=?";
    public static final String SEARCH_FLIGHT_NUMBER_BY_THE_CITIES = "SELECT number FROM flights WHERE cityOfDeparture=? AND cityOfArrival=?;";


    public static final String SEARCH_PRICE_LIST_BY_THE_FLIGHT_NUMBER = "SELECT flightClass,cost FROM prices WHERE flightNumber=?";
    public static final String VIEW_ALL_PRICE_LIST = "SELECT * FROM prices";

    public static final String INSERT_ADMIN = "INSERT INTO users VALUES (?,?);";
    public static final String INSERT_REGULAR_USER = "INSERT INTO users(login) VALUES (?);";
    public static final String GET_ALL_USERS = "SELECT * FROM users;";


}
