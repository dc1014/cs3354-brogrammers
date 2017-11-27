/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSocket;


import java.net.URI;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import constants.Command;
import brogrammers.ClientApp;

@ClientEndpoint
public class WebsocketClient {

    private static WebsocketClient instance = null;

    /*
    *Gets the static singleton instance of WebsocketClient
    *
    *@return the singleton instance of WebsocketClient
    */
    public static WebsocketClient getInstance() throws Exception{
        if (instance == null) 
            instance = new WebsocketClient();
        return instance;
    }
    
    private Session session;
  
    /*
     *Constructs the WebsocketClient instance.
     */
    WebsocketClient() { 
        //No access modifier means that only the package can access it.
        //This trick was used to get around the fact that java does not have "friend" like c++
    }

    /*
     *Called asynchronously when the remote enpoint connecteion is established
     *
     *@param session an instance of the Session connection to the remote endpoint
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    /*
     *Called asynchronously when a message is recieved from the remote endpoint
     *Passes message on to client app to be parsed
     *
     *@param message the message received
     */
    @OnMessage
    public void onMessage(String message) {
        int i = message.indexOf(Command.DELIM);
        String command = message.substring(0, i);
        String data = message.substring(i+1);
        ClientApp.parse(command, data);
    }

    /*
     *Called asynchronously when the remote enpoint connecteion is closed
     */
    @OnClose
    public void onClose() {

    }


    /*
     *Sends a message to the remote endpoint.
     *This message will be sent to all users in the channel.
     *
     *@param message the message to be sent to the channel
     *@exception Exception throws exception if the message fails to send
     */
    public void sendMessage(String message) throws Exception {
        if (session!=null) {
            String data = Command.MESSAGE + Command.DELIM + message;
            session.getBasicRemote().sendText(data);
        }
    }
    
    /*
     *Attempts to set a nickname on the remote endpoint
     *
     *@param nickname The nickname to be set
     *@exception Exception throws exception if the message fails to send
     */
    public void setNickname(String nickname) throws Exception {
        if (session!=null) {
            String data = Command.SETNAME + Command.DELIM + nickname;
            session.getBasicRemote().sendText(data);
        }
    }
    
    /*
     *Attempts to join a channel on the remote endpoint
     *
     *@param name the name of the channel to be joined
     *@exception Exception throws exception if the message fails to send
     */
    public void joinChannel(String name) throws Exception {
        if (session!=null) {
            String data = Command.JOIN + Command.DELIM + name;
            session.getBasicRemote().sendText(data);
        }
    }
    
    /*
     *Attempts to leave a channel on the remote endpoint
     *
     *@param name the name of the channel to leave
     *@exception Exception throws exception if the message fails to send
     */
    public void leaveChannel(String name) throws Exception {
        if (session!=null) {
            String data = Command.LEAVE + Command.DELIM + name;
            session.getBasicRemote().sendText(data);
        }
    }
    
    /*
     *Requests a list of all users in the channel
     *
     *@exception Exception throws exception if the message fails to send
     */
    public void listUsersInChannel() throws Exception {
        if (session!=null) {
            String data = Command.LIST + Command.DELIM;
            session.getBasicRemote().sendText(data);
        }
    }

    /*
     *Attempts to connect to the remote endpoint via websocket connection
     *
     *@exception Exception thrown if unable to connect to remote endpoint
     */
    public void connectToWebSocket() throws Exception{
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        URI uri = URI.create("ws://localhost:8080/server/websocket");
        container.connectToServer(this, uri);
    }
    
    /*
     *Checks whether or not the WebsocketClient instance 
     *is connected to the remote endpoint
     *
     *@return true if connected, otherwise false
     */
    public boolean isConnected() {
        return session!=null;
    }
}
