package scene.Ingame;

import core.Main;
import core.MainFrame;
import scene.PanelArray;
import scene.Scene;
import scene.WordManage;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Ingame extends PanelArray {

    private Ingame inGameInstance;

    //panel
    private JPanel ingameMainPanel;
    private JPanel ingameRightPanel;
    private JPanel scorePanel;
    private JPanel lifePanel;
    private JPanel userImagePanel;

    private JLabel testLabel;

    private JTextField userInputTF;

    private int goalCount=10;
    private int currentGoalCount=0;
    private JButton btn;

    private JToolBar toolBar;
    private JButton pauseButton;
    private JButton reStartButton;

    public Ingame(){

        init();
        wordData = WordManage.getInstance().getWordData();
        userInputTF.requestFocus();
        setToolBar();
        //test

        testLabel = new JLabel(generateWord());
        //add
        ingameMainPanel.setLayout(new BorderLayout());
        ingameMainPanel.add(userInputTF, BorderLayout.SOUTH);
        ingameMainPanel.add(testLabel, BorderLayout.NORTH);

        ingameRightPanel.setLayout(new GridLayout(3,1));
        ingameRightPanel.add(scorePanel);
        ingameRightPanel.add(lifePanel);
        ingameRightPanel.add(userImagePanel);

        contentPanel.add(ingameRightPanel, BorderLayout.EAST);
        contentPanel.add(ingameMainPanel, BorderLayout.CENTER);
        contentPanel.add(btn, BorderLayout.WEST);
        contentPanel.add(toolBar, BorderLayout.NORTH);

        //Event
        btn.addActionListener(event -> sceneChange.accept(Scene.MAIN));
        userInputTF.addActionListener(text->{
            String input = userInputTF.getText();
            userInputTF.setText("");
            testLabel.setText(generateWord());

        });
    }
    private void init(){
        //panel
        ScorePanel scp = new ScorePanel();
        LifePanel lp = new LifePanel();
        contentPanel = new JPanel();
        ingameMainPanel = new JPanel();
        ingameRightPanel = new JPanel();
        ingameRightPanel.setPreferredSize(new Dimension(320, MainFrame.WINDOW_HEIGHT));
        scorePanel = scp.getScorePanel();
        lifePanel = lp.getLifePanel();
        userImagePanel = new JPanel();
        userImagePanel.setBackground(Color.ORANGE);

        //ingameMainPanel
        userInputTF = new JTextField(30);

        contentPanel.setLayout(new BorderLayout());
        btn = new JButton("click");
    }
//    public Word generateWord(){
//        int randomIndex = (int)(Math.random()*wordData.size());
//        int randomXpos = (int)(Math.random()*(MainFrame.WINDOW_WIDTH-400));
//        int speed = 1; //레벨에따라 수정
//        String word = wordData.get(randomIndex);
//        Word selectedWord = new Word(word, randomXpos, speed);
//        return selectedWord;
//    }
    public String generateWord(){
        int randomIndex = (int)(Math.random()*wordData.size());
        String word = wordData.get(randomIndex);
        return word;
    }
    public void changeGoalCount(int goalCount){
        this.goalCount = goalCount;
    }
    public void currentGoalCountIncrease(){
        currentGoalCount++;
    }
    public void setToolBar(){
        toolBar = new JToolBar();
        pauseButton = new JButton("pause");
        reStartButton = new JButton("Restart");
        toolBar.add(pauseButton);
        toolBar.add(reStartButton);
    }
}
