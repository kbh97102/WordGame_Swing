package graphics.inGame;

import graphics.Baldi.BaldiWork;

import javax.swing.*;

public class VictoryPanel extends JPanel {
    private JButton nextLevelButton;
    private JButton exitButton;
    private Runnable nextLevel;

    public VictoryPanel(){
        initialize();
        add(nextLevelButton);
        add(exitButton);

        nextLevelButton.addActionListener(e -> nextLevel.run());
        exitButton.addActionListener(event -> System.exit(0));
    }
    private void initialize(){
        nextLevelButton = new JButton("NextLevel");
        exitButton = new JButton("Exit");
    }
    public void setNextLevel(Runnable nextLevel){
        this.nextLevel = nextLevel;
    }
}
