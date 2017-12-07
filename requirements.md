# Natural Language Specifications

## Definitions, Acronyms, and Abbreviations

* Server - the server responsible for managing and maintaining client socket connections, and handling application messages. 
* Client - the client consumer of the service provided by the consumer. Built for the desktop using JavaFX
* Channel - a chat room channel where messages are relayed to all users that are joined to that specific channel.
* Nickname - the name the client appears under in the channel.  
* Sockets - a method of transport using streams (buffers) over a TCP/IP socket.
* Bookmark - A channel reference object that is saved to provide an easier way of connecting to a channel without having to input it every time. 
* Commands - Actions the server will take on behalf of a Client request. These commands include: Join Room, Leave Room, and Send Message to Channel.

## Functional Requirements

### Server

1. Socket Management  
   1.1 Server should be able to create socket connection with Client.  
   1.2 Server should be able to destroy socket connection when Client disconnects

2. Channel Management  
   2.1 Server should add a Client to a channel if the Client nickname is not taken or reply if the nickname is taken.
   2.2 Server should create channel when adding a Client if the channel does not exist.
   2.3 When the last Client in a channel leaves, the Server should destroy the channel.  

3. Message Management
   3.1 Server should receive messages from Client and parse them into a command, execute, and reply with a String acknowledgment over the socket.
   3.2 Server should send channel messages to Clients who have joined a channel. 

### Client

1. Socket Management
   1.1 Client should be able to create socket connection with Server.

2. Nickname
   2.1 Client should be able to set default nickname

3. Bookmarks
   3.1 Client should display bookmarks.
   3.2 Client should be able to add bookmarks
   3.3 Client should be able to remove bookmarks.
   3.4 Client should be able to join bookmarked room.
   3.5 Client should be able to sort bookmarks.

4. Messages
   4.1 Client should be able to send a command encoded in a message to server
   4.2 Client should be able to receive message from server
   4.3 Client should receive error from server if connection fails or if nickname is taken when attempting to join a channel
   4.4 Client should prompted to join a channel after establishing its nickname
   4.5 Client should be able to receive messages from channels they have joined
   4.6 Client should be able to send a message requesting to leave a channel.
   4.7 The client should be able to bookmark the current channel, adding it to the bookmark list on the home page.
   4.8 Clients should be able to send message requesting Server to send message to all Clients who have joined the channel.

## Non-functional Requirements

   1. The server should respond to client messages within 1 second.

# Use Case Diagram
   ![use case diagrams](/use-cases.png "Use-Case-Diagram")
