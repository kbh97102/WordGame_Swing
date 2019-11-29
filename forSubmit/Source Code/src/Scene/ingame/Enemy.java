package Scene.ingame;

import javax.swing.*;
import java.awt.*;

public class Enemy {
    private String word;
    private JLabel wordLabel;
    private int Xpos;
    private int Ypos;
    private boolean heal;
    private boolean blind;
    private boolean alive;
    private int downSpeed = 10;

    public Enemy(String word, int Xpos){
        this.word = word;
        this.Xpos = Xpos;
        Ypos = 0;
        heal = false;
        blind = false;
        alive = true;
        wordLabel = new JLabel(word);
        wordLabel.setBounds(Xpos, Ypos, 100,30);
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
    public int getYpos(){
        return Ypos;
    }
    public boolean getAlive(){
        return alive;
    }
    public synchronized void move(){
        Ypos += downSpeed;
    }
    public void draw(){
        wordLabel.setLocation(Xpos, Ypos);
    }
    public JLabel getWordLabel(){
        return wordLabel;
    }
    public boolean isReachBottom(){
        return (Ypos > 700);
    }
    public String getWord(){
        return word;
    }
}
