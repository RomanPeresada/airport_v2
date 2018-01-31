package academy.itcloud.roman;

import academy.itcloud.roman.db.dao.factory.FactoryDAO;
import academy.itcloud.roman.db.dao.flights.FlightsDAO;
import academy.itcloud.roman.db.dao.passengers.PassengersDAO;
import academy.itcloud.roman.db.dao.prices.PricesDAO;
import academy.itcloud.roman.model.Flight;
import academy.itcloud.roman.model.Price;
import academy.itcloud.roman.support.create.model.CreateModel;
import academy.itcloud.roman.model.Passenger;
import academy.itcloud.roman.users.Authenticator;
import academy.itcloud.roman.users.NotEnoughPermissionException;
import academy.itcloud.roman.users.User;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static FactoryDAO factoryDAO = FactoryDAO.getInstance(FactoryDAO.Type.DATABASE);
    private static FlightsDAO flights = factoryDAO.getFlights();
    private static PricesDAO prices = factoryDAO.getPrices();
    private static PassengersDAO passengers = factoryDAO.getPassenger();

    private static User currentUser;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String password = "";
        System.out.println("Enter a login");
        String login = scanner.nextLine();
        if (!(login.isEmpty())) {
            System.out.println("Enter a password");
            password = scanner.nextLine();
        }
        currentUser = new Authenticator().findUser(login, password);
        String operation = "";
        while (!operation.equals("exit")) {
            System.out.printf("Choose a operation:\n-view of the flights\n-view of the price\n-view of the arrivals\n-view of the departures\n" +
                    "-view of the passengers list\n-add a (passenger, flight, price)\n-add a flight to the passenger\n-delete a (passenger, flight, price)" +
                    "\n-update a (passenger, flight, price)\n-search passengers by the (flight number,  first and last name, passport number, arrival, departure)" +
                    "\n-search first and last name of passenger by passport number \n-search flight number\n-all passengers\n-exit%n");
            operation = scanner.nextLine();
            switch (operation.trim()) {
                case "view of the flights":
                    viewFlights();
                    break;
                case "view of the price":
                    viewPriceList();
                    break;
                case "view of the arrivals":
                    viewArrivals();
                    break;
                case "view of the departures":
                    viewDepartures();
                    break;
                case "view of the passengers list":
                    viewPassengersList();
                    break;
                case "add a passenger":
                    System.out.println(addPassenger());
                    break;
                case "add a flight":
                    addFlight();
                    break;
                case "add a flight to the passenger":
                    System.out.println(addFlightToThePassenger());
                    break;
                case "add a price":
                    addPrice();
                    break;
                case "delete a flight":
                    deleteFlight();
                    break;
                case "delete a passenger":
                    deletePassenger();
                    break;
                case "delete a price":
                    deletePrice();
                    break;
                case "update a passenger":
                    updatePassenger();
                    break;
                case "update a flight":
                    updateFlight();
                    break;
                case "update a price":
                    updatePrice();
                    break;
                case "search passengers by the flight number":
                    searchPassengersByTheFlightNumber();
                    break;
                case "search passengers by the first and last name":
                    searchPassengersByFirstAndLastName();
                    break;
                case "search passengers by the passport number":
                    searchPassengerByPassport();
                    break;
                case "search passengers by the arrival":
                    searchPassengersByArrival();
                    break;
                case "search passengers by the departure":
                    searchPassengersByDeparture();
                    break;
                case "search first and last name of passenger by passport number":
                    searchFirstAndLastNameOfPassengerByPassport();
                    break;
                case "search flight number":
                    searchFlightNumber();
                    break;
                case "all passengers":
                    getAllPassengers();
                    break;
                case "exit":
                    System.out.println("Goodbye");
                    break;
                default:
                    System.out.println("Operation is invalid, try again");
                    break;
            }
        }
    }

    private static void addFlight() {
        checkPermission(User.Permission.ADD_FLIGHT);
        try {
            flights.addFlight(CreateModel.createFlight(scanner));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteFlight() {
        checkPermission(User.Permission.REMOVE_FLIGHT);
        System.out.println("Enter a number of flight that need to remove");
        try {
            flights.deleteFlight(scanner.nextLine());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateFlight() {
        checkPermission(User.Permission.UPDATE_FLIGHT);
        System.out.println("Enter a number of flight that need to update");
        String number = scanner.nextLine();
        System.out.println("Enter a update flight");
        try {
            flights.updateFlight(number, CreateModel.createFlight(scanner));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean addPassenger() {
        checkPermission(User.Permission.ADD_PASSENGER);
        Passenger passenger = CreateModel.createPassenger(scanner);
        try {
            return passengers.addPassenger(passenger);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean addFlightToThePassenger() {
        checkPermission(User.Permission.ADD_FLIGHT_TO_THE_PASSENGER);
        System.out.println("Enter a passport number of passengers that need more flight");
        String passportNumber = scanner.nextLine();
        System.out.println("Enter a flight number that need a passengers");
        String flightNumber = scanner.nextLine();
        System.out.println("Enter a flight class: ");
        try {
            return passengers.addAFlightToThePassenger(passportNumber, flightNumber, Flight.FlightClass.valueOf(scanner.nextLine()));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    private static boolean deletePassenger() {
        checkPermission(User.Permission.REMOVE_PASSENGER);
        System.out.println("Enter a passport number of passenger that need to remove");
        String passportNumber = scanner.nextLine();
        System.out.println("Enter a number of flight that need to remove");
        try {
            return passengers.deletePassenger(passportNumber, scanner.nextLine());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean updatePassenger() {
        checkPermission(User.Permission.UPDATE_PASSENGER);
        System.out.println("Enter a passport number of passengers that need to update");
        String passportNumber = scanner.nextLine();
        System.out.println("Enter a new data of passengers");
        try {
            return passengers.updatePassenger(passportNumber, CreateModel.createPassenger(scanner));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void getAllPassengers(){
        checkPermission(User.Permission.VIEW_PASSENGERS_LIST);
        try {
            System.out.println(passengers.getAllPassengers());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addPrice() {
        checkPermission(User.Permission.ADD_PRICE);
        System.out.println("Enter a new price");
        try {
            prices.addPrice(CreateModel.createPrice(scanner));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deletePrice() {
        checkPermission(User.Permission.REMOVE_PRICE);
        System.out.println("Enter a price that need to remove");
        try {
            prices.deletePrice(CreateModel.createPrice(scanner));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updatePrice() {
        checkPermission(User.Permission.UPDATE_PRICE);
        System.out.println("Enter a price that need to update");
        Price oldPrice = CreateModel.createPrice(scanner);
        System.out.println("Enter a new price");
        Price newPrice = CreateModel.createPrice(scanner);
        try {
            prices.updatePrice(oldPrice, newPrice);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewFlights() {
        checkPermission(User.Permission.VIEW_FLIGHTS);
        try {
            System.out.println(flights.getAllFlights());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewPriceList() {
        checkPermission(User.Permission.VIEW_PRICE_LIST);
        System.out.println("Enter a number of flight");
        try {
            System.out.println(prices.viewPriceList(scanner.nextLine()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewArrivals() {
        checkPermission(User.Permission.VIEW_ARRIVALS);
        System.out.println("Enter a city of arrival");
        try {
            System.out.println(flights.viewArrivals(scanner.nextLine()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewDepartures() {
        checkPermission(User.Permission.VIEW_DEPARTURES);
        System.out.println("Enter a city of departure");
        try {
            System.out.println(flights.viewDepartures(scanner.nextLine()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewPassengersList() {
        checkPermission(User.Permission.VIEW_PASSENGERS_LIST);
        System.out.println("Enter a number of flight");
        try {
            System.out.println(passengers.viewPassengersList(scanner.nextLine()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void searchFlightNumber() {
        checkPermission(User.Permission.SEARCH_FLIGHT_NUMBER);
        System.out.println("Enter a city of departure");
        String cityOfDeparture = scanner.nextLine();
        System.out.println("Enter a city of arrival");
        String cityOfArrival = scanner.nextLine();
        try {
            System.out.println(flights.searchFlightNumber(cityOfDeparture, cityOfArrival));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void searchPassengersByTheFlightNumber() {
        checkPermission(User.Permission.SEARCH_PASSENGERS_BY_THE_FLIGHT_NUMBER);
        System.out.println("Enter a number of flight");
        try {
            System.out.println(passengers.searchPassengersByFlightNumber(scanner.nextLine()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void searchPassengersByFirstAndLastName() {
        checkPermission(User.Permission.SEARCH_PASSENGERS_BY_THE_FIRST_AND_LAST_NAME);
        System.out.println("Enter a first name of passengers");
        String firstName = scanner.nextLine();
        System.out.println("Enter a last name of passengers");
        String lastName = scanner.nextLine();
        try {
            System.out.println(passengers.searchPassengersByFirstAndLastName(firstName, lastName));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void searchFirstAndLastNameOfPassengerByPassport() {
        checkPermission(User.Permission.SEARCH_PASSENGERS_BY_THE_FIRST_AND_LAST_NAME);
        System.out.println("Enter a passport number of passengers");
        try {
            System.out.println(passengers.searchFirstAndSecondNameByPassportNumber(scanner.nextLine()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void searchPassengerByPassport() {
        checkPermission(User.Permission.SEARCH_PASSENGER_BY_THE_PASSPORT_NUMBER);
        System.out.println("Enter a passport number of passengers");
        try {
            System.out.println(passengers.searchPassengerByPassportNumber(scanner.nextLine()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void searchPassengersByArrival() {
        checkPermission(User.Permission.SEARCH_PASSENGERS_BY_THE_ARRIVAL);
        System.out.println("Enter a city of arrival");
        try {
            System.out.println(passengers.searchPassengersByArrival(scanner.nextLine()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void searchPassengersByDeparture() {
        checkPermission(User.Permission.SEARCH_PASSENGERS_BY_THE_DEPARTURE);
        System.out.println("Enter a city of departure");
        try {
            System.out.println(passengers.searchPassengersByDeparture(scanner.nextLine()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void checkPermission(User.Permission permission) {
        if (!currentUser.checkPermission(permission)) {
            throw new NotEnoughPermissionException();
        }
    }
}
