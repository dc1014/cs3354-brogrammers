/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket")
public class WebsocketServer {

    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
    
    
    //called asynchronously when a user connects
    @OnOpen
    public void onOpen(Session session) {
      sessions.add(session);
    }

    //called asynchronously when a user disconnects.
    //removes the user from any channels they were in
    @OnClose
    public void onClose(Session session) {
        ChannelController.getInstance().disconnect(session);
        sessions.remove(session);
    }

    //called asynchronously when a message is recieved
    @OnMessage
    public void onMessage(String message, Session session) {
        ChannelController.getInstance().parse(message, session);
    }
    
    //sends a String message to the given session
    public static void sendMessage(String message, Session session) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            Logger.getLogger(WebsocketServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
