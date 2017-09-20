# Natural Language Specifications

## Definitions, Acronyms, and Abbreviations

* Server - the server responsible for managing and maintaining client socket connections, and handling application messages.
* Client - the client consumer of the service provided by the consumer. Built for the desktop using JavaFX
* Web Sockets - a method of transport using streams (buffers) over a TCP/IP socket.
* SSL - Secure Socket Layer
* TLS - Transport Layer Security
* Channel - a chat room channel where messages are relayed to all users that are joined to that specific channel.

## Functional Requirements

### Server
1. Socket Management  
   1.1 Server should be able to create socket connection with client.  
   1.2 Failure to connect to service or pass message should result in error.  
2. Messaging  
   2.1 Server should be able to pass message to all users in a channel.  

3. Channel Management  
   3.1 When a Client attempts to join a channel, the server puts the Client in as long as their nickname isn't taken and the channel exists. If the channel does not exist, the server will create the channel and then put the Client inside.  
   3.2 When the last Client in a channel leaves, the Server will destroy the channel.
   3.3 If a Client's nickname is taken, the server will inform the client to pick a new name.  

### Client

1. Socket Management  
   1.1. Client should be able to connect to the server.
    
2. Chat Room  
   1.1 Users should be able to set nickname.  
   2.2 Users should be able to join a channel.  
   3.3 Users should be able to send messages to the channel.  
   4.4 Users should be able to leave the channel.  


## Non-functional Requirements
   1. Messages should be handled via an encrypted channel.  
   2. Bookmarks should be stored Client side after the Client code terminates.  
