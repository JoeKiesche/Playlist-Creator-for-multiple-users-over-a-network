
/**
 * Write a description of class Server here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private ArrayList<String> songs;
    
    /*
     * METHOD - server
     * PARAMETERS - None
     * RETURNS - None
     */
    public Server() {
        songs = new ArrayList<String>();
    }
    
    /*
     * METHOD - addSong: takes the name of the song given by the user and adds it to the playlist
     * PARAMETER - String of the song name
     * RETURNS - None
     */
    public void addSong(String song) {
        synchronized (songs) {
            songs.add(song);
        }
    }
    
    /*
     * METHOD - removeSong
     * PARAMETER - Int of the number song user wants removed
     * RETURNS - None   
     */
    public void removeSong(int index) {
        synchronized (songs) {
            if (index >= 0 && index < songs.size()) {
                songs.remove(index);
            }
        }
        //show if song was removed on server side
        System.out.println(songs);
    }

    /*
     * METHOD - printSongs
     * PARAMETER - None
     * RETURNS - NONE
     */
    public synchronized void printSongs() {
        System.out.println("Current songs:");
        for (String song : songs) {
            System.out.println(song);
        }
    }
    
    /*
     * METHOD - getSongsList
     * PARAMETER - None
     * RETURNS - ArrayList called songs
     */
    public ArrayList<String> getSongsList() {
        synchronized (songs) {
            return new ArrayList<>(songs);
        }
    }
    
    /*
     * MAIN FUNCTION
     */
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(5555);
        } catch (IOException e) {
            System.out.println("Could not listen on port 5555.");
            System.exit(-1);
        }

        Server server = new Server();
        System.out.println("Server started.");

        while (true) {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("Connection failed.");
                System.exit(-1);
            }

            ServerThread thread = new ServerThread(server, clientSocket);
            thread.start();
        }
    }
}