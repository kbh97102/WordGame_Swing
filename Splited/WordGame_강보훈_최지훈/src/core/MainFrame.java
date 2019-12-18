package core;

import graphics.WordWindow.WordWindow;
import graphics.inGame.CustomPanel;
import graphics.inGame.InGamePanel;
import graphics.inGame.SelectPanel;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MainFrame {
    private static MainFrame instance;
    private static int SCREEN_WIDTH = 800;
    private static int SCREEN_HEIGHT = 600;
    private JFrame mainFrame = new JFrame();
    private Music backgroundMusic;
    private JToolBar toolBar = new JToolBar();
    private JButton startButton = new JButton("Start");
    private JButton stopButton = new JButton("Stop");
    private JButton levelUpButton = new JButton("LevelUp");
    private JButton levelDownButton = new JButton("LevelDown");
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu;
    private JMenu wordMenu;
    private JMenuItem displayWordMenu;
    private Consumer<SelectPanel> sceneChange;
    private Consumer<Integer> newGame;
    private Runnable stopGame;
    private Runnable levelUp;
    private Runnable levelDown;
    private Supplier<Integer> getLevel;
    private Runnable setFocus;
    private MainFrame(){

    }
    public void run(){
        setMenuBar();
        setToolBar();
        InGamePanel ingamePanel = new InGamePanel();
        mainFrame.add(ingamePanel);
        backgroundMusic = new Music("Resource/Music/Background.wav");
        backgroundMusic.startLoopMusic();
        mainFrame.setTitle("Word Game");
        mainFrame.setSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
    public static MainFrame getInstance(){
        if(instance == null)
            instance = new MainFrame();
        return instance;
    }
    public void addFocus(Runnable setFocus){
        this.setFocus = setFocus;
    }
    public void addSceneChange(Consumer<SelectPanel> sceneChange){
        this.sceneChange = sceneChange;
    }
    public void addStartEvent(Consumer<Integer> newGame){
        this.newGame = newGame;
    }
    public void addStopEvent(Runnable stopGame){
        this.stopGame = stopGame;
    }
    public void addLevelUpEvent(Runnable levelUp){
        this.levelUp = levelUp;
    }
    public void addLevelDownEvent(Runnable levelDown){
        this.levelDown = levelDown;
    }
    public void addGetLevel(Supplier<Integer> getLevel){
        this.getLevel = getLevel;
    }
    private void setToolBar(){
        toolBar.add(startButton);
        toolBar.add(stopButton);
        toolBar.add(levelUpButton);
        toolBar.add(levelDownButton);
        startButton.addActionListener(event -> {
            setFocus.run();
            newGame.accept(getLevel.get());
        });
        stopButton.addActionListener(event -> stopGame.run());
        levelUpButton.addActionListener(event -> levelUp.run());
        levelDownButton.addActionListener(event -> levelDown.run());
        mainFrame.add(toolBar, BorderLayout.NORTH);
    }
    private void setMenuBar(){
        fileMenu = new JMenu("FIle");
        wordMenu = new JMenu("Word");
        displayWordMenu = new JCheckBoxMenuItem("Display");
        displayWordMenu.addActionListener(event -> WordWindow.getWordWindowInstance().displayWord());
        wordMenu.add(displayWordMenu);
        menuBar.add(fileMenu);
        menuBar.add(wordMenu);
        mainFrame.setJMenuBar(menuBar);
    }
}


