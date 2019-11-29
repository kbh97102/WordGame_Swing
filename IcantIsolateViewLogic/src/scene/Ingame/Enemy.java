package scene.Ingame;

import javax.swing.*;
import java.awt.*;

public class Enemy {
    private int xPos;
    private int yPos;
    private String imgPath;
    private String word;
    private JLabel testWordLabel;
    private int donwSpeed = 20;
    private boolean blind;
    private boolean heal;
    private boolean isAlive;

    public Enemy(String imgPath,String word,  int xPos, boolean isAlive){
        this.imgPath = imgPath;
        this.xPos = xPos;
        this.word = word;
        this.isAlive = isAlive;
        yPos = 0;
        blind = false;
        heal = false;
        testWordLabel = new JLabel();
        testWordLabel.setText(word);
        testWordLabel.setLocation(xPos,yPos);
    }

    public void changeBlind(boolean isBlind){
        this.blind = isBlind;
    }
    public void setBlind(){
        testWordLabel.setSize(new Dimension(100,20));
        testWordLabel.setOpaque(true);
        testWordLabel.setBackground(Color.BLACK);
    }
    public boolean isBlind(){
        return blind;
    }
    public void setHeal(){
        heal = true;
    }
    public int getYPos(){
        return yPos;
    }
    public JLabel getTestWordLabel(){
        return testWordLabel;
    }
    public boolean getIsAlive(){
        return isAlive;
    }
    public void changeIsAlive(boolean isAlive){
        this.isAlive = isAlive;
    }
    public void down(){
        yPos += donwSpeed;
    }
    public void draw(){
        testWordLabel.setLocation(xPos,yPos);
    }

}