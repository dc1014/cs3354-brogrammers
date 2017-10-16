package server;

import java.util.ArrayList;
import javax.websocket.Session;

public class Channel {
        
    //name of the channel
    final String name;
    //list of users in channel
    ArrayList<User> users;
    public Channel(String name) {
        this.name = name;
        users = new ArrayList();
    }
    
    public boolean isEmpty() {
        return users.isEmpty();
    }
    
    //attempts to add the user to the channel
    //checks to ensure the requested nickname is free
    public String add(User user) {
        if (user.nickname.matches("^\\s*$") || user.nickname.toLowerCase().equals("you")
                || user.nickname.matches("^.*[^a-zA-Z0-9\\s].*$"))
            return "ERROR/Invalid nickname";
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).nickname.equals(user.nickname))
                return "ERROR/Nickname taken";
            if (users.get(i).session.getId().equals(user.session.getId()))
                return "ERROR/Already in channel";
        }
        users.add(user);
        updateUsers(user);
        return "CHANNEL/"+this.name;
    }
    
    //attempts to remove the user from a channel given their session id
    //checks to ensure that the user is in fact in the channel
    public String removeBySession(Session s) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).session.getId().equals(s.getId())) {
                users.remove(i);
                updateUsers(null);
                return "SUCCESS";
            }
        }
        return "ERROR/Not in a channel";
    }
    
    //updates all users in room except user
    private void updateUsers(User user) {
        for (User u: users) {
            if (user == null || !user.session.getId().equals(u.session.getId()))
                WebsocketServer.sendMessage(this.toString(), u.session);
        }
    }

    @Override
    public String toString(){
        String str = "USERS/[";
        for (int i = 0; i < users.size(); i++)
            str += "\""+users.get(i).nickname + (i==users.size()-1?"\"":"\", ");
        return str + "]";
    }
}