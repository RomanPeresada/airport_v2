package academy.itcloud.roman.users;

import academy.itcloud.roman.db.implementsDAO.sqlite.queries.Queries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import static academy.itcloud.roman.db.create.db.CreateDB.getConnection;

public class Authenticator {

//    {
//        addUser("regular user");
//        addUser("admin","admin");
//    }

    public boolean addUser(String login, String password) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.INSERT_ADMIN)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addUser(String login) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.INSERT_REGULAR_USER)) {
            preparedStatement.setString(1, login);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public User findUser(String login, String password) {
        if (login.isEmpty()) {
            return new RegularUser();
        }
        try (Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(Queries.GET_ALL_USERS);
            while (resultSet.next()) {
                if (resultSet.getString("login").equals(login)) {
                    String tempPass = resultSet.getString("password");
                    if (!tempPass.equals(password)) {
                        throw new IllegalArgumentException("password is invalid");
                    } else {
                        return new Admin(login, password);

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("User is not exists");
    }
}
