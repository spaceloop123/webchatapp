package storage.message;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import common.models.Message;
import logging.Logger;
import logging.impl.Log;
import utils.Portion;

import java.sql.*;
import java.util.List;

public class MySQLMessageStorage implements MessageStorage {
    private final String URL = "jdbc:mysql://localhost:3306/chat";
    private final String USERNAME = "root";
    private final String PASSWORD = "1234";

    //private static final String DEFAULT_PERSISTENCE_FILE = "C:\\Users\\Dell\\webchatapp\\resources\\messages.json";

    private static final Logger logger = Log.create(InMemoryMessageStorage.class);

    @Override
    public List<Message> getPortion(Portion portion) {
        return null;
    }

    @Override
    public void addMessage(Message message) {
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);

            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 Statement statement = connection.createStatement()) {
                statement.execute("INSERT INTO messages(id, author, text, timestamp) " +
                        "VALUES(" + message.getId() + "," + message.getAuthor() + "," +
                        message.getText() + "," + message.getTimestamp() + ") ");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateMessage(Message message) {
        return false;
    }

    @Override
    public boolean removeMessage(String messageId) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }
}
