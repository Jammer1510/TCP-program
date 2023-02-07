/***************************************************************
 *  file: Server.java
 *  author: Timmy Lin
 *  class: CS 3700 – Computer Networks
 *
 *  assignment: program 1
 *  date last modified: 1/31/2021
 *
 *  purpose: This program implements a server that listens to incoming client requests, reads data from the client,
 *  adds the client number to the server number and sends the result back to the client.
 *
 ****************************************************************/
import java.io.IOException;
import java.net.*;
import java.util.Random;

public class Server {
  // Port number to run the server
  private static final int PORT_NUMBER = 8080;
  private static final String SERVER_NAME = "Server of Timmy Lin";
  
  public static void main(String[] args) {
    DatagramSocket socket;
    try {
      socket = new DatagramSocket(PORT_NUMBER);
      System.out.println("Server started......");
      
      // Continuously receive and process packets from clients
      while (true) {
        byte[] buf = new byte[4096];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        
        // Decode received data from client
        String data = new String(packet.getData(), 0, packet.getLength());
        String[] splitData = data.split(",");
        System.out.println("Client Name: " + splitData[0]);
        System.out.println("Server name: " + SERVER_NAME);
        
        // Generate a random number
        Random rand = new Random();
        int n = rand.nextInt(100) + 1;
        
        // Calculate sum of the number
        int sum_n = n + Integer.parseInt(splitData[1]);
        
        // Prepare the response message to send back to the client
        String msg = splitData[0] + ","+ splitData[1] +"," +"Server of Timmy Lin," + n + "," + sum_n;
        buf = msg.getBytes();
        packet = new DatagramPacket(buf, buf.length, packet.getAddress(), packet.getPort());
        
        // Send the response to the client
        socket.send(packet);
      }
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
