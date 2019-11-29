package core;

import Scene.WordManage;
import Scene.ingame.IngamePanel;

import javax.swing.*;
import java.util.Vector;

public class MainFrame {
    private JFrame mainFrame;
    private IngamePanel ingamePanel = new IngamePanel();
    private JMenuBar menuBar;
    private JMenu game;
    private JMenu menu2;
    private JMenuItem restartMenu;
    private JMenuItem pauseMenu;
    private JMenuItem item1;

    public MainFrame(){
        mainFrame = new JFrame();
        mainFrame.setTitle("WordGame");
        setMenuBar();
        mainFrame.add(ingamePanel.getContentPanel());
        mainFrame.setSize(1280,720);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
    private void setMenuBar(){
        menuBar = new JMenuBar();
        game = new JMenu("Game");
        menu2 = new JMenu("Menu2");
        restartMenu = new JMenuItem("Restart");
        pauseMenu = new JMenuItem("Pause");
        item1 = new JMenuItem("Item1");

        game.add(restartMenu);
        game.add(pauseMenu);
        menu2.add(item1);
        menuBar.add(game);
        menuBar.add(menu2);
        mainFrame.setJMenuBar(menuBar);
    }
}
