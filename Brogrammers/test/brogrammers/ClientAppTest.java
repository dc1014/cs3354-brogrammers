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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.testfx.framework.junit.*;
import org.testfx.toolkit.*;
import org.testfx.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.hasText;

/**
 *
 * @author dc
 */
public class ClientAppTest extends ApplicationTest {
        
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLHomePage.fxml"));
   
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Test of start method, of class ClientApp.
     */
    @Test
    public void testStart() throws Exception {
        System.out.println("start");
        verifyThat("#btn_sortBookmarks", hasText("Sort Bookmarks Alphabetically"));
        verifyThat("#btn_joinChannel", hasText("Join"));   
    }
}
