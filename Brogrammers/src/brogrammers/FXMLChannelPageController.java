/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brogrammers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private void handleBtnSend(ActionEvent event) {
        //TODO
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
