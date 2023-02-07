/***************************************************************
 *  file: Client.java
 *  author: Timmy Lin
 *  class: CS 370 – Computer Networks
 *
 *  assignment: program 1
 *  date last modified: 1/31/2023
 *
 *  purpose: This program is a client that connects to a server and sends a name and a number.
 *  The server responds with its own name and number, and the sum of the client and server numbers is displayed.
 ****************************************************************/
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {

  public static void main(String[] args) throws Exception {
    // address to connect to server
    InetAddress address = InetAddress.getByName("127.0.0.1");
    int port = 8080;

    // create a datagram socket
    DatagramSocket c = new DatagramSocket();
    System.out.println("Connected to server.....");

    // get user input
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter number between 1 to 100: ");
    int n = sc.nextInt();

    // create the message
    String msg = "Client of Timmy Lin," + n;
    byte[] sendData = msg.getBytes();

    // create a datagram packet with the data and address/port to send to
    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);

    // send the packet
    c.send(sendPacket);

    // receive the response from the server
    byte[] receiveData = new byte[4096];
    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
    c.receive(receivePacket);

    // get the data from the server
    String received = new String(receivePacket.getData());
    received = received.trim();
    String[] data = received.split(",");

    // display the data from the server
    System.out.println("Client Name: " + data[0]);
    System.out.println("Client number: " + data[1]);
    System.out.println("Server Name: " + data[2]);
    System.out.println("Server generated number: " + data[3]);
    System.out.println("Sum of number: " + data[4]);

    // close the socket
    c.close();
  }
}

