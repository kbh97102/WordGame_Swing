package core;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Music {
    private File filePath;
    private Clip musicClip;

    public Music(String filePath){
        this.filePath = new File(filePath);
        setClip();
    }
    public void setClip(){
        try{
            musicClip = AudioSystem.getClip();
            musicClip.open(AudioSystem.getAudioInputStream(filePath));
        }
        catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1){
            e1.printStackTrace();
        }
    }
    public void startLoopMusic(){
        musicClip.start();
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void startMusic(){
        musicClip.setFramePosition(0);
        musicClip.start();
    }
    public void stopMusic(){
        musicClip.stop();
    }
}
