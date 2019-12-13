package Scene.ingame;

import javax.swing.*;
import java.awt.*;

public class ScorePanel {

    private JPanel scorePanel;
    private JLabel scoreTextLabel;
    private JLabel scoreIntLabel;
    private int currentScore;
    private final int DEFAULT_SCORE = 0;
    private int scoreIncreasement = 5;
    private int scoreDecreasement = 5;

    //TODO Change Font Name to English
    /**
     *  scoreDecrease will be used if Enemy reached bottom
     *  If user destroyed Enemy, scoreIncrease work
     *  Change scoreIncreasement,Decreasement is not implemented
     */
    public ScorePanel(){
        scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(2,1));
        currentScore = DEFAULT_SCORE;
        scoreIntLabel = new JLabel(Integer.toString(currentScore));
        scoreIntLabel.setFont(new Font("바탕", Font.BOLD, 30));
        scoreTextLabel = new JLabel("SCORE : ");
        scoreTextLabel.setFont(new Font("바탕", Font.BOLD, 30));

        scorePanel.add(scoreTextLabel);
        scorePanel.add(scoreIntLabel);
    }
    public JPanel getScorePanel(){
        return scorePanel;
    }
    public void scoreInit(){
        currentScore = 0;
    }
    public void scoreIncrease(){
        currentScore += scoreIncreasement;
        scoreIntLabel.setText(Integer.toString(currentScore));
    }
    public void scoreDecrease(){
        currentScore -= scoreDecreasement;
        scoreIntLabel.setText(Integer.toString(currentScore));
    }
}
