package Scene.ingame;

import javax.swing.*;
import java.awt.*;

/**
 * word == It is similar to Enemy's Life
 * WordLabel == display Word // It can be replaced drawString but must consider very hard
 * Xpos == Enemy's X position( random )
 * Ypos == Enemy's Y position( default is 0)
 * heal == when state is true, convert healing Word and if user get this word userLife is increase
 * blind == when state is true, convert Blind word
 * alive == Judge Enemy is alive or dead
 * downSpeed == user can adjust Enemy's downspeed
 */
public class Enemy {
    private String word;
    private JLabel wordLabel;
    private int Xpos;
    private double Ypos;
    private boolean heal;
    private boolean blind;
    private boolean alive;
    private double downSpeed = 2;

    public Enemy(String word, int Xpos){
        this.word = word;
        this.Xpos = Xpos;
        Ypos = 0;
        heal = false;
        blind = false;
        alive = true;
        wordLabel = new JLabel(word);
        wordLabel.setBounds(Xpos, (int)Ypos, 100,30);
        wordLabel.setFont(new Font("고딕", Font.BOLD, 15));
    }


    public void changeAlive(boolean state){
        this.alive = state;
    }
    public void changeHeal(boolean state){
        this.heal = state;
    }
    public void changeBlind(boolean state){
        this.blind = state;
    }
    public void changeDownSpeed(int newSpeed){
        this.downSpeed = newSpeed;
    }
    public double getYpos(){
        return Ypos;
    }
    public boolean getAlive(){
        return alive;
    }
    public synchronized void move(int selectedLevel){
        Ypos += downSpeed*(double)selectedLevel;
    }
    public void draw(){
        wordLabel.setLocation(Xpos, (int)Ypos);
    }
    public JLabel getWordLabel(){
        return wordLabel;
    }
    public boolean isReachBottom(){
        return (Ypos > 500);
    }
    public String getWord(){
        return word;
    }
    public boolean isHeal(){
        return heal;
    }
    public boolean isBlind(){
        return blind;
    }
    public void setHealColor(){
        //TODO setColor
    }
}
