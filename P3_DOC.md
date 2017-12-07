# Brogrammers - Websocket Client/Server Chatroom App

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

#### Client
1. Open `./Brogrammers` as a Netbeans project.
2. The project can be successfully built and ran by clicking Run Project (F6)

#### Server
1. Install Netbeans EE
2. Install the plugin Java EE Base
3. Open `./ChatroomAppServer` as a Netbeans project.
4. Right click the project and click <i>Resolve Missing Server Problem</i>
5. Click <i>Add Server...</i>
6. Select Glassfish Server
7. If Glassfish is not installed, click <i>Download Now</i> and choose the newest version
8. Continue through the wizard selecting all the default settings.
9. The ChatroomAppServer project should now be runnable.
10. Upon successfully starting up, a confirmation webpage should pop up.

Please note tests are also run through Netbeans.



## Implemented functionality includes:

1. User can set nickname for use in channels
2. Users can join channels
3. Users can see list of other users in the same channel
4. Users can send and receive messages in a channel
5. Users can leave a channel
6. Users can add channels to bookmarks
7. Users can alphabetically sort bookmarked channels
8. Users can join bookmarked channels via the Homepage
