TIC-TAC-TOE Game using client-server communication.

Please compile MtServer, ClientHandler, ClientListener, and Player to run this game. Please run MtServer on one cmd and Player on two different cmds to play this game.

This repo contains programs to implement a multi-threaded TCP chat server and client to play tic-tac-toe.

Player.java handles keyborad input from the user.
ClientListener.java recieves responses from the server and displays them
MtServer.java listens for client connections and creates a ClientHandler for each new client
ClientHandler.java recieves messages from a client and relays it to the other clients.
Bailey Contributions: ClientHandler.java Debugged ClientHandler.java Modified MtServer.java to get second socket to connect Added TIE case to game Final game testing

Brendan Contributions: Player.java ClientListener.java Debugged ClientHandler.java Modified ClientHandler.java to use a generic Player instead of two distinct Players Initial game testing (works in debug mode)
