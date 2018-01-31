package academy.itcloud.roman.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FlightTest {
    Flight flight;

    @Before
    public void init() {
        flight = new Flight();
    }

    @Test
    public void testNumber() {
        String message = "number isn't valid";
        assertTrue(message, flight.isCorrectNumber("1234OU"));
        assertFalse(message, flight.isCorrectNumber("21"));
        assertFalse(message,flight.isCorrectNumber("214#@A"));
        assertFalse(message,flight.isCorrectNumber("qwerty123456"));
    }

    @Test
    public void testCity() {
        String message = "city isn't valid";
        assertTrue(message, flight.isCorrectCity("Kiev"));
        assertFalse(message, flight.isCorrectCity("minsk"));
        assertFalse(message, flight.isCorrectCity("London22"));
        assertFalse(message,flight.isCorrectCity("Odess@"));
    }

}
