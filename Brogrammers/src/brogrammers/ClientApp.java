/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brogrammers;

import WebSocket.WebsocketClient;
import constants.Command;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
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
    
    public static FXMLChannelPageController channelController;
    public static FXMLHomePageController homeController;
    
    public static String nickname;
    
    public static Scene channelScene;
    
    public static ArrayList<Bookmark> getBookmarks() {
        return bookmarks;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader channelLoader = new FXMLLoader(getClass().getResource("FXMLChannelPage.fxml"));
        Parent channelRoot = (Parent) channelLoader.load();
        channelController = channelLoader.getController();
        channelScene = new Scene(channelRoot);
        
        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("FXMLHomePage.fxml"));
        Parent root = homeLoader.load();
        homeController = homeLoader.getController();
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
    
    public static void parse(String[] data) {
        for (String datum: data){
            System.out.println(datum);
        }
        if (data.length == 0) return;
        if (data[0].equals(Command.RESPONSE)) {
            if (data.length == 1) return;
            switch (data[1]) {
                case Command.SETNAME:
                    if (data.length == 2) return;
                    if (data[2].equals(Command.SUCCESS)) {
                        if (data.length == 3) return;
                        nickname = data[3];
                    }
                    break;
                    
                case Command.JOIN:
                    if (data.length == 2) return;
                    if (data[2].equals(Command.SUCCESS)) {
                        if (data.length == 3) return;
                        enterChannelPage(data[3]);
                    }
                    break;
            }
        }
        if (data[0].equals(Command.MESSAGE)) {
            if (data.length == 1) return;
            if (data[1].equals(Command.SUCCESS)) {
                if (data.length == 2) return;
                Platform.runLater(() -> {
                    channelController.addMessage(data[2], data[3]);
                });
                
            }
        }
    }
    
    public static void sortBookmarks(Comparator<Bookmark> comp) {
        bookmarks.sort(comp);
    }
    
    public static void enterChannelPage(String channelName){
         Platform.runLater(() -> {
            Stage stage = new Stage();
            stage.setScene(channelScene);  
            stage.setTitle(channelName);
            channelController.channelName = channelName;
            channelController.setBookmark();
            stage.setOnCloseRequest(event -> {
                try {
                    channelController.clearChannel();;
                    WebsocketClient.getInstance().leaveChannel();
                } catch (Exception ex) {
                    Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            stage.show();
         });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Debugger.getInstance().setDebug(false); //Change this to false to disable debug mode
        
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
