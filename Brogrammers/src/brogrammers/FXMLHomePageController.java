/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brogrammers;

import WebSocket.WebsocketClient;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import comparator.*;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
/**
 *
 * @author dc
 */
public class FXMLHomePageController implements Initializable {
    
    @FXML
    private Label lbl_nickname;
    @FXML
    private TextField txt_channel;
    @FXML
    private Button btn_sortBookmarks;
    @FXML
    private VBox bookmarksBox;
    private Comparator<Bookmark> comp; //True=Alphabetical; False=Reverse Alphabetical;
    
    public void setName() {
        lbl_nickname.setText(ClientApp.nickname);
    }
    
    @FXML
    private void handleBtnJoinChannel(ActionEvent e) {
        if(txt_channel.getText().isEmpty()){
            return;
        }
        String name = txt_channel.getText();
        joinChannel(name);
    }
    
    @FXML
    private void handleBtnSortBookmarks(ActionEvent e){
        comp = comp instanceof NegativeAlphabeticalComparator ? new AlphabeticalComparator() : new NegativeAlphabeticalComparator();
        ClientApp.sortBookmarks(comp);
        btn_sortBookmarks.setText("Sorted "+(comp instanceof AlphabeticalComparator?"A-Z":"Z-A"));
        renderBookmarks();
    }
    
    private void handleChannelClick(ActionEvent e){
        String name = ((Button)(e.getSource())).getText();
        joinChannel(name);
    }
    
    private void joinChannel(String name) {
        if (Debugger.getInstance().isDebug())
            ClientApp.enterChannelPage(name);
        else
            try {
                WebsocketClient.getInstance().joinChannel(name);
        } catch (Exception ex) {
            Logger.getLogger(FXMLHomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void renderBookmarks(){
        ObservableList<Node> children = bookmarksBox.getChildren();
        ClientApp.sortBookmarks(comp);
        children.remove(0, children.size());
        for (Bookmark b: ClientApp.getBookmarks()) {
            Button btn = new Button(b.getChannel());
            btn.setOnAction(e->handleChannelClick(e));
            btn.setPrefWidth(391);
            children.add(btn);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comp = new AlphabeticalComparator();
        renderBookmarks();
        if (ClientApp.nickname != null)
            lbl_nickname.setText(ClientApp.nickname);
        else 
            lbl_nickname.setText("NOT SET");
    }    
    
}
