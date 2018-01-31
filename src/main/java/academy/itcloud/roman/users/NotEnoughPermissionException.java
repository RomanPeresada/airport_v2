package academy.itcloud.roman.users;

public class NotEnoughPermissionException extends IllegalArgumentException {
    public NotEnoughPermissionException() {
        super("not enough permissions");
    }
}
