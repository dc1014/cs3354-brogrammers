/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brogrammers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import comparator.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 *
 * @author dc
 */
public class FXMLHomePageController implements Initializable {
    
    @FXML
    private Label lbl_nickname;
    private TextField txt_channel;
    private Button btn_sortBookmarks;
    private VBox bookmarksBox;
    private boolean debug = true;
    
    @FXML
    private void handleBtnJoinChannel(ActionEvent e) {
        
    }
    
    @FXML
    private void handleBtnSortBookmarks(ActionEvent e){
        ArrayList<Bookmark> bookmarks;
        if(Debugger.getInstance().isDebug()){
            bookmarks = new ArrayList<>();
            bookmarks.add(new Bookmark("Test Channel A"));
            bookmarks.add(new Bookmark("Test Channel B"));
        }
        else{
            bookmarks = ClientApp.getBookmarks();
        }
        
        bookmarks.sort(new AlphabeticalComparator());
        renderBookmarks(bookmarks);
    }
    
    private void renderBookmarks(ArrayList<Bookmark> bookmarks){
        bookmarksBox.getChildren().remove(0, bookmarksBox.getChildren().size()-1);
        for (Bookmark b: bookmarks) {
            Button btn = new Button(b.getChannel());
            btn.setPrefWidth(391);
            bookmarksBox.getChildren().add(btn);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
