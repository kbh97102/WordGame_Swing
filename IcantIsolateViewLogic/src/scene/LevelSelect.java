package scene;

import javax.swing.*;
import java.awt.*;

public class LevelSelect extends PanelArray{

    private  LevelSelect levelSelectInstance;
    private JButton button;
    private EasyButton easyButton;
    private JButton normalButton;
    private JButton hardButton;

    private JPanel buttonPanel;

    private EasyButton t2;
    private EasyButton t3;

    private JLabel levelTitle;
    private JPanel levelPanel;
    public LevelSelect(){
        init();
        t2 = new EasyButton("normal", "true", "false", 2,100);
        t3 = new EasyButton("hard", "true", "true", 3, 50);


        contentPanel.setLayout(new BorderLayout(0,50));
        buttonPanel.setLayout(new GridLayout(1,5,100,0));

        //
        levelTitle.setIcon(new ImageIcon("./src/Image/leveltitleImage.png"));
        levelTitle.setHorizontalAlignment(JLabel.CENTER);
        //Girdlayout gap
        buttonPanel.add(new JPanel());
        buttonPanel.add(easyButton.getEasyPanel());
        buttonPanel.add(t2.getEasyPanel());
        buttonPanel.add(t3.getEasyPanel());
        buttonPanel.add(new JPanel());

        contentPanel.add(levelTitle, BorderLayout.NORTH);
        contentPanel.add(buttonPanel, BorderLayout.CENTER);
        //event
//        easyButton.addActionListener(event -> sceneChange.accept(Scene.INGAME));
        button.addActionListener(event-> sceneChange.accept(Scene.MAIN));
    }
    private void init(){
        levelPanel = new JPanel();
        contentPanel = new JPanel();
        button = new JButton("click");
        easyButton = new EasyButton("easy", "false", "true", 1, 300);
        normalButton = new JButton("normal");
        hardButton = new JButton("hard");
        buttonPanel = new JPanel();
        levelTitle = new JLabel();
    }

}
