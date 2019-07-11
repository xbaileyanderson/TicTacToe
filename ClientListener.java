/**
 * ClientListener.java
 *
 * This class runs on the client end and just
 * displays any text received from the server.
 *
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.Socket;

import java.util.ArrayList;
import java.util.Scanner;

import static javafx.scene.input.KeyCode.PLAY;


public class ClientListener implements Runnable {
  private Socket connectionSock = null;
  private Player player = null;
  private boolean running;

  ClientListener(Socket sock, Player player) {
    this.connectionSock = sock;
    this.player = player;
    this.running = true;
  }

  /**
   * Gets message from server and displays it to the user.
   */
  public void run() {
    try {
      BufferedReader serverInput = new BufferedReader(
          new InputStreamReader(connectionSock.getInputStream()));
      while (running) {
        // Get data sent from the server
        String serverText = serverInput.readLine();
        if (serverInput != null) {
          //System.out.println("CLIENT DEBUG: " + serverText);
          parseResponse(serverText);
        }
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.toString());
    }
  }

  private void parseResponse(String serverResponse){
    //String[] serverResponseLines = serverResponse.split("\n");
    //String board = "";

    if(serverResponse.equals("PLAY")){  //Client turn
      player.play();
    }
    if(serverResponse.equals("SUCCESS")){ //Action was valid
      player.success();
    }
    if(serverResponse.equals("FAILURE")){  //Action was invalid
      player.failure();
    }
    if(serverResponse.equals("FINISHED")){  //Other Player leaves
      player.finished();
      running = false;
    }
    if(serverResponse.equals("WIN")){  //You win
      player.win();
      running = false;
    }
    if(serverResponse.equals("LOSE")){  //You lose
      player.lose();
      running = false;
    }
    if(serverResponse.equals("TIE")) {  //You tie
      player.tie();
      running = false;
    }
    else
      System.out.println(serverResponse);
  }


} // ClientListener for Player
