/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vote.socket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import se.vote.bo.dao.PollDao;

/**
 *
 * @author Teddy
 */
@ServerEndpoint("/information/{uId}")
public class InformationSocket {

    //private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
    //private static final Set<Connection> sessions = Collections.synchronizedSet(new HashSet<Connection>());
    private static final Map<Session, String> connections = Collections.synchronizedMap(new HashMap());

    @OnOpen
    public void onOpen(@PathParam("uId") String uId, final Session session) {
        // add the new session to the set

        connections.put(session, uId);
        System.out.println("Open: " + uId);
        System.out.println("Size: " + connections.size());

    }

    @OnClose
    public void onClose(final Session session) {
        // remove the session from the set

        String uId = connections.get(session);

        // sessions.remove();
        System.out.println("Close " + uId);
        connections.remove(session);

        System.out.println("Size: " + connections.size());
    }

    @OnMessage
    public String onMessage(String message) {
        return "{\"success\": \"" + false + "\",\"code\": \"" + "Not supported" + "\" }";
    }

    // Static method - I don't like this at all
    public void broadcastPoll(int pollId) throws IOException {
        //Check so only members get the broadcast

        Set<String> members = PollDao.getPollMembers(pollId);

        String message = "{\"message\": \"" + "You have a new questoin to answer" + "\",\"pollId\": \"" + pollId + "\" }";

        for (Session session : connections.keySet()) {
            if (session.isOpen()) {
                if (members.contains(connections.get(session))) {
                    System.out.println("Sending broadcastPoll");

                    session.getBasicRemote().sendText(message);
                }
            }
        }
    }

}
