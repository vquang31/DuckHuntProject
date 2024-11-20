
package UserInterface;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class SoundSystem {
    
    
/*
    0 -> TitleScreen    /// menu
    1 -> Gunshot(SFX)   /// bắn
    2 -> Perfect
    3 -> GotDuck(s)     // bắn trúng
    4 -> RoundClear     // hết round    
    5 -> YouFailed      // bắn hụt
    6 -> GameOver       // thua
    
 */
    public Clip[] clip = new Clip[7];
    
    public SoundSystem() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
       
        for(int i = 0 ; i < 7; i++){
            clip[i] = AudioSystem.getClip();
            clip[i].drain();
        }
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("src/assets/audio/TitleScreen.wav"));
        clip[0].open(audioIn);
        clip[0].loop(Clip.LOOP_CONTINUOUSLY);
        
        audioIn = AudioSystem.getAudioInputStream(new File("src/assets/audio/Gunshot(SFX).wav"));
        clip[1].open(audioIn);

        audioIn = AudioSystem.getAudioInputStream(new File("src/assets/audio/Perfect.wav"));
        clip[2].open(audioIn);
        
        audioIn = AudioSystem.getAudioInputStream(new File("src/assets/audio/GotDuck(s).wav"));
        clip[3].open(audioIn);
        
        audioIn = AudioSystem.getAudioInputStream(new File("src/assets/audio/RoundClear.wav"));
        clip[4].open(audioIn);
        
        audioIn = AudioSystem.getAudioInputStream(new File("src/assets/audio/YouFailed.wav"));
        clip[5].open(audioIn);
        
        audioIn = AudioSystem.getAudioInputStream(new File("src/assets/audio/GameOver.wav"));
        clip[6].open(audioIn);
        
    }
    
    public void playSound(int i){
        clip[i].setFramePosition(0);
        clip[i].start();
    }
    public void stopSound(int i){
        clip[i].stop();
    }
    
}
