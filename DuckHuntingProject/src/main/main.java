
package main;

import UserInterface.*;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JLabel;
import object.*;

public class main {
    public static void main(String [] args) throws FontFormatException, IOException, UnsupportedAudioFileException, UnsupportedAudioFileException, UnsupportedAudioFileException{
        try {
            Game game =  new Game();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
