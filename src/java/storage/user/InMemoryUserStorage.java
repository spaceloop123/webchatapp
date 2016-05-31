package storage.user;

import common.models.User;
import logging.Logger;
import logging.impl.Log;
import storage.user.listener.JSONUserListener;
import storage.user.listener.UserListener;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserStorage implements UserStorage {

    private static InMemoryUserStorage ourInstance = new InMemoryUserStorage();

    public static InMemoryUserStorage getInstance() {
        return ourInstance;
    }

    private InMemoryUserStorage() {
        readUsers();
    }

    private static final String DEFAULT_PERSISTENCE_FILE = "C:\\Users\\Dell\\webchatapp\\resources\\users.json";

    private static final Logger logger = Log.create(InMemoryUserStorage.class);

    private List<User> users = new ArrayList<>();
    private UserListener listener = new JSONUserListener();

    private void readUsers() {
        listener.read(DEFAULT_PERSISTENCE_FILE, users);
    }

    @Override
    public void addUser(User user) {
        users.add(user);
        listener.write(DEFAULT_PERSISTENCE_FILE, users);
    }

    @Override
    public boolean updateUser(User user) {
        for (User item : users) {
            if (item.getLogin().equals(user.getLogin())) {
                item.setUsername(user.getUsername());

                logger.info("Update message " + item);

                listener.write(DEFAULT_PERSISTENCE_FILE, users);

                return true;
            }
        }
        return false;
    }

    @Override
    public boolean ifUserExists(User user) {
        for (User item : users) {
            if (item.getLogin().equals(user.getLogin())
                    && item.getPassword().equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean ifUserLoginExists(User user) {
        for (User item : users) {
            if (item.getLogin().equals(user.getLogin())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateStorage() {
        readUsers();
    }

    @Override
    public User containsPassword(String password) {
        for (User item : users) {
            if (item.getPassword().equals(password)) {
                return item;
            }
        }
        return null;
    }
}
