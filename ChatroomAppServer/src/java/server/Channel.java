package server;

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
    
    //updates all users in room except user
    public void updateUsers(User user) {
        users.stream().filter((u) -> (user == null || !user.session.getId().equals(u.session.getId()))).forEachOrdered((u) -> {
            WebsocketServer.sendMessage(this.toString(), u.session);
        });
    }

    @Override
    public String toString(){
        String str = "USERS/[";
        for (int i = 0; i < users.size(); i++)
            str += "\""+users.get(i).nickname + (i==users.size()-1?"\"":"\", ");
        return str + "]";
    }

    public void emit(String string) {
        for (User u: users) {
            WebsocketServer.sendMessage(string, u.session);
        }
    }
}