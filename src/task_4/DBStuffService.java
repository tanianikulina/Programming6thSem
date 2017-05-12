package task_4;

import java.sql.*;

public class DBStuffService {
    private Connection connection;

    public DBStuffService() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://194.87.187.238/nikulina", "nikulina", "nikulina");
        this.connection = connection;
    }
    public boolean check(String  login, String password) throws SQLException {
        PreparedStatement select = connection.prepareStatement("SELECT * FROM loginsPasswords WHERE login= ? AND password = ?");
        select.setString(1, login);
        select.setString(2, password);
        ResultSet resultSet = select.executeQuery();
        return resultSet.next();
    }

    public ResultSet getMessages() throws SQLException {
        PreparedStatement select = connection.prepareStatement("SELECT = * FROM messages");
        return select.executeQuery();
    }

    public void putMessage(String login, String message) throws SQLException {
        PreparedStatement insert = connection.prepareStatement("INSERT INTO messages (login, message) VALUES (?, ?)");
        insert.setString(1, login);
        insert.setString(2, message);
        insert.execute();
    }
}
