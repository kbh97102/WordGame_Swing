package Scene.ingame;

import Scene.WordManage;
import Scene.ingame.thread.DrawTask;
import Scene.ingame.thread.LogicTask;
import Scene.ingame.thread.MakeWordTask;
import Scene.ingame.thread.MoveTask;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Vector;
import java.util.concurrent.*;

public class IngamePanel {
    //Jpanel
    private JPanel contentPanel;
    private JPanel ingameMainPanel;
    private JPanel ingameRightPanel;
    private JPanel scorePanel;
    private JPanel lifePanel;
    private JPanel userImagePanel;
    private JPanel wordDropPanel;
    private JPanel forLifeAndUserImage;
    private ScorePanel scp;
    private LifePanel lp;
    private JPanel wordManagePanel;
    private JPanel buttonPanel;
    private JPanel textFieldPanel;

    //button
    private JTextField textField;
    private JButton addButton;
    private JButton delButton;
    //toolBar button
    private JButton saveButton;
    private JButton stopButton;
    private JButton levelUpButton;
    private JButton levelDownButton;

    //Thread
    private DrawTask drawTask;
    private MakeWordTask makeWordTask;
    private MoveTask moveTask;
    private LogicTask logicTask;
    private ScheduledExecutorService loopService;
    ScheduledFuture<?> makeWordState;
    ScheduledFuture<?> logicState;
    ScheduledFuture<?> drawState;
    ScheduledFuture<?> moveState;

    //etc
    private JTextField userInputTF;
    private JToolBar toolBar;
    private Vector<String> wordData;
    private Vector<Enemy> enemies;
    private int goalCount = 3;
    private JButton startButton = new JButton("start");
    private int selectedLevel = 1;
    private JLabel levelLabel;


    //TODO toolbar Color, Frame Title Color Change

    public IngamePanel() {
        init();
        setting();
        userInputTF.requestFocus();
        setToolBar();

        drawTask = new DrawTask(enemies, wordDropPanel);
        makeWordTask = new MakeWordTask(enemies, wordDropPanel, wordData);
        moveTask = new MoveTask(enemies, selectedLevel);
        logicTask = new LogicTask(enemies, goalCount, this::shutdownThread, lp, wordDropPanel, scp,this::continueGame);

        //test
        levelLabel.setBounds(400,100,280,100);
        levelLabel.setFont(new Font("Gothic", Font.BOLD,50));
        String testtext = "Level = " + Integer.toString(selectedLevel);
        levelLabel.setText(testtext);
        //add
        wordDropPanel.add(levelLabel);
        ingameMainPanel.add(userInputTF, BorderLayout.SOUTH);
        ingameMainPanel.add(wordDropPanel, BorderLayout.CENTER);

        ingameRightPanel.add(scorePanel, BorderLayout.NORTH);
        ingameRightPanel.add(forLifeAndUserImage, BorderLayout.CENTER);


        //TODO Change panelName and Add UserImage
        forLifeAndUserImage.add(lifePanel, BorderLayout.NORTH);
        contentPanel.add(ingameRightPanel, BorderLayout.EAST);
        contentPanel.add(ingameMainPanel, BorderLayout.CENTER);
        contentPanel.add(toolBar, BorderLayout.NORTH);

        //event
        userInputTF.addActionListener(this::userInputTFEvent);
    }
    private void userInputTFEvent(ActionEvent e){
        String userInput = userInputTF.getText();
        for (Enemy enemy : enemies) {
            if (enemy.getAlive() && enemy.getWord().equals(userInput)) {
                if(enemy.isHeal()){
                    lp.lifeIncrease();
                }
                enemy.changeAlive(false);
                logicTask.changeGoalCount();
                scp.scoreIncrease();
            }
        }
        userInputTF.setText("");
    }
    /**
     * Object build
     */
    private void init() {
        //panel
        contentPanel = new JPanel();
        scp = new ScorePanel();
        lp = new LifePanel();
        contentPanel = new JPanel();
        ingameMainPanel = new JPanel();
        ingameRightPanel = new JPanel();
        scorePanel = scp.getScorePanel();
        lifePanel = lp.getLifePanel();
        userImagePanel = new JPanel();
        forLifeAndUserImage = new JPanel();
        wordDropPanel = new JPanel();
        textFieldPanel = new JPanel();
        wordManagePanel = new JPanel();
        buttonPanel = new JPanel();

        //etc
        levelLabel = new JLabel();
        userInputTF = new JTextField(30);
        wordData = new Vector<>();
        wordData = WordManage.getInstance().getWordData();
        enemies = new Vector<>();

        //button
        textField = new JTextField(15);
        addButton = new JButton("Add");
        delButton = new JButton("Delete");
        saveButton = new JButton("Save");
        levelUpButton = new JButton("Level Up");
        levelDownButton = new JButton("Level Down");

        loopService = Executors.newScheduledThreadPool(4);
    }

    /**
     * setPanelLayoutManager
     * setPanelSize
     * setPanelBackground
     * setPanelEdge
     * setTextField(userInputTF)Font
     */
    private void setting() {
        //layout
        ingameMainPanel.setLayout(new BorderLayout());
        ingameRightPanel.setLayout(new BorderLayout());
        forLifeAndUserImage.setLayout(new BorderLayout());
        contentPanel.setLayout(new BorderLayout());
        wordDropPanel.setLayout(null);

        //setSize
        ingameRightPanel.setPreferredSize(new Dimension(320, 720));
        scorePanel.setPreferredSize(new Dimension(320, 180));
        lifePanel.setPreferredSize(new Dimension(320, 180));
        userImagePanel.setPreferredSize(new Dimension(320, 350));
        userInputTF.setPreferredSize(new Dimension(ingameMainPanel.getWidth(), 50));
        ingameRightPanel.setPreferredSize(new Dimension(320, 720));

        //background
        wordDropPanel.setBackground(new Color(240, 255, 240));
        lifePanel.setBackground(new Color(230, 230, 255));
        scorePanel.setBackground(new Color(240, 255, 240));
        userImagePanel.setBackground(Color.ORANGE);

        //edge
        scorePanel.setBorder(BorderFactory.createLineBorder(Color.GREEN.brighter().brighter(), 1));
        lifePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE.brighter().brighter(), 1));
        userImagePanel.setBorder(new LineBorder(Color.GREEN, 1));

        //Font
        userInputTF.setFont(new Font("TimesRoman", Font.BOLD, 20));
    }

    /**
     * SetToolBar
     *
     * PauseButton == ScheduledExecutorService kill, if data is not changed, data still maintain
     * startButton == ScheduledExecutorService 'Rebuild'
     * saveWord and loadWord Button is testButton, this Buttons will be deleted
     */
    private void setToolBar() {
        toolBar = new JToolBar();
        stopButton = new JButton("StopButton");
        startButton.addActionListener(e -> startButtonAction());
        stopButton.addActionListener(e -> stopButtonAction());
        levelUpButton.addActionListener(e -> levelUpEvent());
        levelDownButton.addActionListener(e -> levelDownEvent());
        toolBar.add(startButton);
        toolBar.add(stopButton);
        toolBar.add(levelUpButton);
        toolBar.add(levelDownButton);
    }
    private void levelUpEvent(){
        if(selectedLevel >= 10){
            selectedLevel = 10;
        }else{
            selectedLevel++;
        }
        levelLabel.setText("Level = "+Integer.toString(selectedLevel));
    }
    private void levelDownEvent(){
        if(selectedLevel <= 1){
            selectedLevel = 1;
        }
        else{
            selectedLevel--;
        }
        levelLabel.setText("Level = "+Integer.toString(selectedLevel));
    }

    private void stopButtonAction(){
        shutdownThread();
        enemies.removeAllElements();
        wordDropPanel.removeAll();
        wordDropPanel.add(levelLabel);
        wordDropPanel.repaint();
    }

    public void shutdownThread(){
        moveState.cancel(true);
        makeWordState.cancel(true);
        logicState.cancel(true);
        drawState.cancel(true);
        loopService.shutdown();
    }

    public void startButtonAction(){
        wordDropPanel.remove(levelLabel);
        wordDropPanel.repaint();
        //TODO ScheduledFuture learn
        startThread();
    }

    public void startThread(){
        moveTask.setSelectedLevel(selectedLevel);
        loopService = new ScheduledThreadPoolExecutor(4);
        makeWordState = loopService.scheduleWithFixedDelay(makeWordTask, 0, 3000/selectedLevel, TimeUnit.MILLISECONDS);
        moveState = loopService.scheduleWithFixedDelay(moveTask, 0, 100, TimeUnit.MILLISECONDS);
        drawState = loopService.scheduleWithFixedDelay(drawTask, 0, 10, TimeUnit.MILLISECONDS);
        logicState = loopService.scheduleWithFixedDelay(logicTask, 0, 10, TimeUnit.MILLISECONDS);
    }

    public void continueGame(){
        //초기화 할 때 뭘 해야될까
        //패널 초기화 enemy초기화
        wordDropPanel.removeAll();
        enemies.removeAllElements();
        lp.changeLife(1);
        startThread();
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }
}
