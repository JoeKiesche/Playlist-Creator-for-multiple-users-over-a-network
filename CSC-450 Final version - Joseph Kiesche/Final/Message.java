import java.io.Serializable;

/**
 * Write a description of class Message here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
class Message implements Serializable {
    //Intialize Variables
    private String songName;
    private String userName;
    
    /*
     * Message object Constructor
     * PARAMETERS - String songName and String userName
     * RETURNS - None
     */
    public Message(String songName, String userName) {
        this.songName = songName;
        this.userName = userName;
    }
    
    /*
     * METHOD - getSongName
     * PARAMETER - None
     * RETURNS - songName 
     */
    public String getSongName() {
        return songName;
    }
    
    /*
     * METHOD - getUserName
     * PARAMETER - None
     * RETURNS - userName
     */
    public String getUserName() {
        return userName;
    }
}
