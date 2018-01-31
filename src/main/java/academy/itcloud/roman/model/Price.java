package academy.itcloud.roman.model;

import academy.itcloud.roman.db.implementsDAO.sqlite.flights.FlightsDAOImplements;

import java.io.Serializable;

public class Price implements Serializable {

    private Flight flight;
    private Flight.FlightClass flightClass;
    private double cost;

    public static class Builder {
        private Flight flight;
        private Flight.FlightClass flightClass;
        private double cost;

        public Builder setFlight(String flightNumber) {
            Flight temp = FlightsDAOImplements.findFlight(flightNumber);
            if (temp == null) {
                throw new IllegalArgumentException("Flight does not exist");
            }
            this.flight = temp;
            return this;
        }

        public Builder setFlightClass(Flight.FlightClass flightClass) {
            this.flightClass = flightClass;
            return this;
        }

        public Builder setCost(double cost) {
            if (cost <= 0) {
                throw new IllegalArgumentException("cost can be better than 0");
            }
            this.cost = cost;
            return this;
        }

        public Price build() {
            Price price = new Price();
            price.flight = this.flight;
            price.flightClass = this.flightClass;
            price.cost = this.cost;
            return price;
        }
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    private Price() {
    }

    public Flight getFlight() {
        return flight;
    }

    @Override
    public String toString() {
        return "Price:\n" +
                "flight - '" + flight + '\'' +
                ", class of flight - " + flightClass +
                ", cost - " + cost +
                "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        if (flight != null ? !flight.equals(price.flight) : price.flight != null) return false;
        return flightClass == price.flightClass;
    }

    @Override
    public int hashCode() {
        int result = flight != null ? flight.hashCode() : 0;
        result = 31 * result + (flightClass != null ? flightClass.hashCode() : 0);
        return result;
    }

    public Flight.FlightClass getFlightClass() {
        return flightClass;
    }

    public double getCost() {
        return cost;
    }


}

