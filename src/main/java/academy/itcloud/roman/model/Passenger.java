package academy.itcloud.roman.model;


import academy.itcloud.roman.db.implementsDAO.sqlite.flights.FlightsDAOImplements;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Passenger implements Serializable {

    public enum Gender {
        MALE,
        FEMALE
    }

    private String firstName;
    private String lastName;
    private String nationality;
    private String passportNumber;
    private LocalDate birthDate;
    private Gender gender;
    private Map<Flight, Flight.FlightClass> flights;

    public Passenger() {
    }

    public Passenger(String firstName, String lastName, String nationality, String numberOfPassport, LocalDate dateOfBirthday, Gender gender, String numberOfFlight, Flight.FlightClass flightClass) {
        if (firstName.isEmpty() || lastName.isEmpty()) {
            throw new IllegalArgumentException("Data can`t be empty");
        }
        if(!isCorrectNationality(nationality)){
            throw new IllegalArgumentException("Nationality is invalid");
        }
        if(!isCorrectPassportNumber(numberOfPassport)){
            throw new IllegalArgumentException("Passport number is invalid");
        }
        Flight temp = FlightsDAOImplements.findFlight(numberOfFlight);
        if (temp == null) {
            throw new IllegalArgumentException("Flight doesn't exist");
        }
        this.flights = new HashMap<>();
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.passportNumber = numberOfPassport;
        this.birthDate = dateOfBirthday;
        this.gender = gender;
        this.flights.put(temp, flightClass);
    }

    public Passenger(String firstName, String lastName, String nationality, String passportNumber, LocalDate birthDate, Gender gender, Map<Flight, Flight.FlightClass> flights) {
        if (firstName.isEmpty() || lastName.isEmpty() || nationality.isEmpty() || flights.isEmpty()) {
            throw new IllegalArgumentException("Data can`t be empty");
        }
        this.flights = new HashMap<>();
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.passportNumber = passportNumber;
        this.birthDate = birthDate;
        this.gender = gender;
        this.flights = flights;
    }


    @Override
    public String toString() {
        return "Passenger:\n " +
                "first name - '" + firstName + '\'' +
                ", lastName - '" + lastName + '\'' +
                ", nationality - '" + nationality + '\'' +
                ", number of passport - '" + passportNumber + '\'' +
                ", date of birthday - '" + birthDate.format(DateTimeFormatter.ISO_DATE) + '\'' +
                ", gender - " + gender +
                ", flights - '" + flights + '\'' +
                "\n";
    }

    public void addFlight(Flight flight, Flight.FlightClass flightClass) {
        flights.put(flight, flightClass);
    }

    public Map<Flight, Flight.FlightClass> getMap() {
        return Collections.unmodifiableMap(flights);
    }

    public Set<Flight> getFlights() {
        return flights.keySet();
    }

    public Flight.FlightClass getFlightClass(Flight flight) {
        return flights.get(flight);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
    }


    public boolean isCorrectNationality(String nationality){
        Pattern pattern = Pattern.compile("^([A-Z]+)|([a-z]+)|([A-Z][a-z]+)$");
        Matcher matcher = pattern.matcher(nationality);
        return matcher.matches();
    }

    public boolean isCorrectPassportNumber(String number){
        Pattern pattern = Pattern.compile("^(\\w){5,10}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Passenger passenger = (Passenger) o;

        if (firstName != null ? !firstName.equals(passenger.firstName) : passenger.firstName != null) return false;
        if (lastName != null ? !lastName.equals(passenger.lastName) : passenger.lastName != null) return false;
        return passportNumber != null ? passportNumber.equals(passenger.passportNumber) : passenger.passportNumber == null;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (passportNumber != null ? passportNumber.hashCode() : 0);
        return result;
    }
}
