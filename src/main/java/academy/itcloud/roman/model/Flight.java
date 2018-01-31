package academy.itcloud.roman.model;


import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Flight implements Serializable {

    public enum Status {
        CHECK_IN, GATE_CLOSED, ARRIVED, DEPARTED_AT, UNKNOWN, CANCELED, EXPECTED_AT, DELAYED, IN_FLIGHT
    }

    private String number;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String cityOfDeparture;
    private String cityOfArrival;
    private String terminal;
    private Status status;
    private String gate;

    public boolean isCorrectNumber(String number) {
        Pattern pattern = Pattern.compile("^(\\w){4,10}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public  boolean isCorrectCity(String city) {
        Pattern pattern = Pattern.compile("^[A-Z][a-z]+$");
        Matcher matcher = pattern.matcher(city);
        return matcher.matches();
    }

    public Flight() {
    }

    public Flight(String numberOfFlight, LocalDateTime departure, LocalDateTime arrival, String cityOFDeparture, String cityOfArrival, String terminal, Status status, String gate) {
        if (departure.isAfter(arrival)) {
            throw new DateTimeException("Date of arrival time can't be bigger than date of departureTime");
        }
        if (terminal.isEmpty() || gate.isEmpty() ||cityOfArrival.isEmpty() || cityOFDeparture.isEmpty()) {
            throw new IllegalArgumentException("Data can`t be empty");
        }
        if(!isCorrectNumber(numberOfFlight)){
            throw new IllegalArgumentException("Flight number if invalid");
        }
        this.arrivalTime = arrival;
        this.departureTime = departure;
        this.number = numberOfFlight;
        this.cityOfArrival = cityOfArrival;
        this.cityOfDeparture = cityOFDeparture;
        this.terminal = terminal;
        this.status = status;
        this.gate = gate;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public String getNumber() {
        return number;
    }

    public String getCityOfArrival() {
        return cityOfArrival;
    }

    public String getGate() {
        return gate;
    }

    public String getCityOfDeparture() {
        return cityOfDeparture;
    }

    public String getTerminal() {
        return terminal;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;
        return this.number.equals(flight.number);

    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    public String toString() {
        return "Flight:\n" +
                "departureTime - " + departureTime.format(DateTimeFormatter.ISO_DATE) +
                ", arrivalTime - " + arrivalTime.format(DateTimeFormatter.ISO_DATE) +
                ", number of flight - '" + number + '\'' +
                ", city of departure - '" + cityOfDeparture + '\'' +
                ", city of arrival - '" + cityOfArrival + '\'' +
                ", terminal - '" + terminal + '\'' +
                ", flight status - " + status +
                ", gate - '" + gate + '\'' +
                "\n";
    }

    public enum FlightClass {
        ECONOMY,
        BUSINESS
    }
}
