/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brogrammers;

import WebSocket.WebsocketClient;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

/**
 *
 * @author dc
 */
public class ClientApp extends Application {
    
    private static final ArrayList<Bookmark> bookmarks = new ArrayList();
    
    public static ArrayList<Bookmark> getBookmarks() {
        return bookmarks;
    }
    
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLHomePage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Chat Room by Brogrammers");
        //Nickname Box
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Nickname");
        dialog.setHeaderText("Please enter your nickname");
        dialog.setContentText("Nickname:");
        Optional<String> result = dialog.showAndWait();
        
        result.ifPresent(name -> {
            try {
                System.out.println(name);
                WebsocketClient.getInstance().setNickname(name);
            } catch (Exception ex) {
                Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            stage.show();
        });
        
    }
    
    public static void print(String txt) {
        System.out.println(txt);
    }
    
    public static void parse(String command, String data) {
        System.out.println(command + "-" + data);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Debugger.getInstance().setDebug(true); //Change this to false to disable debug mode
        
        if(!Debugger.getInstance().isDebug()){
            while(true) {
                try {
                    WebsocketClient.getInstance().connectToWebSocket();
                } catch (Exception ex) {
                    Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
                    continue;
                }
                break;
            }
        }
        launch(args);
    }
    
}
