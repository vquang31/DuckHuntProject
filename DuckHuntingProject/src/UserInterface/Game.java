package UserInterface;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


import Data.*;
import java.awt.event.MouseAdapter;
import java.util.TimerTask;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import lib.*;
import object.*;



public class Game extends JFrame{
    Player player ;
    Duck duck ;
    SoundSystem soundSystem;
    int round = 0 ;
    int remainDuck = DATA.DUCK_PER_ROUND;
    int score = 0;
    int wrongPerRound = 0;
    boolean allowFire = false;  // để fix bug bắn chim lúc chim đang chết
    
    public int statusGame = 0 ;
    //////
    // 0 : chua choi
    // 1 : dang choi
    
    Font font ;

    BufferedImage cursorImg = ImageIO.read(getClass().getResource("/assets/images/Cursor.png"));
    Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(15,15), "");
    
   
    
    int width = DATA.WIDTH;
    int height = DATA.HEIGHT;

    // window 
    ImageIcon backgroundIcon;
//    BufferedImage backgroundImageBuffer ;
    JLabel      backgroundImage ;
    
    
    ImageIcon   grassIcon;
    JLabel      grassImage;
    
    ImageIcon   startButtonIcon;
    JLabel      startButtonImage;
    
    ImageIcon   exitButtonIcon;
    JLabel      exitButtonImage;
    
    ImageIcon   replayButtonIcon;
    
    /// display
    
    
    JLabel      roundLabel = new JLabel() ;
    
    ImageIcon   []tableImageIcon = new ImageIcon[3];
    JLabel      []tableLabel = new JLabel[3];
    
    ImageIcon   bulletImageIcon = new ImageIcon();
    JLabel      []bulletImage = new JLabel[3];   
    
    ImageIcon   []duckStatusImageIcon = new ImageIcon[3];
    JLabel      []duckStatusImage = new JLabel[10];
    
    
    JLabel      scoreTotalLabel = new JLabel();
      
    
    private void init() throws FontFormatException, IOException, UnsupportedAudioFileException, LineUnavailableException{
        
        
        
        font = Font.createFont(Font.TRUETYPE_FONT,new File("src/assets/fonts/Minecraft.ttf")).deriveFont(40f);
        
        Font font1 = Font.createFont(Font.TRUETYPE_FONT,new File("src/assets/fonts/Minecraft.ttf")).deriveFont(80f);


// init Data
        player = new Player();
        duck = new Duck(round);   
        soundSystem = new SoundSystem();
        soundSystem.playSound(0);
        // roundLabel
        
        roundLabel.setFont(font);
        roundLabel.setText("R=0");
        roundLabel.setBounds( 180, 700 , 500, 42);
        add(roundLabel);
        
        roundLabel.setVisible(false);
        
        /// table 1
        
        tableImageIcon[0] = new ImageIcon ("src/assets/images/BulletBox.png");
        tableLabel[0] = new JLabel(tableImageIcon[0]);
        tableLabel[0].setBounds( 140 , 600 , tableImageIcon[0].getIconWidth(), tableImageIcon[0].getIconHeight());

        
        bulletImageIcon = new ImageIcon("src/assets/images/Bullet.png");
        for(int i = 0 ; i < 3; i++){
            bulletImage[i]  = new JLabel(bulletImageIcon);
            bulletImage[i].setBounds(162 + i * 38 , 610, bulletImageIcon.getIconWidth(), bulletImageIcon.getIconHeight());
            add(bulletImage[i]);
            bulletImage[i].setVisible(false);
        }
        add(tableLabel[0]);
        tableLabel[0].setVisible(false);
         
        // table  2
        
        tableImageIcon[1] = new ImageIcon ("src/assets/images/DuckBox.png");
        tableLabel[1] = new JLabel(tableImageIcon[1]);
        tableLabel[1].setBounds( 333 , 600 , tableImageIcon[1].getIconWidth(), tableImageIcon[1].getIconHeight());

        
        for(int i = 0 ; i < 3;  i++)
        {
            duckStatusImageIcon[i] = new ImageIcon("src/assets/images/DuckStatus"+String.valueOf(i)+".png");
        }

        for(int i = 0 ; i < 10 ; i++)
        {
            duckStatusImage[i] = new JLabel(duckStatusImageIcon[0]);
            duckStatusImage[i].setBounds( 500 + i * 52, 625, duckStatusImageIcon[0].getIconWidth(), duckStatusImageIcon[0].getIconHeight());
            add(duckStatusImage[i]);
            duckStatusImage[i].setVisible(false);
        }
        add(tableLabel[1]);
        tableLabel[1].setVisible(false);
        
//        tableLabel[1].setBounds( , ,tableImageIcon[1].getIconWidth(), tableImageIcon[1].getIconHeight());


        /// table 3

        scoreTotalLabel.setFont(font);
        scoreTotalLabel.setText("0000000");
        scoreTotalLabel.setForeground (Color.white);
        scoreTotalLabel.setBounds(width - 245, height - 155, 500 , 42);
        add(scoreTotalLabel);
        scoreTotalLabel.setVisible(false);
        
        
        tableImageIcon[2] = new ImageIcon("src/assets/images/ScoreBox.png");
        tableLabel[2] = new JLabel(tableImageIcon[2]);
        tableLabel[2].setBounds(1000, 600,tableImageIcon[2].getIconWidth(), tableImageIcon[2].getIconHeight());        
        
        add(tableLabel[2]);
        tableLabel[2].setVisible(false);

        
        
        
        // initSource and setup
        
        grassIcon = new ImageIcon("src/assets/images/Grass.png");
        grassIcon = processImage.scaleImageIcon(grassIcon, width, 265);
        grassImage = new JLabel(grassIcon);         
        grassImage.setBounds(0,DATA.SPAWN_HEIGHT + 15  , grassIcon.getIconWidth(), grassIcon.getIconHeight());

        
        backgroundIcon = new ImageIcon("src/assets/images/Background.png");
        backgroundIcon = processImage.scaleImageIcon(backgroundIcon, width, height - 40);
        backgroundImage = new JLabel(backgroundIcon);        
        backgroundImage.setBounds(0,0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight()); 

        
        startButtonIcon = new ImageIcon("src/assets/images/StartButton.png");
        startButtonIcon = processImage.scaleImageIcon(startButtonIcon, 300, 100);
        
//        startButtonImage = new JLabel(startButtonIcon);
        startButtonImage = new JLabel("START");
        startButtonImage.setFont(font1);
        startButtonImage.setBounds(width / 2  ,
                                   height /3 - 200,
                                   800, 200);
        
        
        exitButtonIcon = new ImageIcon("src/assets/images/ExitButton.png");
        exitButtonIcon = processImage.scaleImageIcon(exitButtonIcon, 300, 100);
//        exitButtonImage = new JLabel(exitButtonIcon);
    
        exitButtonImage = new JLabel("EXIT");
        exitButtonImage.setFont(font1);
        exitButtonImage.setBounds(width / 2   ,
                                   height /3  ,
                                   800, 200);
        
        replayButtonIcon = new ImageIcon("src/assets/images/ReplayButton.png");
        
        this.add(grassImage);
        
        this.add(duck.duckImage);         
        this.add(duck.dogImage);
        
        this.add(exitButtonImage);
        this.add(startButtonImage);         
        this.add(backgroundImage);
        

        processEvent();
        
    }
    
    void processEvent(){
        java.util.Timer timer = new java.util.Timer();
        TimerTask moveDuckTask = new TimerTask() {
            @Override
            public void run() {
                duck.duckMove(round);
            }
        };
        timer.schedule(moveDuckTask, 1000, 16);        
        exitButtonImage.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        startButtonImage.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                resetGame();
                statusGame = 1;
                allowFire = true;

                duck.duckImage.setVisible(true);
                
                
                //
                startButtonImage.setVisible(false);
                exitButtonImage.setVisible(false);
                soundSystem.stopSound(0);
                
                
                roundLabel.setVisible(true);
                for(int i = 0 ; i < 3 ; i++){
                    tableLabel[i].setVisible(true);
                    bulletImage[i].setVisible(true);   
                }
                for(int i = 0 ; i < 10 ; i++){
                    duckStatusImage[i].setVisible(true);
                }
                scoreTotalLabel.setVisible(true);
                
            }
        });

        duck.duckImage.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                if(player.quantityBullet > 0 && allowFire == true && checkDuckInScreen()== true ){
                    duck.die();
                    /// audio
                    soundSystem.playSound(1);

                    
                    
                    // table 1
                    
                    // RELOAD BULLET
                    reloadBullet();


                    // update Tabel2
                    duckStatusImage[DATA.DUCK_PER_ROUND - remainDuck].setIcon(duckStatusImageIcon[1]);
                    remainDuck--;         

                    /// xử lý khi remainDuck = 0 
                    processNewRound();

                    // table3
                    score += player.quantityBullet*10;
                    String tmp = String.valueOf(score);
                    int tmp1 = tmp.length();
                    for(int i = 0 ; i < 7 - tmp1; i++){
                        tmp = "0" + tmp;
                    }
                    scoreTotalLabel.setText(tmp);                  
                }
            }
        });
        
        this.addMouseListener(new MouseAdapter(){ /// bắn hụt 
            @Override
            public void mousePressed(MouseEvent e){
                if(statusGame == 1){
                        if(player.quantityBullet > 0 && allowFire == true && checkDuckInScreen() == true){
                        player.fire();
                        
                        soundSystem.playSound(1);
                        
                        
                        bulletImage[player.quantityBullet].setVisible(false);
                        if(player.quantityBullet == 0){ /// hết đạn
                            wrongPerRound ++;

                            // reload
                            if(wrongPerRound !=3 ){
                               reloadBullet(); 
                            }
                            else{
                                for(int i = 0  ; i < 3; i ++){
                                    bulletImage[i].setVisible(true);
                                }
                                player.reload();
                            }
                            
                            duckStatusImage[DATA.DUCK_PER_ROUND - remainDuck].setIcon(duckStatusImageIcon[2]);
                            remainDuck--;
                            processNewRound();
                            duck.statusDuck = 0;
                            duck.dogImage.setIcon(duck.dogImageIcon[1]);
                            duck.setupDogAnimation(0);
                            
                                    
                            
                             // nếu sai 3 lần thì thua                            
                            if(wrongPerRound == 3) {
//                                timer.cancel();
                                duck.duckImage.setVisible(false);
                                startButtonImage.setText("RESTART");
                                startButtonImage.setVisible(true);
                                exitButtonImage.setVisible(true);
                                //
                                resetGame();
                            }                                                             
                        }
                    }                    
                }
            }
        });
    }
    
    public Game() throws IOException, FontFormatException, UnsupportedAudioFileException, LineUnavailableException {
//        backgroundImageBuffer = ImageIO.read(getClass().getResource("/assets/images/background.png"));
//        backgroundImage = new JLabel(new ImageIcon(backgroundImageBuffer));
        init();
        this.getContentPane().setCursor(cursor);
        this.setResizable(false);
        this.setLayout(null);
        this.setBounds(200, 200 , width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    public void processNewRound(){
        if(remainDuck == 0){
            java.util.Timer audioTimer = new java.util.Timer();
            TimerTask audioTimerTask = new TimerTask(){
                @Override
                public void run(){
                    soundSystem.playSound(4);           
                }
            };
            audioTimer.schedule(audioTimerTask, 3000);

            remainDuck = DATA.DUCK_PER_ROUND;
            round ++;
            wrongPerRound = 0;
            java.util.Timer timer = new java.util.Timer ();
            TimerTask timerTask = new TimerTask(){
                @Override
                public void run() {
                    for(int i = 0 ; i < 10 ; i++){
                        duckStatusImage[i].setIcon(duckStatusImageIcon[0]);                    
                        
                    }
                    roundLabel.setText("R=" +String.valueOf(round));
                    timer.cancel();
                }
            };
            timer.schedule(timerTask,4000);
        }    
    }
    
    public void reloadBullet(){ // hợp lý hiển thị 
        allowFire = false;
        java.util.Timer timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask(){
            @Override
            public void run() {
                for(int i = 0  ; i < 3; i ++){
                    bulletImage[i].setVisible(true);
                }
                player.reload();
                allowFire = true;
                timer.cancel();
            }
        };
        timer.schedule(timerTask, DATA.TIME_SPAWN + 1000);        
    }
    
    public boolean checkDuckInScreen(){ 
        /// check xem chim có trong khung hình không, không được bắn khi chim chưa spawn                                
        return (0 <= duck.position.x) 
                && (duck.position.x + duck.duckImageIcon[1].getIconWidth()<= DATA.WIDTH)
                && (0 <= duck.position.y)
                && (duck.position.y <= DATA.SPAWN_HEIGHT);

    }
    
    public void resetGame(){
        // audio
        soundSystem.playSound(6);

        round = 0 ;
        duck.reset();
        remainDuck = DATA.DUCK_PER_ROUND;
        score = 0;
        wrongPerRound = 0;
        allowFire = false;  // để fix bug bắn chim lúc chim đang chết
        statusGame = 0 ;
        
        roundLabel.setText("R=0");
        scoreTotalLabel.setText("0000000");
        
        for(int i = 0 ; i < 10  ; i++){
            duckStatusImage[i].setIcon(duckStatusImageIcon[0]);
        }
    
         
        
    }
    
}
