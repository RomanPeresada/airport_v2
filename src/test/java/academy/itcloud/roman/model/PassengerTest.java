package academy.itcloud.roman.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PassengerTest {
    Passenger passenger;

    @Before
    public void init() {
        passenger = new Passenger();
    }

    @Test
    public void testNationality() {
        assertTrue(passenger.isCorrectNationality("UKR"));
        assertTrue(passenger.isCorrectNationality("Ukraine"));
        assertFalse(passenger.isCorrectNationality("Ukr@ine"));
        assertFalse(passenger.isCorrectNationality("UKR2"));
    }

    @Test
    public void testPassportNumber() {
        assertFalse(passenger.isCorrectPassportNumber("@@@@@e"));
        assertFalse(passenger.isCorrectPassportNumber("AAAA"));
        assertTrue(passenger.isCorrectPassportNumber("12345PP"));
        assertTrue(passenger.isCorrectPassportNumber("1234567"));
    }
}
