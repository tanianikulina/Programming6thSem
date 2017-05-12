package task_4;

import java.sql.*;

public class DBStuff {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://194.87.187.238/nikulina", "nikulina", "nikulina");
        PreparedStatement createLoginsPasswords = connection.prepareStatement("CREATE TABLE loginsPasswords(" +
        "id serial PRIMARY KEY, " +
        "login text UNIQUE, " +
        "password text NOT NULL)");
        createLoginsPasswords.execute();

        PreparedStatement insert = connection.prepareStatement("INSERT INTO loginsPasswords (login, password) VALUES" +
        "('tania', 'tania')," +
        "('admin', 'admin')");
        insert.execute();

        PreparedStatement createMessagesTable = connection.prepareStatement("CREATE TABLE messages(" +
                "id serial PRIMARY KEY, " +
                "login text UNIQUE, " +
                "message text NOT NULL)");
        createMessagesTable.execute();

        PreparedStatement insert2 = connection.prepareStatement("INSERT INTO messages (login, message) VALUES" +
                "('tania', 'hi')," +
                "('admin', 'hello')");
        insert2.execute();

        PreparedStatement select = connection.prepareStatement("SELECT * FROM messages");
        ResultSet resultSet = select.executeQuery();
        while (resultSet.next())
            System.out.println(resultSet.getString("login") + ": " + resultSet.getString("message"));
    }
}
