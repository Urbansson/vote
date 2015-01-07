/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vote.socket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


/**
 *
 * @author Teddy
 */
@ServerEndpoint("/broadcast")
public class broadcastSocket {

    private static final Set<Session> sessions
            = Collections.synchronizedSet(new HashSet<Session>());
 
    @OnOpen
    public void onOpen(final Session session) {
        // add the new session to the set
        sessions.add(session);
        System.out.println("Open");

    }

    @OnClose
    public void onClose(final Session session) {
        // remove the session from the set
        sessions.remove(session);
        System.out.println("Close");

    }

    @OnMessage
    public String onMessage(String message) {

        System.out.println("Message");

        return "Hejsan";
    }

    // Static method - I don't like this at all
    public void broadcastPoll(String message, String pollId) throws IOException {
        for (Session session : sessions) {
            if (session.isOpen()) {
                session.getBasicRemote().sendText(message);
            }
        }
    }
}
