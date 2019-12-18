package graphics.inGame;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class InfoPanel {
    private JPanel contentPanel;
    private JLabel scoreLabel;
    private JLabel score;
    private JLabel lifeLabel;
    private JLabel life;
    private Runnable updateScoreLife;
    private Supplier<Integer> getCurrentLife;
    private Supplier<Integer> getCurrentScore;
    private JLabel levelLabel = new JLabel("Level : ");
    private JLabel level = new JLabel();
    private int selectedLevel = 1;

    public InfoPanel(Supplier<Integer> getCurrentScore, Supplier<Integer> getCurrentLife) {
        initialize();
        this.getCurrentScore = getCurrentScore;
        this.getCurrentLife = getCurrentLife;
        contentPanel.add(lifeLabel);
        contentPanel.add(life);
        contentPanel.add(scoreLabel);
        contentPanel.add(score);
        level.setText(Integer.toString(selectedLevel));
        contentPanel.add(levelLabel);
        contentPanel.add(level);
        updateScoreLife.run();
    }
    private void initialize() {
        scoreLabel = new JLabel("Score : ");
        score = new JLabel();
        lifeLabel = new JLabel("Life : ");
        life = new JLabel();
        contentPanel = new JPanel();
        contentPanel.setOpaque(true);
        contentPanel.setBackground(Color.BLACK);
        scoreLabel.setFont(new Font("Arial ", Font.BOLD, 15));
        scoreLabel.setForeground(new Color(255, 255, 0));
        score.setFont(new Font("Arial ", Font.BOLD, 15));
        score.setForeground(new Color(255, 255, 0));
        lifeLabel.setFont(new Font("Arial ", Font.BOLD, 15));
        lifeLabel.setForeground(new Color(255, 255, 0));
        life.setFont(new Font("Arial ", Font.BOLD, 15));
        life.setForeground(new Color(240, 255, 0));
        levelLabel.setFont(new Font("Arial ", Font.BOLD, 15));
        levelLabel.setForeground(new Color(255, 255, 0));
        level.setFont(new Font("Arial ", Font.BOLD, 15));
        level.setForeground(new Color(255, 255, 0));
        updateScoreLife = () -> {
            score.setText(Integer.toString(getCurrentScore.get()));
            life.setText(Integer.toString(getCurrentLife.get()));
        };
    }
	public JPanel getContentPanel() {
        return contentPanel;
    }
    public Runnable getUpdateScoreLife() {
        return updateScoreLife;
    }
    public int getLevel(){
        return selectedLevel;
    }
    public void increaseLevel(){
        if(selectedLevel >= 10){
            selectedLevel = 10;
        }else{
            selectedLevel++;
        }

        level.setText(Integer.toString(selectedLevel));
    }
    public void decreaseLevel(){
        if(selectedLevel <= 1){
            selectedLevel = 1;
        }else{
            selectedLevel--;
        }

        level.setText(Integer.toString(selectedLevel));
    }
    public void updateLevel() {
        level.setText(Integer.toString(selectedLevel));
    }
}
