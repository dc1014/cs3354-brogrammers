package server;

import constants.Command;
import java.util.ArrayList;
//import javax.websocket.Session;

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
    
    
    public void updateUsers() {
        users.stream().forEachOrdered((u) -> {
            WebsocketServer.sendMessage(Command.LIST + Command.DELIM + this.toString(), u.session);
        });
    }

    @Override
    public String toString(){
        if (users.isEmpty()) return "";
        String str = users.get(0).nickname;
        for (int i = 1; i < users.size(); i++)
            str += Command.DELIM + users.get(i).nickname;
        return str;
    }

    public void emit(String string) {
        for (User u: users) {
            WebsocketServer.sendMessage(string, u.session);
        }
    }
}