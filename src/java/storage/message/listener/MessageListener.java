package storage.message.listener;

import common.models.Message;

import java.util.List;

public interface MessageListener {
    void read(String fileName, List<Message> list);

    void write(String fileName, List<Message> list);
}
