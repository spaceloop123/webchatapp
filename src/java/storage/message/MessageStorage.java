package storage.message;

import common.models.Message;
import utils.Portion;

import java.util.List;

public interface MessageStorage {

    List<Message> getPortion(Portion portion);

    void addMessage(Message message);

    boolean updateMessage(Message message);

    boolean removeMessage(String messageId);

    int size();
}
