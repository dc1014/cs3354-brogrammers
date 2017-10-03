package client;

import java.io.IOException;
import java.net.URI;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

@ClientEndpoint
public class WebsocketClient {

  private static final Logger LOGGER = Logger.getLogger(WebsocketClient.class.getName());
  private Session session;
  final String EXIT = "quit";
  
  public WebsocketClient() {       
        connectToWebSocket();
        Scanner s = new Scanner(System.in);
        String message;
        while(!(message=s.nextLine()).equals(EXIT)) {
            sendMessage(message);
        }
  }

  //called asynchronously when the connetion is established
  @OnOpen
  public void onOpen(Session session) {
    this.session = session;
    System.out.println("Connected");
  }

  //called asynchronously when a message is recieved
  @OnMessage
  public void onMessage(String message) {
    System.out.println("Server>"+message+"\n");
  }

  //called asynchronously when the connetion is lost
  @OnClose
  public void onClose() {
    connectToWebSocket();
  }


  public static void main(String[] args) {
    WebsocketClient wc = new WebsocketClient();
  }

  //sends a message to the remote endpoint if connected
  private void sendMessage(String message) {
      if (session!=null)
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            Logger.getLogger(WebsocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
  }

  //attempts to connect to the remote endpoint
  private void connectToWebSocket() {
    WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    try {
      URI uri = URI.create("ws://localhost:8080/server/websocket");
      container.connectToServer(this, uri);
    } catch (DeploymentException | IOException ex) {
      LOGGER.log(Level.SEVERE, null, ex);
      System.exit(-1);
    }
  }
}