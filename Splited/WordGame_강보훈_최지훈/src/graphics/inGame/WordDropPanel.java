package graphics.inGame;

import logic.Enemy;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class WordDropPanel implements CustomPanel{

    private JPanel contentPanel;
    private Consumer<SelectPanel> sceneChange;
    public WordDropPanel(){
        initialize();
    }
    private void initialize(){
        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setOpaque(false);
    }
    public JPanel getContentPanel(){
        return contentPanel;
    }
    @Override
    public void addSceneChange(Consumer<SelectPanel> sceneChange) {
        this.sceneChange = sceneChange;
    }
}
