package academy.itcloud.roman.users;

import java.util.HashSet;
import java.util.Set;

public class RegularUser extends User {

    private Set<Permission> permissions;

    public RegularUser() {
        super("","");
        this.permissions = new HashSet<>();
        permissions.add(Permission.VIEW_FLIGHTS);
        permissions.add(Permission.VIEW_PRICE_LIST);
        permissions.add(Permission.VIEW_DEPARTURES);
        permissions.add(Permission.VIEW_ARRIVALS);
    }

    @Override
    public boolean checkPermission(Permission permission)  {
        return permissions.contains(permission);
    }
}
