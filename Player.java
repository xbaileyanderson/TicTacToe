
import java.io.*;

import java.net.Socket;

import java.util.Scanner;

public class Player {
  public static void main(String[] args) {
    Player p = new Player();
    p.startListener();
  }

  private String hostname;
  private int port;

  private DataOutputStream serverOutput;
  private Thread listenerThread;
  private Scanner keyboard;


  public Player(){
    hostname = "localhost";
    port = 7654;

    keyboard = new Scanner(System.in);
    initNetworking();
  }

  private void initNetworking(){
    try{
      //Connect & Create
      System.out.println("Creating connection objects...");

      Socket connectionSock = new Socket(hostname, port);

      serverOutput = new DataOutputStream(connectionSock.getOutputStream());
      listenerThread = new Thread(new ClientListener(connectionSock, this));

      System.out.println("*Client listener created*");
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public void startListener(){
    System.out.println("*Starting thread*");
    listenerThread.start();

    System.out.println("Connecting to server on port " + port + "...");
    Thread.yield();
    }




  public void play(){

    System.out.println("Please enter the number corresponding to the space you would like to write on");
    System.out.println(
            "1,2,3"
                    + "\n4,5,6"
                    + "\n7,8,9\n");

      try{
        String data = keyboard.nextLine();
        serverOutput.writeBytes(data + "\n");
      }
      catch(Exception e){
        System.out.println(e.getMessage());
      }
  }

  //TODO: Add close methods
  public void success(){
    System.out.println("*Your was response was received by the server | your move was legitimate*");
    System.out.println("Now waiting for your opponents response...");
  }

  public void failure(){
    System.out.println("*Your response was received by the sever | your move was NOT legitimate*");
    System.out.println("PLEASE ENTER A LEGAL NUMBER BETWEEN 1-8");
    play();
  }

  public void finished(){
    System.out.println("*The other user disconnected, therefore you WIN (by default)*");
  }

  public void win(){
    System.out.println("YOU WON!");
  }

  public void lose(){
    System.out.println("*YOU LOST!*");
    System.out.println();
  }

  public void close(){
    try {
      System.out.println("Sending farewell msg to server...");
      serverOutput.write("GOODBYE".getBytes());

      listenerThread.join();
      System.out.println("*Thread Closed*");

      System.out.println("GOODBYE");
    }
    catch(Exception e){
      System.out.println(e.getMessage());
    }
  }

  public void tie() {
    System.out.println("*YOU TIED*");
    System.out.println();
  }
}
