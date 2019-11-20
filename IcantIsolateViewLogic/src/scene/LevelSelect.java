package scene;

import javax.swing.*;
import java.awt.*;

public class LevelSelect extends PanelArray{

    private  LevelSelect levelSelectInstance;
    private JButton button;


    private JPanel buttonPanel;


    private JLabel levelTitle;
    private JPanel levelPanel;

    private EasyButton b1;
    private EasyButton b2;
    private EasyButton b3;

    public LevelSelect(){
        init();
        b1 = new EasyButton("easy", "Blind : No", "Heal : Yes", 1, 100);
        b2 = new EasyButton("normal", "Blind : No", "Heal : No", 1, 100);
        b3 = new EasyButton("hard", "Blind : Yes", "Heal : No", 1, 100);


        contentPanel.setLayout(new BorderLayout(0,50));
        buttonPanel.setLayout(new GridLayout(1,7,50,0));

        levelTitle.setIcon(new ImageIcon("./src/Image/leveltitleImage.png"));
        levelTitle.setHorizontalAlignment(JLabel.CENTER);
        buttonPanel.add(new JPanel());
        buttonPanel.add(b1.getEasyPanel());
        buttonPanel.add(new JPanel());
        buttonPanel.add(b2.getEasyPanel());
        buttonPanel.add(new JPanel());
        buttonPanel.add(b3.getEasyPanel());
        buttonPanel.add(new JPanel());

        contentPanel.add(levelTitle, BorderLayout.NORTH);
        contentPanel.add(buttonPanel, BorderLayout.CENTER);
        //event
        b1.getButton().addActionListener(event->sceneChange.accept(Scene.INGAME));
        button.addActionListener(event-> sceneChange.accept(Scene.MAIN));
    }
    private void init(){
        levelPanel = new JPanel();
        contentPanel = new JPanel();
        button = new JButton("click");
        buttonPanel = new JPanel();
        levelTitle = new JLabel();
    }

}
