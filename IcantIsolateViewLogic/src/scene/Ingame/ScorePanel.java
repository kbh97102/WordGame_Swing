package scene.Ingame;

import javax.swing.*;
import java.awt.*;

public class ScorePanel {

    private JPanel scorePanel;
    private JLabel scoreTextLabel;
    private JLabel scoreIntLabel;
    private int currentScore;
    private final int DEFAULT_SCORE = 0;
    private int scoreIncreasement = 1;
    private int scoreDecreasement = 1;

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
    //functional interface는 포함 관계가 아닌경우 사용 //서로 다른 객체를 다룰때

    public void scoreIncrease(){
        currentScore += scoreIncreasement;
    }
    public void scoreDecrease(){
        currentScore -= scoreDecreasement;
    }
}
