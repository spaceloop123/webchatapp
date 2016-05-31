package storage.message;

import common.models.Message;
import logging.Logger;
import logging.impl.Log;
import storage.message.listener.JSONMessageListener;
import storage.message.listener.MessageListener;
import utils.Portion;

import java.util.ArrayList;
import java.util.List;

public class InMemoryMessageStorage implements MessageStorage {

    private static InMemoryMessageStorage ourInstance = new InMemoryMessageStorage();

    public static InMemoryMessageStorage getInstance() {
        return ourInstance;
    }

    private InMemoryMessageStorage() {
        readMessages();
    }

    private static final String DEFAULT_PERSISTENCE_FILE = "C:\\Users\\Dell\\webchatapp\\resources\\messages.json";

    private static final Logger logger = Log.create(InMemoryMessageStorage.class);

    private List<Message> messages = new ArrayList<>();
    private MessageListener messageListener = new JSONMessageListener();

    @Override
    public synchronized List<Message> getPortion(Portion portion) {
        int from = portion.getFromIndex();
        if (from < 0) {
            throw new IllegalArgumentException(String.format("Portion from index %d can not be less then 0", from));
        }
        int to = portion.getToIndex();
        if (to != -1 && to < portion.getFromIndex()) {
            throw new IllegalArgumentException(String.format("Porting last index %d can not be less then start index %d", to, from));
        }
        to = Math.max(to, messages.size());
        return messages.subList(from, to);
    }

    @Override
    public void addMessage(Message message) {
        messages.add(message);
        messageListener.write(DEFAULT_PERSISTENCE_FILE, messages);
    }

    @Override
    public boolean updateMessage(Message message) {
        for (Message item : messages) {
            if (message.getId().equals(item.getId())) {
                item.setText(message.getText());
                item.setEdited(true);

                logger.info("Update message " + item);

                messageListener.write(DEFAULT_PERSISTENCE_FILE, messages);

                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized boolean removeMessage(String messageId) {
        for (Message item : messages) {
            if (messageId.equals(item.getId())) {
                item.setRemoved(true);

                logger.info("Removed message " + item);

                messageListener.write(DEFAULT_PERSISTENCE_FILE, messages);

                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return messages.size();
    }

    private synchronized void readMessages() {
        messageListener.read(DEFAULT_PERSISTENCE_FILE, messages);
    }
}
