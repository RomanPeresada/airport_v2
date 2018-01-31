package academy.itcloud.roman.support.create.model;

import academy.itcloud.roman.model.Flight;
import academy.itcloud.roman.model.Passenger;
import academy.itcloud.roman.model.Price;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class CreateModel {

    public static Price createPrice(Scanner scanner) {
        System.out.println("Enter a number of flight");
        String numberOfFlight = scanner.nextLine();
        System.out.println("Enter a class of flight");
        Flight.FlightClass classOfFlight = Flight.FlightClass.valueOf(scanner.nextLine().toUpperCase());
        System.out.println("Enter a price of ticket for this class");
        double cost = Integer.parseInt(scanner.nextLine());

        return new Price.Builder().setFlight(numberOfFlight).setFlightClass(classOfFlight).setCost(cost).build();
    }

    public static Passenger createPassenger(Scanner scanner) {
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name:");
        String secondName = scanner.nextLine();
        System.out.println("Enter nationality:");
        String nationality = scanner.nextLine();
        System.out.println("Enter number of passport:");
        String numberOfPassport = scanner.nextLine();
        System.out.println("Enter date of Birthday:\nyear:");
        int yearOfBirthday = Integer.parseInt(scanner.nextLine());
        System.out.println("month:");
        int monthOfBirthday = Integer.parseInt(scanner.nextLine());
        System.out.println("day:");
        int dayOfBirthday = Integer.parseInt(scanner.nextLine());
        LocalDate dateOfBirthday = LocalDate.of(yearOfBirthday, monthOfBirthday, dayOfBirthday);
        System.out.println("Enter gender:");
        Passenger.Gender gender = Passenger.Gender.valueOf(scanner.nextLine().toUpperCase());
        System.out.println("Enter a number of flight");
        String numberOfFlight = scanner.nextLine();
        System.out.println("Enter class of flight:");
        Flight.FlightClass classes = Flight.FlightClass.valueOf(scanner.nextLine().toUpperCase());
        return new Passenger(firstName, secondName, nationality, numberOfPassport, dateOfBirthday, gender,numberOfFlight, classes);
    }

    public static Flight createFlight(Scanner scanner) {
        System.out.println("Enter a date and time of departureTime:\nyear:");
        int yearOfDeparture = Integer.parseInt(scanner.nextLine());
        System.out.println("month (1-12):");
        int monthOfDeparture = Integer.parseInt(scanner.nextLine());
        System.out.println("day:");
        int dayOfDeparture = Integer.parseInt(scanner.nextLine());
        System.out.println("hour:");
        int hourOfDeparture = Integer.parseInt(scanner.nextLine());
        System.out.println("minute:");
        int minuteOfDeparture = Integer.parseInt(scanner.nextLine());
        LocalDateTime localDateTimeOfDeparture = LocalDateTime.of(yearOfDeparture, monthOfDeparture, dayOfDeparture, hourOfDeparture, minuteOfDeparture);

        System.out.println("Enter a date and time of arrivalTime:\nyear:");
        int yearOfArrival = Integer.parseInt(scanner.nextLine());
        System.out.println("month (1-12):");
        int monthOfArrival = Integer.parseInt(scanner.nextLine());
        System.out.println("day:");
        int dayOfArrival = Integer.parseInt(scanner.nextLine());
        System.out.println("hour:");
        int hoursOfArrival = Integer.parseInt(scanner.nextLine());
        System.out.println("minute:");
        int minutesOfArrival = Integer.parseInt(scanner.nextLine());
        LocalDateTime localDateTimeOfArrival = LocalDateTime.of(yearOfArrival, monthOfArrival, dayOfArrival, hoursOfArrival, minutesOfArrival);

        if (localDateTimeOfDeparture.isAfter(localDateTimeOfArrival)) {
            throw new DateTimeException("date is invalid");
        }
        System.out.println("Enter a number of flight:");
        String number = scanner.nextLine();
        System.out.println("Enter the city of departure:");
        String cityOfDeparture = scanner.nextLine();
        System.out.println("Enter the city of arrival:");
        String cityOfArrival = scanner.nextLine();
        System.out.println("Enter the terminal:");
        String terminal = scanner.nextLine();
        System.out.println("Enter a flight status(CHECK_IN, GATE_CLOSED, ARRIVED, DEPARTED_AT, UNKNOWN, CANCELED, EXPECTED_AT, DELAYED, IN_FLIGHT):");
        Flight.Status flightStatus = Flight.Status.valueOf(scanner.nextLine().toUpperCase());
        System.out.println("Enter a gate:");
        String gate = scanner.nextLine();
        return new Flight(number, localDateTimeOfDeparture, localDateTimeOfArrival, cityOfDeparture, cityOfArrival, terminal, flightStatus, gate);
    }
}
