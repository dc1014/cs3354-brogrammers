/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import javax.websocket.Session;

public class User {
        Session session;
        String nickname;
        public User (Session session, String nickname) {
            this.session = session;
            this.nickname = nickname;
        }
    }
