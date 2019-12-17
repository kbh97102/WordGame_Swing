package graphics.inGame;

import logic.GameLogic;
import logic.Scene;
import logic.UpdateLife;
import logic.UpdateScore;

import javax.swing.*;
import java.awt.*;

public class InGamePanel extends JPanel{
    private JPanel contentPanel = new JPanel();
    private WordDropPanel wordDropPanel = new WordDropPanel();
    private InfoPanel infoPanel;
    private UpdateScore updateScore = new UpdateScore();
    private UpdateLife updateLife = new UpdateLife();
    private GameLogic gameLogic;
    private Runnable updateScoreLife;

    private JToolBar toolBar = new JToolBar();
    private JButton startButton = new JButton("Start");
    private JButton stopButton = new JButton("Stop");
    private JButton levelUpButton = new JButton("LevelUp");
    private JButton levelDownButton = new JButton("LevelDown");
    private JTextField userInputTF;
    private Runnable displayLevel;

    private Scene scene;

    public InGamePanel(){
        initialize();
        setToolBar();

        contentPanel.add(wordDropPanel.getContentPanel(), BorderLayout.CENTER);
        contentPanel.add(userInputTF, BorderLayout.SOUTH);

        userInputTF.addActionListener(e -> setUserInputTF());

        contentPanel.add(infoPanel.getContentPanel(), BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
    private void initialize(){
        gameLogic = new GameLogic(updateLife, updateScore);
        setLayout(new BorderLayout());
        userInputTF = new JTextField(30);
        userInputTF.setFont(new Font("바탕",Font.BOLD,20));
        gameLogic.setWordDropPanel(wordDropPanel.getContentPanel());
        infoPanel = new InfoPanel(updateScore.getCurrentScore(),updateLife.getCurrentLife());
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setOpaque(false);
        gameLogic.setUpdateScoreLIfe(infoPanel.getUpdateScoreLife());
        updateScoreLife = infoPanel.getUpdateScoreLife();
        displayLevel = () -> infoPanel.updateLevel();
        gameLogic.setIngamePanel(contentPanel);
        gameLogic.setPainting(this::painting);
        gameLogic.setIngamePanel(this);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backgroundImage = new ImageIcon("Resource/Image/back.jpg").getImage();
        painting(g, backgroundImage);
    }
    public void painting(Graphics g, Image image){
        g.drawImage(image,0,0,null);
    }
    private void setToolBar(){
        toolBar.add(startButton);
        toolBar.add(stopButton);
        toolBar.add(levelUpButton);
        toolBar.add(levelDownButton);

        startButton.addActionListener(event -> {
            gameLogic.newGame(infoPanel.getLevel());
        });
        stopButton.addActionListener(event -> stopButtonEvent());
        levelUpButton.addActionListener(event -> infoPanel.increaseLevel());
        levelDownButton.addActionListener(event -> infoPanel.decreaseLevel());
        this.add(toolBar, BorderLayout.NORTH);
    }
    private void stopButtonEvent(){
        wordDropPanel.getContentPanel().removeAll();
        wordDropPanel.getContentPanel().revalidate();
        wordDropPanel.getContentPanel().repaint();
        gameLogic.stopGame(displayLevel);
        updateScoreLife.run();
    }

    private void setUserInputTF(){
        gameLogic.checkAnswer(userInputTF.getText());
        userInputTF.setText("");
    }
}
