package scene;

import javax.swing.*;
import java.awt.*;

public class EasyButton {

    private JPanel buttonContentPanel;
    private JButton difficultyButton;
    private JLabel blindOption;
    private JLabel speedOption;
    private JLabel bestScore;
    private JLabel healWordOption;


    private String difficulty;
    private String existBlind;
    private int speed;
    private int highScore;
    private String existHeal;


    public EasyButton(String difficulty, String existBlind, String existHeal, int speed, int highScore){
        init();
        this.difficulty = difficulty;
        this.existBlind = existBlind;
        this.existHeal = existHeal;
        this.speed = speed;
        this.highScore = highScore;

        difficultyButton.setText(difficulty);
        blindOption.setText(existBlind);
        healWordOption.setText(existHeal);
        speedOption.setText(Integer.toString(speed));
        bestScore.setText(Integer.toString(highScore));


        buttonContentPanel.setLayout(new GridLayout(9,1,0,5));
        //buttonContentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK ,1));
        buttonContentPanel.add(difficultyButton);
        buttonContentPanel.add(blindOption);
        buttonContentPanel.add(speedOption);
        buttonContentPanel.add(healWordOption);
        buttonContentPanel.add(bestScore);
        buttonContentPanel.add(new JLabel());
        buttonContentPanel.add(new JLabel());
        buttonContentPanel.add(new JLabel());

        buttonContentPanel.setBackground(Color.ORANGE);

    }

    public void init(){
        buttonContentPanel = new JPanel();
        difficultyButton = new JButton();
        blindOption = new JLabel();
        speedOption = new JLabel();
        healWordOption = new JLabel();
        bestScore = new JLabel();
    }
    public JPanel getEasyPanel(){
        return buttonContentPanel;
    }
    public JButton getButton(){
        return difficultyButton;
    }
}
