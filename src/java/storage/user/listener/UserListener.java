package storage.user.listener;

import common.models.User;

import java.util.List;

public interface UserListener {
    void read(String fileName, List<User> list);

    void write(String fileName, List<User> list);
}
