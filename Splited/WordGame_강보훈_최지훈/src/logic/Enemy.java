package logic;

import javax.swing.*;
import java.awt.*;

public class Enemy {

    private String word;
    private JLabel wordLabel;
    private int xPos;
    private double yPos;
    private boolean heal;
    private boolean blind;
    private int downSpeed;
    public Enemy(int xPos,String word){
        this.xPos = xPos;
        this.yPos = 0;
        this.word = word;
        this.heal = false;
        this.blind = false;
        this.downSpeed = 10;
        wordLabel = new JLabel(word);
        wordLabel.setBounds(xPos,(int)yPos,150,30);
        wordLabel.setFont(new Font("SanSerif", Font.BOLD,15));
        
        wordLabel.setForeground(new Color(240, 255, 240));
    }
    public void move(int level){
        yPos += downSpeed*(level*0.5)/30;
    }
    public void draw(){
        wordLabel.setLocation(xPos,(int)yPos);
    }
    public String getWord(){
        return word;
    }
    public JLabel getWordLabel(){
        return wordLabel;
    }
    public boolean getHealState(){
        return heal;
    }
    public boolean getBlindState(){
        return blind;
    }
    public void setHeal(){
        this.heal = true;
    }
    public void setBlind(){
        this.blind = true;
    }
    public double getyPos(){
        return yPos;
    }
    public void setHealColor(){
    	wordLabel.setForeground(new Color(100, 150, 255));
        }
    public void setBlindColor(){
        wordLabel.setForeground(Color.RED);
        }
}
