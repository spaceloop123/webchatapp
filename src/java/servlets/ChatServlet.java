package servlets;

import common.models.Message;
import storage.message.InMemoryMessageStorage;
import storage.message.MessageStorage;
import utils.Constants;
import utils.InvalidTokenException;
import utils.MessageHelper;
import utils.Portion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet(value = "/chat")
public class ChatServlet extends HttpServlet {
    private MessageStorage messageStorage = InMemoryMessageStorage.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getQueryString();
        Map<String, String> map = queryToMap(query);
        String token = map.get(Constants.REQUEST_PARAM_TOKEN);
        try {
            int index = MessageHelper.parseToken(token);
            if (index > messageStorage.size()) {
                resp.sendError(400,
                        String.format("Incorrect token in request: %s. Server does not have so many messages", token));
            }
            Portion portion = new Portion(index);
            List<Message> messages = messageStorage.getPortion(portion);
            String responseBody = MessageHelper.buildServerResponseBody(messages, messageStorage.size());
            PrintWriter out = resp.getWriter();
            out.println(responseBody);
            out.flush();
        } catch (Exception e) {
            resp.sendError(400, "Bad Request");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Message message = MessageHelper.getClientMessage(req.getInputStream(), Constants.REQUEST_METHOD_POST);
            messageStorage.addMessage(message);
        } catch (ParseException | org.json.simple.parser.ParseException e) {
            resp.sendError(400, "Bad Request");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Message message = MessageHelper.getClientMessage(req.getInputStream(), Constants.REQUEST_METHOD_PUT);
            messageStorage.updateMessage(message);
        } catch (ParseException | org.json.simple.parser.ParseException e) {
            resp.sendError(400, "Bad Request");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Message message = MessageHelper.getClientMessage(req.getInputStream(), Constants.REQUEST_METHOD_DELETE);
            messageStorage.removeMessage(message.getId());
        } catch (org.json.simple.parser.ParseException | ParseException e) {
            resp.sendError(400, "Bad Request");
        }
    }

    private Map<String, String> queryToMap(String query) {
        return Stream.of(query.split(Constants.REQUEST_PARAMS_DELIMITER))
                .collect(Collectors.toMap(
                        keyValuePair -> keyValuePair.split("=")[0],
                        keyValuePair -> keyValuePair.split("=")[1]
                ));
    }
}
