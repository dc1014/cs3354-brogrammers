# Natural Language Specifications

## Definitions, Acronyms, and Abbreviations

* Server - the server responsible for managing and maintaining client socket connections, and handling application messages.
* Client - the client consumer of the service provided by the consumer. Built for the desktop using JavaFX
* Channel - a chat room channel where messages are relayed to all users that are joined to that specific channel.
* Nickname - the name the client appears under in the channel.
* Web Sockets - a method of transport using streams (buffers) over a TCP/IP socket.
* SSL - Secure Socket Layer
* TLS - Transport Layer Security
* Publisher - A publishers is an entity which can ask a server to broadcast the message for other subscribers of its publication list
* Subscriber - A subscriber receives messages broadcast from a server via the publication list it registered for. 


## Functional Requirements

### Server
1. Socket Management  
   1.1 Server should be able to create socket connection with client.  
   1.2 Server should register client nickname for socket connection, or reply that it is taken
   1.3 Server should be able to destroy socket connection when client disconnects

2. Messaging  
   2.1 When the server receives a message from a user in a channel, it should publish that message to all the subscribers of that channel.
   2.2 When a server receives an unsubscribe message from a user in a channel, the user will be removed from subscription list of that channel

3. Channel Management  
   3.1 When a Client attempts to subscribe to a channel, it will either create the channel or return the existing instance.  
   3.2 When the last Client in a channel leaves, the Server will destroy the channel.  

### Client

1. Home Page  
   1.1 Upon launch, the Client should prompt for nickname and connect to the Server.  
   1.2 If the server responds with the nickname is taken, the Client will inform the user to enter a different nickname.  
   1.3 The User should prompted to join a channel after establishing its nickname
   1.4 If the Server responds with an okay, the Client will load the Channel scene, and allow the user to send chat messages.
   1.5 Show home page when client loses its socket connection with server.  
   1.6 Client should display bookmarks and allow user to navigate to a bookmarked channel.

2. Channel Management
   2.1 Client should be able to subscribe to a channel with the server.
   2.2 Client should be able to unsubscribe from a channel with the server.   
   2.3 The client should be able to bookmark the current channel. This will add it to the bookmark list on the home page.

3. Messaging  
    3.1 Client should be able to publish a message to a channel that it has joined
    3.2 Client should be able to receive messages for its subscriptions registered with server

## Non-functional Requirements

   1. Messages should be handled via an encrypted channel.  
   2. Bookmarks should be stored Client side after the Client code terminates.
   3. The server should respond to clients the have sent messages within 5 seconds.

# Use Case Diagram
   ![use case diagrams](/use-cases.png "Use-Case-Diagram")
