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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private boolean sortAlphabetical; //True=Alphabetical; False=Reverse Alphabetical;
    
    private void JoinChannel(String channelName){
        if(Debugger.getInstance().isDebug()) System.out.println( "User tried to join channel: " + channelName);
        //throw new UnsupportedOperationException(); //Because not implemented yet
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLChannelPage.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Channel "+channelName);
            stage.setScene(new Scene(root1));  
            stage.show();
        }
        catch(Exception ex){
            Logger.getLogger(FXMLHomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void handleBtnJoinChannel(ActionEvent e) {
        if(txt_channel.getText().isEmpty()){
            //Can't be empty. Add validation here.
        }
        else{
            JoinChannel(txt_channel.getText());
        }
    }
    
    @FXML
    private void handleBtnSortBookmarks(ActionEvent e){
        ArrayList<Bookmark> bookmarks = getBookmarks();
        bookmarks.sort(sortAlphabetical ? new AlphabeticalComparator() : new NegativeAlphabeticalComparator());
        sortAlphabetical = !sortAlphabetical;
        ((Button) e.getSource()).setText("Sort Bookmarks"+(sortAlphabetical?"":" Reverse")+" Alphabetically");
        renderBookmarks(bookmarks);
    }
    
    private ArrayList<Bookmark> getBookmarks(){
        ArrayList<Bookmark> bookmarks;
        if(Debugger.getInstance().isDebug()){
            bookmarks = new ArrayList<>();
            bookmarks.add(new Bookmark("Alpha"));
            bookmarks.add(new Bookmark("Gamma"));
            bookmarks.add(new Bookmark("Beta"));
        }
        else{
            bookmarks = ClientApp.getBookmarks();
        }
        return bookmarks;
    }
    
    private void handleChannelClick(ActionEvent e){
        String channelName = ((Button)(e.getSource())).getText();
        JoinChannel(channelName);
    }
    
    private void renderBookmarks(ArrayList<Bookmark> bookmarks){
        ObservableList<Node> children = bookmarksBox.getChildren();
        children.remove(0, children.size());
        for (Bookmark b: bookmarks) {
            Button btn = new Button(b.getChannel());
            btn.setOnAction(e->handleChannelClick(e));
            btn.setPrefWidth(391);
            children.add(btn);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sortAlphabetical = true;
        ArrayList<Bookmark> bookmarks = getBookmarks();
        renderBookmarks(bookmarks);
        try {
            lbl_nickname.setText(WebsocketClient.getInstance().getNickname());
        } catch (Exception ex) {
            lbl_nickname.setText("Nickname unknown or you're in debug mode");
            Logger.getLogger(FXMLHomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
