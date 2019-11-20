package core;

import scene.*;
import scene.Ingame.Ingame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;



public class MainFrame {

    private static JFrame mainFrame;
    public final static int WINDOW_WIDTH = 1280;
    public final static int WINDOW_HEIGHT = 720;
    private JPanel test;

    HashMap<Scene, PanelArray> scenePanelArrayHashMap;

    public MainFrame(){
        mainFrame = new JFrame();
        scenePanelArrayHashMap = new HashMap<>();

//        test = Scene.MainPanel.getInstance().getPanel();
//        mainFrame.add(test);
        MainPanel mainPanel = new MainPanel();
        Ingame ingame = new Ingame();
        LevelSelect levelSelect = new LevelSelect();
        WordManagerWindow wordManagerWindow = new WordManagerWindow();

        mainPanel.addSceneChange(this::changeScene);
        ingame.addSceneChange(this::changeScene);
        levelSelect.addSceneChange(this::changeScene);
        wordManagerWindow.addSceneChange(this::changeScene);

        mainPanel.addOnExit(this::askIsExit);

        scenePanelArrayHashMap.put(Scene.MAIN, mainPanel);
        scenePanelArrayHashMap.put(Scene.INGAME,ingame);
        scenePanelArrayHashMap.put(Scene.LEVELSELECT, levelSelect);
        scenePanelArrayHashMap.put(Scene.WORDMANAGE, wordManagerWindow);



        //root 여기서 작업
        mainFrame.getContentPane().add(scenePanelArrayHashMap.get(Scene.MAIN).getPanel());

        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                askIsExit();
            }
        });

        mainFrame.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }

    public void changeScene(Scene select){
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(scenePanelArrayHashMap.get(select).getPanel());
        mainFrame.getContentPane().revalidate();
        mainFrame.getContentPane().repaint();
    }


    public static boolean getJoptionPaneShowYesNo(String message, String title){
        return JOptionPane.showConfirmDialog(mainFrame, message, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
    public void askIsExit(){
        if(getJoptionPaneShowYesNo("종료하시겠습니까", "Exit")){
            System.exit(0);
        }
    }
}
