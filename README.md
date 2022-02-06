# TwoWayMesgPracticce
A java program that implements client/server messaging using ports

To run the program, first sart the server by running the following command:

`java TwoWayMesgServer.java 50000`

50000 is an example port number.

Then, in another terminal, run the following command to launch client:

`java TwoWayMesgClient.java localhost 50000`

This will connect the two terminals. Start by typing a message in the client terminal, then pressing enter. The message will show up on the server. The two terminals can continue sending messages back and forth until the user terminates the program or types `ctrl+d`.
