package core;

import graphics.WordWindow.WordWindow;
import graphics.inGame.InGamePanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    private static int SCREEN_WIDTH = 1280;
    private static int SCREEN_HEIGHT = 720;
    private JFrame mainFrame = new JFrame();
    private JToolBar toolBar = new JToolBar();
    private Music backgroundMusic;


    //TODO Add menu
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu;
    private JMenu wordMenu;
    private JMenuItem displayWordMenu;


    public MainFrame(){
        setMenuBar();

        InGamePanel ingamePanel = new InGamePanel();
        mainFrame.add(ingamePanel);

        //TODO jar시 상대경로
//        backgroundMusic = new Music("../../../Resource/Music/Background.wav");
//        backgroundMusic.startLoopMusic();

        mainFrame.setTitle("Word Game");
        mainFrame.setSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
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


