
package main;

import UserInterface.*;
import java.awt.FontFormatException;
import java.io.IOException;
import javax.swing.JLabel;
import object.*;



public class main {
    public static void main(String [] args) throws FontFormatException{
        Game game =  new Game();
//        x.setLocation(p);
    }
}

class Game{
    MainFrame mainFrame;
    Game() throws FontFormatException{
        try {
            mainFrame = new MainFrame(); 
        } catch (IOException e) {
            System.out.println("Không thể tải hình ảnh: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
