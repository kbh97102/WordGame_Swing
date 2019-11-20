package scene;

import javax.swing.*;
import java.awt.*;

public class LevelSelect extends PanelArray{

    private  LevelSelect levelSelectInstance;
    private JButton button;

    public LevelSelect(){
        init();

        button.addActionListener(event-> sceneChange.accept(Scene.INGAME));
    }
    private void init(){
        contentPanel = new JPanel();
        button = new JButton("click");
        contentPanel.add(button);
        contentPanel.setBackground(Color.ORANGE);
    }

}
