package graphics.inGame;

import logic.Enemy;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WordDropPanel{
    private JPanel contentPanel;

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



}
