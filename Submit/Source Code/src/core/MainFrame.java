package core;

import Scene.WordWindow;
import Scene.ingame.IngamePanel;

import javax.swing.*;
import java.awt.event.KeyAdapter;

public class MainFrame {
    private static JFrame mainFrame;
    private IngamePanel ingamePanel = new IngamePanel();
    private JMenuBar menuBar;
    private JMenu game;
    private JMenu menu2;
    private JMenuItem restartMenu;
    private JMenuItem pauseMenu;
    private JMenuItem wordDisplayMenu;

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
        menu2 = new JMenu("Word");
        restartMenu = new JMenuItem("Restart");
        pauseMenu = new JMenuItem("Pause");
        wordDisplayMenu = new JMenuItem("Display");

        wordDisplayMenu.addActionListener(event -> WordWindow.getWordWindowInstance());


        game.add(restartMenu);
        game.add(pauseMenu);
        menu2.add(wordDisplayMenu);
        menuBar.add(game);
        menuBar.add(menu2);
        mainFrame.setJMenuBar(menuBar);
    }
    public static void requestShowConfirmDiolog(String message, String title){
        JOptionPane.showConfirmDialog(mainFrame,message, title, JOptionPane.YES_NO_OPTION);
    }
}
