/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import constants.Command;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.websocket.Session;

/**
 *
 * @author jay
 */
public class ChannelController {
    
    //Thread-safe set of channels
    private final Set<Channel> channels = Collections.synchronizedSet(new HashSet());
    public final Set<User> users = Collections.synchronizedSet(new HashSet());
    
    //instance of this to return for getInstance()
    private static ChannelController instance = null;
    
    //Delimiter for commands
    private final String DELIM = Command.DELIM;
       
    private ChannelController(){
    }
    
    //returns singleton instance of this
    public static ChannelController getInstance() { 
        if (instance == null)
            instance = new ChannelController();
        return instance;
    }
    
    
    //parses message recieved by server. the returned value is sent back to the user
    public String parse(String message, Session session) {
        System.out.println(message);
        int i = message.indexOf(DELIM);
        if (i == -1) {
            String response = Command.RESPONSE + Command.DELIM + Command.ERROR + Command.DELIM + "Invalid command format";
            WebsocketServer.sendMessage(response, session);
            return response;
        }
        String command = message.substring(0, i);
        message = message.substring(i+1);
        
        switch (command) {
            case Command.SETNAME: return setNickname(message, session);
            case Command.JOIN: return joinChannel(message, session);
            case Command.MESSAGE: return sendMessageToChannel(message, session);
            case Command.LEAVE: return leaveChannel(getUserBySession(session));                   
        }
        
        String response =  Command.RESPONSE + Command.DELIM + Command.ERROR + Command.DELIM + "Command not found";
        WebsocketServer.sendMessage(response, session);
        return response;
    }
    
    //disconnects a user from all channels they are in.
    //Handles user being in multiple channels.
    public void disconnect(Session session){
        User u = getUserBySession(session);
        users.remove(u);
        leaveChannel(u);
    }

    String setNickname(String nickname, Session session) {

        if (isInvalidNickname(nickname)) {
            String response = Command.RESPONSE + Command.DELIM + Command.SETNAME + Command.DELIM + Command.ERROR + Command.DELIM + "That nickname is invalid!";
            WebsocketServer.sendMessage(response, session);
            return response;
        }
        
        for(User user : users) {
            if (user.nickname.equals(nickname)) {
                String response = Command.RESPONSE + Command.DELIM + Command.SETNAME + Command.DELIM + Command.ERROR + Command.DELIM + "That nickname is taken";
                WebsocketServer.sendMessage(response, session);
                return response;
            }
        }

        users.add(new User(session, nickname));
        String response = Command.RESPONSE + Command.DELIM + Command.SETNAME + Command.DELIM + Command.SUCCESS + Command.DELIM + nickname;
        WebsocketServer.sendMessage(response, session);
        return response;
    }

    //attempts to put a user into a channel
    String joinChannel(String channel_name, Session session) {
        User u = getUserBySession(session);
        if (u == null) {
            String response = Command.RESPONSE + Command.DELIM + Command.SETNAME + Command.DELIM + Command.ERROR + Command.DELIM + "You must set a nickname first!";
            WebsocketServer.sendMessage(response, session);
            return response;
        }
        if (u.channel != null) {
            String response = Command.RESPONSE + Command.DELIM + Command.JOIN + Command.DELIM + Command.ERROR + Command.DELIM + "Already in a channel";
            WebsocketServer.sendMessage(response, session);
            return response;
        }
        if (isInvalidChannelName(channel_name)) {
            String response = Command.RESPONSE + Command.DELIM + Command.JOIN + Command.DELIM + Command.ERROR + Command.DELIM + "Invalid channel name";
            WebsocketServer.sendMessage(response, session);
            return response;
        }
        
        Channel c = getChannel(channel_name);
        //if channel doesnt exist, make it
        if (c == null) {
            c = new Channel(channel_name);
            channels.add(c);
        }
        
        c.updateUsers(u);
        c.users.add(u);
        u.channel = c;        
        
        String response = Command.RESPONSE + Command.DELIM + Command.JOIN + Command.DELIM + Command.SUCCESS + Command.DELIM + channel_name;
        WebsocketServer.sendMessage(response, session);
        return response;
    }
    
    boolean isInvalidChannelName(String channel_name) {
        return channel_name.matches("^\\s*$") || channel_name.matches("^.*[^a-zA-Z0-9\\s].*$");
    }
    
    boolean isInvalidNickname(String name) {
        return name.matches("^\\s*$") || name.matches("^.*[^a-zA-Z0-9\\s].*$") || name.toLowerCase().equals("you");
    }

    //removes user from a channel
    String leaveChannel(User u) {
        Channel c = u.channel;
        u.channel = null;
        c.users.remove(u);
        if (c.isEmpty())
            channels.remove(c);
        
        String response = Command.RESPONSE + Command.DELIM + Command.LEAVE + Command.DELIM + Command.SUCCESS + Command.DELIM + "Left channel - " + c.name;
        WebsocketServer.sendMessage(response, u.session);
        return response;
    }
  
    //sends a message to all users in the channel except the sender
    //returns success to the sender
    String sendMessageToChannel(String message, Session session) {
        User user = getUserBySession(session);
        if (user.channel == null) {
            String response = Command.RESPONSE + Command.DELIM + Command.MESSAGE + Command.DELIM + Command.ERROR + Command.DELIM + "Not in a channel";
            WebsocketServer.sendMessage(response, session);
            return response;
        }
        String response = Command.RESPONSE + Command.DELIM + Command.MESSAGE + Command.DELIM + Command.SUCCESS;
        WebsocketServer.sendMessage(response, session);
        
        String nickname = user.nickname;
        user.channel.emit(Command.MESSAGE + Command.DELIM + 
                    Command.SUCCESS + Command.DELIM + nickname+Command.DELIM+message);
        return response;
    }
    

    //finds a channel given a user's session
    User getUserBySession(Session session) {
        Iterator li = users.iterator();
        User u;
        while (li.hasNext()) {
            u = (User)li.next();
            if (u.session.getId().equals(session.getId()))
                return u;
        }
        return null;
    }
    
    User getUserByNickname(String nickname) {
        Iterator li = users.iterator();
        User u;
        while (li.hasNext()) {
            u = (User)li.next();
            if (u.nickname.equals(nickname))
                return u;
        }
        return null;
    }

    //finds a channel given the channel_name
    Channel getChannel(String name) {
        Iterator<Channel> li = channels.iterator();
        Channel c;
        while (li.hasNext()) {
            c = li.next();
            if (c.name.equals(name))
                return c;
        }
        return null;
    }
}
