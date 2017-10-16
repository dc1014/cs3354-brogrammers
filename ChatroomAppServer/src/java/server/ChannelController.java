/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

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
    
    //instance of this to return for getInstance()
    private static ChannelController instance = null;
    
    //Delimiter for commands
    private final String DELIM = "/";
       
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
        int i = message.indexOf(DELIM);
        if (i == -1) {
            return "ERROR/Invalid command format";
        }
        String command = message.substring(0, i);
        message = message.substring(i+1);
        return execute(command, message, session);
    }
    
    //disconnects a user from all channels they are in.
    //Handles user being in multiple channels.
    public void disconnect(Session session){
        Channel c;
        while ((c=getChannelBySession(session)) != null)
            c.removeBySession(session);
    }
    
    //executes a command
    private String execute(String command, String message, Session session) {
        switch (command) {
            case "join": return joinChannel(message, session);
            case "send": return sendMessageToChannel(message, session);
            case "exit": return leaveChannel(message, session);
            case "list": Channel c = getChannel(message);
                        if(c != null)
                            return c.toString();
                        return "ERROR/Channel not found";
            default:  return "ERROR/Command not found";
        }
    }

    //attempts to put a user into a channel
    private String joinChannel(String message, Session session) {
        //parses for the format [channel_name]+[DELIM]+[nickname]
        int i = message.indexOf(DELIM);
        if (i == -1)
            return "ERROR/Invalid channel/nickname format";
        String channel_name = message.substring(0, i);
        String nickname = message.substring(i+1);
        
        if (channel_name.matches("^\\s*$") || channel_name.matches("^.*[^a-zA-Z0-9\\s].*$"))
            return "ERROR/Invalid channel name";
        
        Channel c = getChannel(channel_name);
        //if channel doesnt exist, make it
        if (c == null) {
            c = new Channel(channel_name);
            channels.add(c);
        }
        //return the result of attempting to add
        //either success or nickname taken
        return c.add(new User(session, nickname));
    }

    //removes user from a channel
    private String leaveChannel(String channelName, Session session) {
        Channel c = getChannel(channelName);
        if (c == null) return "ERROR/Not in that channel";
        String response = c.removeBySession(session);
        if (c.isEmpty())
            channels.remove(c);
        //returns the result of attempting to remove the user from the channel
        return response;
    }
  
    //sends a message to all users in the channel except the sender
    //returns success to the sender
    private String sendMessageToChannel(String message, Session session) {
        Channel c = getChannelBySession(session);
        if (c == null) return "ERROR/Not in a channel";
        String nickname = null;
        for (User u: c.users) {
            if (u.session.getId().equals(session.getId()))
                nickname = u.nickname;
        }
        if (nickname == null)
            return "ERROR/Not in a channel";
        for (User u: c.users) {
            if (!u.session.getId().equals(session.getId()))
                WebsocketServer.sendMessage("MESSAGE/"+nickname+">"+message, u.session);
        }
        return "SUCCESS";
    }

    //finds a channel given a user's session
    private Channel getChannelBySession(Session session) {
        Iterator li = channels.iterator();
        Channel c;
        while (li.hasNext()) {
            c = (Channel)li.next();
            for (User u: c.users) {
              if (u.session.getId().equals(session.getId()))
                  return c;
            }
        }
        return null;
    }

    //finds a channel given the channel_name
    private Channel getChannel(String name) {
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
