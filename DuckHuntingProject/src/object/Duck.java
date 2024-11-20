
package object;

import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import Data.*;
import UserInterface.SoundSystem;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import lib.*;

public class Duck {
    private SoundSystem soundSystem ;
    public Point position;
    public Point velocity;
    public boolean alive = true;
    public int statusDuck = 1;
    //  0: miss - > bay ra ngoài màn hình 
    //  1: in screen -> phải ở trong màn hình
    
    
    
    public int statusDog = 0;
    //  0 : đi lên 
    //  1 : đứng im
    //  2 : đi xuống
    
    public ImageIcon   duckDieImageIcon = new ImageIcon();
    public ImageIcon   []duckImageIcon = new ImageIcon[4] ;
    
    public ImageIcon    []dogImageIcon = new ImageIcon[2];
    
    public JLabel      duckImage; 
    public JLabel      dogImage;
    
    public Duck(int round) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        soundSystem = new SoundSystem();
        soundSystem.stopSound(0);
        position = new Point();
        velocity = new Point();
        
        dogImageIcon[0] = new ImageIcon("src/assets/images/Dog1.png");
        dogImageIcon[1] = new ImageIcon("src/assets/images/Dog2.png");
        
        dogImage = new JLabel(dogImageIcon[0]);
        
        dogImage.setBounds(DATA.WIDTH / 2, DATA.SPAWN_HEIGHT + 140 , dogImageIcon[0].getIconWidth() , dogImageIcon[0].getIconHeight() );
        
        
        duckDieImageIcon = new ImageIcon("src/assets/images/DuckDie.png");

        for(int i = 0 ; i < 4;  i++){
            duckImageIcon[i] = new ImageIcon("src/assets/images/Duck"+String.valueOf(i)+".png");            
        }
        
        duckImage  = new JLabel(duckImageIcon[0]);
        spawn(round);
    }
    
    public void duckMove(int round){
        if(alive == true){
            if(statusDuck == 1){
                if(DATA.WIDTH <= position.x + duckImageIcon[0].getIconWidth()  
                        && 0 <= velocity.x ) {
                    velocity.x *= -1;
                    updateDisplay();
                }
                if(position.x <= 0 && velocity.x <= 0 ){
                    velocity.x *= -1;
                    updateDisplay();
                }
                if(DATA.SPAWN_HEIGHT - 20 <= position.y && 0 <= velocity.y){
                    velocity.y *= -1;
                    updateDisplay();
                }
                if(position.y <= 0  && velocity.y <= 0 ){
                    velocity.y *= -1;
                    updateDisplay();
                }                
            }else{ // bắn miss
                if( DATA.WIDTH < position.x 
                ||  position.x + duckImage.getSize().width < 0 
                ||  position.y + duckImage.getSize().height < 0 
                ||  DATA.SPAWN_HEIGHT + 20 < position.y){
                    java.util.Timer timer = new java.util.Timer ();
                    TimerTask spawnTimerTask = new TimerTask(){
                    @Override
                    public void run() {
                        spawn(round);
                        timer.cancel();                          
                        }
                    };
                    timer.schedule(spawnTimerTask, DATA.TIME_SPAWN / 2);   
                    
                }        
            }
            position.setLocation(position.x + velocity.x, position.y + velocity.y);
            duckImage.setLocation(position);            
        }
        else{
            if(position.y < DATA.SPAWN_HEIGHT + 50){
                position.setLocation(position.x , position.y + 8);
                duckImage.setLocation(position);                
            }
            else{  /// 1 cái bug ko đáng kể ở đây là: đặt là 5s nhưng thực chất là 10s 
                /// mô tả bug
                //  ban đầu sẽ mất 5s để spawn 
                //  trong thời gian 5s này thì alive vẫn là false nên vẫn thực hiện case này 
                //  dẫn đến 5s tiếp theo bị setLocation của spawn liên tục ->
                
                java.util.Timer timer = new java.util.Timer ();
                TimerTask spawnTimerTask = new TimerTask(){
                    @Override
                    public void run() {
//                        if(alive == false){ Không thể làm thế này 
//                          trong ̀5s đầu tiên khi hôi sinh lại sẽ gây ra bug do trong 5s đó case này vẫn thực hiện
                            spawn(round);
                            timer.cancel();                          
//                        }
                    }
                };
                timer.schedule(spawnTimerTask, DATA.TIME_SPAWN / 2);      
            }
        }
//        System.out.print(duckImage.getLocation().x + " ");
//        System.out.println(duckImage.getLocation().y);
    }
    
    public void updateDisplay(){ 
        int tmp = 0 ;
        if( velocity.x <= 0){
            if( velocity.y <= 0 )
                tmp = 0;
            else tmp = 2;
        }
        else{
            if( 0 <= velocity.y)
                tmp = 3;
            else tmp = 1;    
        }
        duckImage.setIcon(duckImageIcon[tmp]);
    }
    
    
    public void die(){
        alive = false;
        duckImage.setIcon(duckDieImageIcon);
        dogImage.setIcon(dogImageIcon[0]);

        // dogCatchDuck
        setupDogAnimation(1);
        
    }

    public void spawn (int round){
        //random
        alive = true;
        statusDuck = 1;
        Random random = new Random();
        
        Point tmpVelocity = new Point();               //       min -> max
//        int randomNum = rand.nextInt(max – min + 1) + min;
        tmpVelocity.x = random.nextInt(round + 3) + 3; // random: 3 -> 5 + round
        tmpVelocity.y = random.nextInt(round + 3) + 2; // random: 2 -> 4 + round

        int tmp = random.nextBoolean() ? -1 : 1;  // random hướng bay
        tmpVelocity.x *= tmp;
        
        position.setLocation(random.nextInt(DATA.WIDTH - 30 + 1 ) + 30, DATA.SPAWN_HEIGHT + 80 ); 
        velocity.setLocation(tmpVelocity);
//        0|1
//        2|3


        updateDisplay();
        duckImage.setBounds(position.x, position.y, duckImageIcon[0].getIconWidth(), duckImageIcon[0].getIconHeight());
    }
    
    public void setupDogAnimation(int Catch){
        
        java.util.Timer audioTimer = new java.util.Timer();
        TimerTask audioTimerTask = new TimerTask(){
            @Override
            public void run(){
            if(Catch == 1){
                soundSystem.playSound(3);
             }
             else{
                soundSystem.playSound(5);
             }             
            }
        };
        audioTimer.schedule(audioTimerTask, 1000);
        
        java.util.Timer timer1 = new java.util.Timer(); // status 0
        TimerTask timerTask1 = new TimerTask(){
            @Override
            public void run() {
                dogMove();
            }
        };
        timer1.schedule(timerTask1, 1000, 16);

        
        
        java.util.Timer timer2 = new java.util.Timer();
        TimerTask timerTask2 = new TimerTask(){
            @Override
            public void run() {
                statusDog = 1;
                timer2.cancel();
            }
        };
        timer2.schedule(timerTask2, 1600);

        java.util.Timer timer3 = new java.util.Timer();
        TimerTask timerTask3 = new TimerTask(){
            @Override
            public void run() {
                statusDog = 2;
                timer3.cancel();
            }           
        };
        timer3.schedule(timerTask3, 3200);

        java.util.Timer timer4 = new java.util.Timer();
        TimerTask timerTask4 = new TimerTask(){
            @Override
            public void run() {
                statusDog = 0;
                dogImage.setLocation(DATA.WIDTH / 2, DATA.SPAWN_HEIGHT + 140);
                timer4.cancel();
                timer1.cancel();
            }           
        };
        timer4.schedule(timerTask4, 3800);     
    }
    
    public void dogMove(){
        Point location = dogImage.getLocation();
        if(statusDog == 0 ){
            dogImage.setLocation(location.x, location.y - 6);
        }
        if(statusDog == 2){
            dogImage.setLocation(location.x, location.y + 6);
        }
        
    }
    
    public void reset(){
        spawn(0);
    }
}
