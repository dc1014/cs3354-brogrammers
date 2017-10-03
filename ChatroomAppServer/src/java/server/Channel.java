package server;

import java.util.ArrayList;
import javax.websocket.Session;

public class Channel {
        final String name;
        ArrayList<User> users;
        public Channel(String name) {
            this.name = name;
            users = new ArrayList();
        }
        public boolean isEmpty() {
            return users.isEmpty();
        }
        public String add(User user) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).nickname.equals(user.nickname))
                    return "Nickname taken";
                if (users.get(i).session.getId().equals(user.session.getId()))
                    return "Already in channel";
            }
            users.add(user);
            return "Success";
        }
        public String removeBySession(Session s) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).session.getId().equals(s.getId())) {
                    users.remove(i);
                    return "Success";
                }
            }
            return "Error: Not in a channel";
        }
        
        @Override
        public String toString(){
            String str = name + " [";
            for (int i = 0; i < users.size(); i++)
                str += users.get(i).nickname + (i==users.size()-1?"":", ");
            return str + "]";
        }
    }