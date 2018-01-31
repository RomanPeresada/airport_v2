package academy.itcloud.roman.users;

public abstract class User {

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }


    public enum Permission {
        VIEW_FLIGHTS,
        VIEW_PRICE_LIST,
        VIEW_PASSENGERS_LIST,
        VIEW_ARRIVALS,
        VIEW_DEPARTURES,
        SEARCH_PASSENGER_BY_THE_PASSPORT_NUMBER,
        SEARCH_FLIGHT_NUMBER,
        SEARCH_PASSENGERS_BY_THE_PRICE,
        SEARCH_PASSENGERS_BY_THE_FIRST_AND_LAST_NAME,
        SEARCH_PASSENGERS_BY_THE_ARRIVAL,
        SEARCH_PASSENGERS_BY_THE_DEPARTURE,
        SEARCH_PASSENGERS_BY_THE_FLIGHT_NUMBER,
        ADD_FLIGHT_TO_THE_PASSENGER,
        ADD_FLIGHT,
        ADD_PASSENGER,
        ADD_PRICE,
        REMOVE_FLIGHT,
        REMOVE_PASSENGER,
        REMOVE_PRICE,
        UPDATE_FLIGHT,
        UPDATE_PASSENGER,
        UPDATE_PRICE,
    }

    public abstract boolean checkPermission(Permission permission);

}
