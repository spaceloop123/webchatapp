package storage.user;

import common.models.User;

public interface UserStorage {
    void addUser(User user);

    boolean updateUser(User user);

    boolean ifUserExists(User user);

    boolean ifUserLoginExists(User user);

    void updateStorage();

    User containsPassword(String password);
}
