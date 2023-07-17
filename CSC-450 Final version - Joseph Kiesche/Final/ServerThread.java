
/**
 * Write a description of class ServerThread here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.net.*;
import java.io.*;
import java.util.ArrayList;

class ServerThread extends Thread {
    private Server server;
    private Socket socket;
    //Constructor 
    public ServerThread(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }
    
    //Run function
    public void run() {
        try {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        boolean running = true;
        while (running) {
            String command = (String) in.readObject(); 
            if (command.equalsIgnoreCase("add")) {
                Object object = in.readObject();
                if (object instanceof Message) {
                    Message message = (Message) object;
                    server.addSong(message.getSongName());
                    //  Log in the console who added what song to make sure everything is working
                    System.out.println("Added song \"" + message.getSongName() + "\" from user " + message.getUserName());
                    out.flush();
                }
            
            } else if (command.equalsIgnoreCase("remove")) {
                    // Print the current playlist 
                    ArrayList<String> songs = server.getSongsList();
                    out.writeObject(songs);
                    out.flush();
                    
                    //  Read in index for the song that we want to remove
                    int index = in.readInt();
                    System.out.println(index);
                    
                    // Remove the song at given index
                    server.removeSong(index-1);
                    songs = server.getSongsList();  
                    out.writeObject(songs);  
                    out.flush();
                    
                    //  Print message to console for what index has been removed
                    System.out.println("Removed song at index " + index);
                    ArrayList<String> songs2 = server.getSongsList();
                    out.writeObject(songs2);
                    out.flush();
            } else if (command.equalsIgnoreCase("print")) {
                //  Create an arraylist and set it to the current songlist 
                ArrayList<String> songs = server.getSongsList();
                
                //  Send it over and flush it 
                out.writeObject(songs);
                out.flush();
            } else if (command.equalsIgnoreCase("quit")) {
                //  If quit set flag to false
                running = false;
            }
        }
    
        //  exceptions 
    } catch (IOException e) {
        System.out.println("Connection closed.");
    } catch (ClassNotFoundException e) {
        System.out.println("Received unknown object type from client.");
    }
}
}
