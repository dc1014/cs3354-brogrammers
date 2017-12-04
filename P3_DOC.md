# Brogrammers - Websocket Client/Server App

## Design Pattern Used - Singleton

Our group used the singleton pattern. Please see:

* `./ChatroomAppServer/src/java/server/ChannelController.Java` lines 35 - 39 
* `./Brogrammers/src/WebSocket/WebsocketClient.java` lines 30 - 34

## Test Cases

We performed a combination of unit tests and a GUI test. 

* GUI Test - `./Brogrammers/test/brogrammers/ClientAppTest.java`
* Unit Test - 
    * Sort Function - `./Brogrammers/test/SortTest.java`
    * Debugger Singleton Unit Test - `./Brogrammers/test/brogrammers/DebuggerTest.java`
    * Channel Controller Behavior - `./ChatroomAppServer/test/server/ChannelControllerTest.java` 

## Building and Running Software 

1. Install Netbeans and Netbeans Web (for running Glassfish).
2.  and confirm it binds to a port
* Run 
