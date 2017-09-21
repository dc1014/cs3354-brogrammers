# Natural Language Specifications

## Definitions, Acronyms, and Abbreviations

* Server - the server responsible for managing and maintaining client socket connections, and handling application messages.
* Client - the client consumer of the service provided by the consumer. Built for the desktop using JavaFX
* Channel - a chat room channel where messages are relayed to all users that are joined to that specific channel.
* Nickname - the name the client appears under in the channel.
* Web Sockets - a method of transport using streams (buffers) over a TCP/IP socket.
* SSL - Secure Socket Layer
* TLS - Transport Layer Security


## Functional Requirements

### Server
1. Socket Management  
   1.1 Server should be able to create socket connection with client.  

2. Messaging  
   2.1 When the server receives a message, it locates the channel the sender is located, and then sends the message to all other clients in the room. Then the server sends an okay response to the sender. If the server cannot find such a channel, then it responds to the sender, telling the client to return to the Home Page.  

3. Channel Management  
   3.1 When a Client attempts to join a channel, the server puts the Client in as long as their nickname isn't taken and the channel exists. If the channel does not exist, the server will create the channel and then put the Client inside.  
   3.2 If a Client's nickname is taken, the server will inform the   client to pick a new name.
   3.3 When the last Client in a channel leaves, the Server will destroy the channel.  


### Client

1. Home Page  
   1.1 Upon launch, the Client should connect to the Server.  
   1.2 The User should be able to join a Channel. If the user does not have a default nickname set, the client will ask the user to enter a nickname.  
   1.3 If the server responds with the nickname is taken, the Client will inform the user to enter a different nickname.  
   1.4 If the Server responds with an okay, the Client will load the Chat Room scene, and allow the user to send chat messages.
   1.5 If the client loses connection with the server at any time, the client will return to the home page and attempt to reestablish connection. While attempting the connect, the user will have no control of the client, and the client will display an error screen explaining the issue.  
   1.6 The user should be able to view the channels they have bookmarked, and select them. Selecting the channel will cause the client to attempt to join the channel (see 1.2-1.4).

2. Chat Room  
   2.1 The Chat Room scene will contain a text field and a send button. When the send button is pressed, the text in the text field will be sent to the Server.  
   2.2 Users should be able to send messages to the channel. The client will then update the message interface with the new message with some indication that the message is sending, which it will remove when the server responds with an okay. If the server responds with an error, the Client will notify the user that the message failed to send and remove the message, or provide an option to attempt to resend the message.  
   2.3 The client should be listening for incoming messages from the server. When the Client receives a message, it is appended to the message interface, which is displayed above the text field and send button.  
   2.4 Users should be able to leave the channel. This will return the client to the Home Page scene.
   2.5 The client should be able to bookmark the current channel. This will add it to the bookmark list on the home page.


## Non-functional Requirements
   1. Messages should be handled via an encrypted channel.  
   2. Bookmarks should be stored Client side after the Client code terminates.  
   3. The server should respond to clients the have sent messages within 5 seconds.

# Use Case Diagram
   ![use case diagrams](/use-cases.png "Use-Case-Diagram")
