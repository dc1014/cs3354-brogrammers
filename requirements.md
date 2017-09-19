```# Natural Language Specifications 

## Definitions, Acronyms, and Abbreviations 

* Server - the server responsible for managing and maintaining client socket connections, and handling application messages.
* Client - the client consumer of the service provided by the consumer. Built for the desktop using JavaFX
* Web Sockets - a method of transport using streams (buffers) over a TCP/IP socket.
* SSL - Secure Socket Layer
* TLS - Transport Layer Security

## Functional Requirements

### Server

1. Socket Management
    1.1 Server should be able to create socket connection with client 
2. Messaging
    2.1 Server should be able to pass message to all users in a channel
3. Chat Rooms 

### Client

1. Users should be able to set nickname 
2. Users should be able to join a channel. 

## Non-functional Requirements 

1. Messages should be handled via an encrypted channel
2. Failure to connect to service or pass message should result in error```
