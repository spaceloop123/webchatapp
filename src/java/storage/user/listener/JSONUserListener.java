package storage.user.listener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import common.models.User;
import logging.Logger;
import logging.impl.Log;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class JSONUserListener implements UserListener {

    private static final Logger logger = Log.create(UserListener.class);

    private Type itemsListType;
    private Gson gson;

    public JSONUserListener() {
        this.itemsListType = new TypeToken<List<User>>() {
        }.getType();
        gson = new Gson();
    }

    @Override
    public void read(String fileName, List<User> list) {
        try (Reader inputStreamReader = new InputStreamReader(new FileInputStream(fileName))) {
            List<User> listItems = Collections.synchronizedList(gson.fromJson(new JsonReader(inputStreamReader), itemsListType));
            list.addAll(listItems);
        } catch (FileNotFoundException e) {
            logger.error("File " + fileName + " not found", e);
        } catch (IOException e) {
            logger.error("IOException while reading " + fileName, e);
        }
    }

    @Override
    public void write(String fileName, List<User> list) {
        try (Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileName))) {
            List<User> listItems = Collections.synchronizedList(list);
            gson.toJson(listItems, outputStreamWriter);
        } catch (IOException e) {
            logger.error("Can't write chat history to " + fileName, e);
        }
    }
}
