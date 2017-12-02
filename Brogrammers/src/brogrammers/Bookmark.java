/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brogrammers;

/**
 *
 * @author jay
 */
public class Bookmark {
    private String channel;
    public Bookmark(String channel) {
        this.channel = channel;
    }
    
    public String getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return channel;
    }
    
  
}
