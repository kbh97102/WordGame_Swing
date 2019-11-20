package scene;

import javax.swing.*;
import java.awt.*;

public class LevelSelect extends PanelArray{

    private  LevelSelect levelSelectInstance;
    private JButton button;
    private JButton easyButton;
    private JButton normalButton;
    private JButton hardButton;

    private JPanel levelPanel;
    public LevelSelect(){
        init();

        levelPanel.setLayout(new GridLayout(7,1));
        contentPanel.setLayout(new BorderLayout());

        levelPanel.add(easyButton);
        levelPanel.add(new JLabel());
        levelPanel.add(normalButton);
        levelPanel.add(new JLabel());
        levelPanel.add(hardButton);
        levelPanel.add(new JLabel());
        levelPanel.add(button);

        contentPanel.add(levelPanel, BorderLayout.CENTER);
        contentPanel.setBackground(Color.ORANGE);

        //event
        easyButton.addActionListener(event -> sceneChange.accept(Scene.INGAME));
        button.addActionListener(event-> sceneChange.accept(Scene.MAIN));
    }
    private void init(){
        levelPanel = new JPanel();
        contentPanel = new JPanel();
        button = new JButton("click");
        easyButton = new JButton("easy");
        normalButton = new JButton("normal");
        hardButton = new JButton("hard");
    }

}
