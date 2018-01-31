package academy.itcloud.roman.db.create.db;


import java.sql.*;

public class CreateDB {


    private static final String DRIVER_NAME = "org.sqlite.JDBC";

    private static final String CREATE_FLIGHTS_TABLE = "CREATE TABLE IF NOT EXISTS flights(" +
            "number VARCHAR(10) NOT NULL," +
            "departureTime DATETIME NOT NULL," +
            "arrivalTime DATETIME NOT NULL," +
            "cityOfDeparture VARCHAR(20) NOT NULL," +
            "cityOfArrival VARCHAR(20) NOT NULL," +
            "terminal VARCHAR(2) NOT NULL," +
            "status VARCHAR(15) NOT NULL," +
            "gate VARCHAR(10) NOT NULL," +
            "PRIMARY KEY(number)" +
            ");";

    private static final String CREATE_PASSENGER_TABLE = "CREATE TABLE IF NOT EXISTS passengers(" +
            "firstName VARCHAR(20) NOT NULL," +
            "lastName VARCHAR(20) NOT NULL," +
            "nationality VARCHAR(20) NOT NULL," +
            "passportNumber VARCHAR (12) NOT NULL," +
            "birthdate DATE NOT NULL ," +
            "gender VARCHAR(7) NOT NULL," +
            "PRIMARY KEY(passportNumber)," +
            "FOREIGN KEY(passportNumber) REFERENCES relations(passportNumber)" +
            "ON DELETE CASCADE " +
            "ON UPDATE CASCADE);";

    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users(" +
            "login VARCHAR(30) NOT NULL," +
            "password VARCHAR(20)," +
            "PRIMARY KEY(login));";

    private static final String CREATE_PRICES_TABLE = "CREATE TABLE IF NOT EXISTS prices(" +
            "flightNumber VARCHAR(10) NOT  NULL," +
            "flightClass VARCHAR(10) NOT NULL," +
            "cost DOUBLE NOT NULL," +
            "PRIMARY KEY (flightNumber,flightClass)," +
            "FOREIGN KEY(flightNumber) REFERENCES flights(number) " +
            "ON DELETE CASCADE " +
            "ON UPDATE CASCADE);";

    private static final String CREATE_RELATIONS_TABLE = "CREATE TABLE IF NOT EXISTS relations(" +
            "passportNumber VARCHAR(20) NOT NULL," +
            "flightNumber VARCHAR (20) NOT NULL," +
            "flightClass VARCHAR (20) NOT  NULL ," +
            "PRIMARY KEY (passportNumber,flightNumber)," +
            "FOREIGN KEY(passportNumber) REFERENCES passangers(passportNumber)" +
            "ON DELETE CASCADE " +
            "ON UPDATE CASCADE," +
            "FOREIGN KEY (flightNumber) REFERENCES flight(number)" +
            "ON DELETE CASCADE " +
            "ON UPDATE CASCADE);";
    private static final String URL = "jdbc:sqlite:airport_test";


    static {
        createDataBaseAndTables();
    }


    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void createDataBaseAndTables() {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName(DRIVER_NAME);

            connection = DriverManager.getConnection(URL);

            createUsers();
            createFlights();
            createPrices();
            createPassengers();
            createRelations();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean createUsers() throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            return statement.execute(CREATE_USERS_TABLE);
        }
    }

    private static boolean createRelations() throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            return statement.execute(CREATE_RELATIONS_TABLE);
        }
    }


    private static boolean createFlights() throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            return statement.execute(CREATE_FLIGHTS_TABLE);

        }
    }

    private static boolean createPrices() throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            return statement.execute(CREATE_PRICES_TABLE);
        }
    }

    private static boolean createPassengers() throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            return statement.execute(CREATE_PASSENGER_TABLE);
        }
    }

}
