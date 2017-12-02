/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brogrammers;

import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private LinkedList<String> lst_messages;
    private int MAX_LEN = 15;
    
    @FXML
    private void handleBtnSend(ActionEvent event) {
        if(!(txt_message.getText().isEmpty())){
            if(Debugger.getInstance().isDebug()){
                System.out.println("Message would have been sent but you're in Debug mode");
            }
            else{
                try {
                    WebSocket.WebsocketClient.getInstance().sendMessage(txt_message.getText());
                } catch (Exception ex) {
                    System.out.println("Couldn't send message: "+ex.getMessage());
                }
            }
            addMessage("You: "+txt_message.getText());
            txt_message.setText("");
        }
    }
    
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
    
    public void addMessage(String msg){
        if(lst_messages.size() >= MAX_LEN){
            lst_messages.removeFirst();
        }
        lst_messages.addLast(msg);
        renderMessages();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lst_messages = new LinkedList<>();
        ClientApp.channelController = this;
        
    }
    
    @Override
    public void finalize(){
        //On Channel Exit
    }
    
}
