# Brogrammers - Websocket Client/Server App

## Design Pattern Used - Singleton

Our group used the singleton pattern. Please see:

* `./ChatroomAppServer/src/java/server/ChannelController.Java` lines 35 - 39 
* `./Brogrammers/src/WebSocket/WebsocketClient.java` lines 30 - 34
* .\Brogrammers\src\brogrammers\Debuger.java

## Test Cases

We performed a combination of unit tests and a GUI test. 

* GUI Test - `./Brogrammers/test/brogrammers/ClientAppTest.java`
* Sort Function - `./Brogrammers/test/SortTest.java`
* Debugger Singleton Unit Test - `./Brogrammers/test/brogrammers/DebuggerTest.java`
* Channel Controller Behavior - `./ChatroomAppServer/test/server/ChannelControllerTest.java` 

## Building and Running Software 

1. Install Netbeans and Netbeans Web
2. Install Glassfish
3. Ensure server is running and bound to a port on localhost.
4. Open `./Brogrammers` NetBeans project and compile/run application.  

Please note tests are also run through Netbeans.



Implemented functionality includes: 

1. User can set nickname for use in channels
2. Users can join channels
3. Users can see list of other users in the same channel
4. Users can send and receive messages in a channel 
5. Users can leave a channel
6. Users can add channels to bookmarks 
7. Users can alphabetically sort bookmarked channels 
8. Users can join bookmarked channels via the Homepage
