package graphics.inGame;

import graphics.Baldi.BaldiWork;

import javax.swing.*;
import java.awt.*;

public class VictoryPanel extends JPanel {
    private JButton nextLevelButton;
    private JButton exitButton;
    private Runnable nextLevel;

    public VictoryPanel(){
        initialize();
        add(nextLevelButton);
        add(exitButton);
        this.setPreferredSize(new Dimension(1280,720));

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image = new ImageIcon("Resource/Image/victory.jpg").getImage();
        g.drawImage(image,0,0,null);
    }
}
