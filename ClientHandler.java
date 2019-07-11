/**
 * ClientHandler.java
 *
 * This class handles communication between the client
 * and the server.  It runs in a separate thread but has a
 * link to a common list of sockets to handle broadcast.
 *
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.Socket;

import java.util.ArrayList;
import java.util.Scanner;


public class ClientHandler implements Runnable {
  private Socket connectionSock = null;
  private ArrayList<Socket> socketList;

  private boolean playing = true;
  private boolean fail = false;
  private final int row = 3;
  private final int col = 3;
  private char[][] board ;


  Socket playerX;
  Socket playerO;
  private BufferedReader playerXInput;
  private DataOutputStream playerXOutput;
  private BufferedReader playerOInput;
  private DataOutputStream playerOOutput;


  public ClientHandler(ArrayList<Socket> socketList){
    this.socketList = socketList;
    board = new char[row][col];
    initializeBoard(board);
  }


  public void initializeBoard(char[][] board1) {
    for (int row1 = 0; row1 < 3; ++row1) {
      for (int col1 = 0; col1 < 3; ++col1) {
        board1[row1][col1] = '-';
      }
    }

  }

  public boolean checkIfTie(char[][]board3) {
    int counter = 0;
    for (int row2 = 0; row2 < 3; ++row2) {
      for (int col2 = 0; col2 < 3; ++col2) {
        if(board3[row2][col2] != '-') {
          ++counter;
        }

      }
    }
    if (counter == 9) {
      return true;
    }
    else
      return false;
  }

// xORo == 0 if x, == 1 if o
  public boolean checkIfWin(char[][] board2, int xORo) {
    if (xORo == 0) {
      //left to right
      if (board2[0][0] == 'x' && board2[0][1] == 'x' && board2[0][2] == 'x') {
        return true;
      }
      else if (board2[1][0] == 'x' && board2[1][1] == 'x' && board2[1][2] == 'x') {
        return true;
      }
      else if (board2[2][0] == 'x' && board2[2][1] == 'x' && board2[2][2] == 'x') {
        return true;
      }
      //going up and down
      else if (board2[0][0] == 'x' && board2[1][0] == 'x' && board2[2][0] == 'x') {
        return true;
      }
      else if (board2[0][1] == 'x' && board2[1][1] == 'x' && board2[2][1] == 'x') {
        return true;
      }
      else if (board2[0][2] == 'x' && board2[1][2] == 'x' && board2[2][2] == 'x') {
        return true;
      }
      //going diagonal
      else if (board2[0][0] == 'x' && board2[1][1] == 'x' && board2[2][2] == 'x') {
        return true;
      }
      else if (board2[2][0] == 'x' && board2[1][1] == 'x' && board2[0][2] == 'x') {
        return true;
      }
    }
    if (xORo == 1) {
      //left to right
      if (board2[0][0] == 'o' && board2[0][1] == 'o' && board2[0][2] == 'o') {
        return true;
      }
      else if (board2[1][0] == 'o' && board2[1][1] == 'x' && board2[1][2] == 'o') {
        return true;
      }
      else if (board2[2][0] == 'o' && board2[2][1] == 'o' && board2[2][2] == 'o') {
        return true;
      }
      //going up and down
      else if (board2[0][0] == 'o' && board2[1][0] == 'o' && board2[2][0] == 'o') {
        return true;
      }
      else if (board2[0][1] == 'o' && board2[1][1] == 'o' && board2[2][1] == 'o') {
        return true;
      }
      else if (board2[0][2] == 'o' && board2[1][2] == 'o' && board2[2][2] == 'o') {
        return true;
      }
      //going diagonal
      else if (board2[0][0] == 'o' && board2[1][1] == 'o' && board2[2][2] == 'o') {
        return true;
      }
      else if (board2[2][0] == 'o' && board2[1][1] == 'o' && board2[0][2] == 'o') {
        return true;
      }
    }
    return false;
  }

// xORo == 0 if x, == 1 if o
  public void playerMove(int move, char[][] gameboard, int xORo) {
    if (xORo == 0) {
      if (move == 1 && gameboard[0][0] != 'o' && gameboard[0][0] != 'x') {
        gameboard[0][0] = 'x';
      } else if (move == 2 && gameboard[0][1] != 'o' && gameboard[0][1] != 'x') {
        gameboard[0][1] = 'x';
      } else if (move == 3 && gameboard[0][2] != 'o' && gameboard[0][2] != 'x') {
        gameboard[0][2] = 'x';
      } else if (move == 4 && gameboard[1][0] != 'o' && gameboard[1][0] != 'x') {
        gameboard[1][0] = 'x';
      } else if (move == 5 && gameboard[1][1] != 'o' && gameboard[1][1] != 'x') {
        gameboard[1][1] = 'x';
      } else if (move == 6  && gameboard[1][2] != 'o' && gameboard[1][2] != 'x') {
        gameboard[1][2] = 'x';
      } else if (move == 7 && gameboard[2][0] != 'o' && gameboard[2][0] != 'x') {
        gameboard[2][0] = 'x';
      } else if (move == 8 && gameboard[2][1] != 'o' && gameboard[2][1] != 'x') {
        gameboard[2][1] = 'x';
      } else if (move == 9 && gameboard[2][2] != 'o' && gameboard[2][2] != 'x') {
        gameboard[2][2] = 'x';
      } else {
        fail = true;
      }
    } else if (xORo == 1) {
      if (move == 1 && gameboard[0][0] != 'o' && gameboard[0][0] != 'x') {
        gameboard[0][0] = 'o';
      } else if (move == 2 && gameboard[0][1] != 'o' && gameboard[0][1] != 'x') {
        gameboard[0][1] = 'o';
      } else if (move == 3 && gameboard[0][2] != 'o' && gameboard[0][2] != 'x') {
        gameboard[0][2] = 'o';
      } else if (move == 4 && gameboard[1][0] != 'o' && gameboard[1][0] != 'x') {
        gameboard[1][0] = 'o';
      } else if (move == 5 && gameboard[1][1] != 'o' && gameboard[1][1] != 'x') {
        gameboard[1][1] = 'o';
      } else if (move == 6  && gameboard[1][2] != 'o' && gameboard[1][2] != 'x') {
        gameboard[1][2] = 'o';
      } else if (move == 7 && gameboard[2][0] != 'o' && gameboard[2][0] != 'x') {
        gameboard[2][0] = 'o';
      } else if (move == 8 && gameboard[2][1] != 'o' && gameboard[2][1] != 'x') {
        gameboard[2][1] = 'o';
      } else if (move == 9 && gameboard[2][2] != 'o' && gameboard[2][2] != 'x') {
        gameboard[2][2] = 'o';
      } else {
        fail = true;
      }
    }
  }

  public String boardToString() {
    String r1 = board[0][0] + " " + board[0][1] + " " + board[0][2] + "\n";
    String r2 = board[1][0] + " " + board[1][1] + " " + board[1][2] + "\n";
    String r3 = board[2][0] + " " + board[2][1] + " " + board[2][2] + "\n";

    return (r1+ r2 + r3);
  }

  private void clientTurn(Socket client, BufferedReader clientIn, DataOutputStream clientOut, char moveNotation) {
    try {
      //Send PLAY msg to playerX
      String play = "PLAY\n";
      String boardPlayMsg = boardToString() + play;
      clientOut.writeBytes(boardPlayMsg);
      int playerChar = 0;

      //Wait for response
      boolean validMove = false;
      while (!validMove) {
        String clientMove = clientIn.readLine();

        if (clientMove != null) {

          //Sterilize as valid move
          try {
            int clientMoveInt = Integer.parseInt(clientMove);
            if(clientMoveInt > 0 && clientMoveInt < 10) {

              if(client == socketList.get(0)){
                playerMove(clientMoveInt, board, 0);
                playerChar = 0;
              }
              else {
                playerMove(clientMoveInt, board, 1);
                playerChar = 1;
              }


              if (fail) { //Not valid move
                clientOut.writeBytes(boardToString() + "FAILURE\n");
                fail = false;
              }
              else {
                clientOut.writeBytes("SUCCESS\n");
                //TODO Make Move Method
                validMove = true;
              }
            }
            else {  //Not correct number
              clientOut.writeBytes(boardToString() + "FAILURE\n");
            }
          }
          catch (Exception e) { //Not int
            clientOut.writeBytes(boardToString() + "FAILURE\n");
            break;
          }
        }
      }
      //WIN LOSE LOGIC
      if (checkIfWin(board, playerChar)) {
        if(moveNotation == 'x'){
          playerXOutput.writeBytes("WIN\n");
          playerOOutput.writeBytes("LOSE\n");
          System.out.println("GAME OVER");
        }
        else if (moveNotation == 'o'){
          playerOOutput.writeBytes("WIN\n");
          playerXOutput.writeBytes("LOSE\n");
          System.out.println("GAME OVER");
        }

      }
      if (checkIfTie(board)) {
        playerXOutput.writeBytes("TIE\n");
        playerOOutput.writeBytes("TIE\n");
        System.out.println("GAME OVER");
      }
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }



  /**
   * received input from a client.
   * sends it to other clients.
   */
  public void run() {
    try {
      //Wait and and create user objects
      playerX = socketList.get(0);
      System.out.println("Client X connected");

      //x's user
      try {
        playerXInput = new BufferedReader(
                new InputStreamReader(playerX.getInputStream()));
        playerXOutput = new DataOutputStream(playerX.getOutputStream());
        playerXOutput.writeBytes("Welcome to Tic Tac Toe. You are Player X, so you will be Xs. You will go first.\n"
                + "Waiting for other player to connect...\n");

        //O's user
        while (true) {
          if (socketList.size() == 2) {
            //Init second user and send beginning message
            Socket playerO = socketList.get(1);

            playerOInput = new BufferedReader(
                    new InputStreamReader(playerO.getInputStream()));
            playerOOutput = new DataOutputStream(playerO.getOutputStream());
            playerOOutput.writeBytes("Welcome to Tic Tac Toe. You are Player O, so you will be Xs. You will go second.\n");
            System.out.println("Client O connected");
            break;
          }
        }
        //Play the game
        System.out.println("*Beginning Game*");
        while (true) {
          if (!playing)
            break;
          clientTurn(playerX, playerXInput, playerXOutput, 'x');
          if (!playing)
            break;
          clientTurn(playerO, playerOInput, playerOOutput, 'o');
        }
      } catch (Exception e) {

      }
    }
    catch(Exception e){
    }
  }
} // ClientHandler for MtServer.java
