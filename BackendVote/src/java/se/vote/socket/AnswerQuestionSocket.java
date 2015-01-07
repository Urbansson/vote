/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vote.socket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@ServerEndpoint("/answerquestion/{qId}")
public class AnswerQuestionSocket {

    private static final Map<Session, String> connections = Collections.synchronizedMap(new HashMap());

    @OnMessage
    public String onMessage(String message) {
        return "{\"success\": \"" + false + "\",\"code\": \"" + "Not supported" + "\" }";
    }

    @OnOpen
    public void onOpen(@PathParam("qId") String qId, final Session session) {
        // add the new session to the set
        //@PathParam("uId") String uId
        System.out.println("Recived Connection: " + qId);
        connections.put(session, qId);

    }

    @OnClose
    public void onClose(final Session session) {
        // remove the session from the set

        String qId = connections.get(session);

        // sessions.remove();
        System.out.println("Close " + qId);
        connections.remove(session);

        System.out.println("Size: " + connections.size());
    }

    // Static method - I don't like this at all
    public void broadcastAnswer(int questionId,int choice, String uId) throws IOException {
        //Check so only members get the broadcast

        String message = "{\"uId\": \"" + uId + "\",\"choice\": " + choice + "}";

        System.out.println("Send out: " + questionId);
        for (Session session : connections.keySet()) {
            System.out.println("Connected Quests: " + connections.get(session));
            System.out.println(connections.get(session).equalsIgnoreCase(questionId + ""));
        }

        for (Session session : connections.keySet()) {
            if (session.isOpen()) {
                if (connections.get(session).equalsIgnoreCase(questionId + "")) {
                    System.out.println("Sending");
                    session.getBasicRemote().sendText(message);
                }
            }
        }
    }
}
