package academy.itcloud.roman.users;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Java on 27-Dec-17.
 */
public class Admin extends User {

    private Set<Permission> permissions;



    public Admin(String login, String password) {
        super(login, password);
        this.permissions = new HashSet<>();
        permissions.add(Permission.VIEW_FLIGHTS);
        permissions.add(Permission.VIEW_ARRIVALS);
        permissions.add(Permission.VIEW_DEPARTURES);
        permissions.add(Permission.VIEW_PRICE_LIST);
        permissions.add(Permission.VIEW_PASSENGERS_LIST);
        permissions.add(Permission.SEARCH_PASSENGERS_BY_THE_ARRIVAL);
        permissions.add(Permission.SEARCH_PASSENGERS_BY_THE_DEPARTURE);
        permissions.add(Permission.SEARCH_FLIGHT_NUMBER);
        permissions.add(Permission.SEARCH_PASSENGERS_BY_THE_FIRST_AND_LAST_NAME);
        permissions.add(Permission.SEARCH_PASSENGER_BY_THE_PASSPORT_NUMBER);
        permissions.add(Permission.SEARCH_PASSENGERS_BY_THE_PRICE);
        permissions.add(Permission.SEARCH_PASSENGERS_BY_THE_FLIGHT_NUMBER);
        permissions.add(Permission.ADD_FLIGHT_TO_THE_PASSENGER);
        permissions.add(Permission.ADD_PASSENGER);
        permissions.add(Permission.ADD_FLIGHT);
        permissions.add(Permission.ADD_PRICE);
        permissions.add(Permission.REMOVE_PASSENGER);
        permissions.add(Permission.REMOVE_FLIGHT);
        permissions.add(Permission.REMOVE_PRICE);
        permissions.add(Permission.UPDATE_FLIGHT);
        permissions.add(Permission.UPDATE_PRICE);
        permissions.add(Permission.UPDATE_PASSENGER);
    }

    @Override
    public boolean checkPermission(Permission permission) {
        return permissions.contains(permission);
    }
}
