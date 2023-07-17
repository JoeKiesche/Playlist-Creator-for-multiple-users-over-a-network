
/**
 * Write a description of class Client here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.*;
import java.net.*;
import java.util.*;

class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String hostName = args[0];
        int portNumber = 5555;
        String userName = args[1];

        try {
            Socket socket = new Socket(hostName, portNumber);
            System.out.println("Connected to server.");
            
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            
            boolean running = true;
            while (running) {
                System.out.println("\nEnter command:");
                System.out.println("add");
                System.out.println("remove");
                System.out.println("print");
                System.out.println("quit");
                String usrChoice = scanner.nextLine();

                if (usrChoice.equalsIgnoreCase("add")) {
                    System.out.println("\nEnter song name:");
                    String songName = scanner.nextLine();
                    
                    // Send the command string
                    out.writeObject("add");  
                    
                    // Send the Message object
                    out.writeObject(new Message(songName, userName));  
                    out.flush();
                
                } else if (usrChoice.equalsIgnoreCase("remove")) {
                    
                    // Send the command string
                    out.writeObject("remove"); 
                    out.flush();

                    // Create a instance of ArrayList and use in.readObject();
                    ArrayList<String> songs = (ArrayList<String>) in.readObject();
                    System.out.println("\nCurrent songs:");
                    
                    //  Counter for printing indexes
                    int counter = 0;
                    for (String song : songs) {
                        counter++;
                        System.out.println(counter + ". " + song);
                    }
                    System.out.println("Enter song index:");
                    
                    //  Take in the int of the removed song and send it
                    int index = Integer.parseInt(scanner.nextLine());
                    out.writeInt(index);
                    out.flush();
                    
                    ArrayList<String> songs2 = (ArrayList<String>) in.readObject();
                    System.out.println("\nCurrent songs:");
                    
                    //  Counter for printing indexes
                    int counter2 = 0;
                    for (String song : songs2) {
                        counter2++;
                        System.out.println(song);
                    }
                    
                } else if (usrChoice.equalsIgnoreCase("print")){
                    // Send the command string
                    out.writeObject("print");  
                    out.flush();

                    // Create a instance of ArrayList and use in.readObject();
                    ArrayList<String> songs = (ArrayList<String>) in.readObject();
                    System.out.println("\nCurrent songs:");
                    
                    
                    //  Counter for printing indexes 
                    int counter = 0;
                    for (String song : songs) {
                        counter++;
                        System.out.println(counter + ". " + song);
                    }
                
                } else if (usrChoice.equalsIgnoreCase("quit")){
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                
                } else {
                    System.out.println("Invalid input.");
                }
            }
            // Close in, out and socket 
            out.close();
            in.close();
            socket.close();
        
            //Exceptions 
        } catch (IOException e) {
            System.out.println("Could not connect to server.");
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            System.out.println("Received unknown object type from server.");
            System.exit(-1);
        }
    }
}

