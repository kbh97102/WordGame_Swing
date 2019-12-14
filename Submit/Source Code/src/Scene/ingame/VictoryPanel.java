package Scene.ingame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VictoryPanel {
    private JPanel contentpanel;
    private Runnable changeScene;
    private JButton b1;
    private JButton b2;
    private JButton b3;

    public VictoryPanel(){
        this.changeScene = changeScene;
        contentpanel = new JPanel();
        contentpanel.setFocusable(true);
        contentpanel.requestFocus();
        contentpanel.setLayout(new GridLayout(1,3));
        b1 = new JButton("Restart");
        b2 = new JButton("Exit");
        b3 = new JButton("Fuck you");

        b2.addActionListener(event -> System.exit(0));
        b1.addActionListener(event -> changeScene.run());
        contentpanel.add(b1);
        contentpanel.add(b2);
        contentpanel.add(b3);

    }
    public void setChangeScene(Runnable changeScene){
        this.changeScene = changeScene;
    }
    public JPanel getContentpanel(){
        return contentpanel;
    }
}
