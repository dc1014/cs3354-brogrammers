/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brogrammers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 *
 * @author dc
 */
public class FXMLChannelPageController implements Initializable {
    
    @FXML
    private TextField txt_message;
    @FXML
    private VBox box_messages;
    @FXML
    private Button btn_bookmark;
    @FXML
    private VBox userBox;
    
    private LinkedList<String> lst_messages;
    private int MAX_LEN = 22;
    
    public String channelName;
    
    @FXML
    private void handleBtnSend(ActionEvent event) {
        if(!(txt_message.getText().isEmpty())){
            if(Debugger.getInstance().isDebug()){
                addMessage(ClientApp.nickname,txt_message.getText());
            }
            else{
                try {
                    WebSocket.WebsocketClient.getInstance().sendMessage(txt_message.getText());
                } catch (Exception ex) {
                    System.out.println("Couldn't send message: "+ex.getMessage());
                }
            }
            txt_message.setText("");
        }
    }
    
    public void setBookmark() {
        ArrayList<Bookmark> bookmarks = ClientApp.getBookmarks();
        btn_bookmark.setText("Click to Bookmark");
        for (Bookmark bm: bookmarks) {
            if (bm.getChannel().equals(channelName))
                btn_bookmark.setText("Bookmarked!");
        }
    }
    
    @FXML
    private void handleBookmark(ActionEvent event) {
        ArrayList<Bookmark> bookmarks = ClientApp.getBookmarks();
        for (Bookmark bm: bookmarks) {
            if (bm.getChannel().equals(channelName)) {
                btn_bookmark.setText("Click to Bookmark");
                bookmarks.remove(bm);
                ClientApp.homeController.renderBookmarks();
                return;
            }
        }
        btn_bookmark.setText("Bookmarked!");
        bookmarks.add(new Bookmark(channelName));
        ClientApp.homeController.renderBookmarks();
    }
    
    /**
     * Method used for rendering the messages. It clears the contents of the messages box and adds
     * a new label for every item in the linked list containing messages.
     */
    private void renderMessages(){
        ObservableList<Node> children = box_messages.getChildren();
        children.remove(0, children.size());
        for (Iterator<String> it = lst_messages.iterator(); it.hasNext();) {
            String s = it.next();
            Label lbl = new Label();
            lbl.setText(s);
            //btn.setPrefWidth(391);
            children.add(lbl);
        }
    }
    
    /**
     * Renders the users in the users box by clearing and then sequentially adding to the users box
    */
    protected void setUsers(ArrayList<String> names){
        ObservableList<Node> children = userBox.getChildren();
        children.remove(0, children.size());
        for (String n: names) {
            Label user = new Label(n);
            user.setPrefWidth(391);
            children.add(user);
        }
    }
    
    /**
     * Adds to the messages linked list (and removes old items if necessary)
     * @param name Username
     * @param msg Message
     */
    public void addMessage(String name, String msg){
        if(lst_messages.size() >= MAX_LEN){
            lst_messages.removeFirst();
        }
        if (name.equals(ClientApp.nickname))
            name = "you";
        lst_messages.addLast(name + ">" + msg);
        renderMessages();
    }
    
    /**
     * Refresh Page
     */
    public void clearChannel() {
        lst_messages = new LinkedList<>();
        renderMessages();
        channelName = null;
        setUsers(new ArrayList());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lst_messages = new LinkedList<>();
        
    }
    
    @Override
    public void finalize(){
        //On Channel Exit
    }
    
}
