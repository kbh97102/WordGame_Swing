package scene;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Vector;

public class MainPanel extends PanelArray {

    private MainPanel mainPanelInstance;

    private JLabel titleLabel;
    private JButton startButton;
    private JButton wordManageButton;
    private JButton exitButton;
    private Vector<String> wordData;

    public Runnable onExit;

    private JPanel buttonPanel;
    private JPanel titlePanel;


    public MainPanel() {
        init();



        //imageIcon
        titleLabel.setIcon(new ImageIcon("./src/Image/titleImage.png"));
        startButton.setIcon(new ImageIcon("./src/Image/startImage.png"));
        wordManageButton.setIcon(new ImageIcon("./src/Image/wordManageImage.png"));
        exitButton.setIcon(new ImageIcon("./src/Image/exitImage.png"));

        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);

        wordManageButton.setBorderPainted(false);
        wordManageButton.setContentAreaFilled(false);
        wordManageButton.setFocusPainted(false);

        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);


        titlePanel.add(titleLabel);



        buttonPanel.add(startButton);
        buttonPanel.add(wordManageButton);
        buttonPanel.add(exitButton);
        //add
        contentPanel.add(titlePanel, BorderLayout.NORTH);
        contentPanel.add(buttonPanel, BorderLayout.CENTER);
        //Event
        startButton.addActionListener(event -> sceneChange.accept(Scene.LEVELSELECT));
        wordManageButton.addActionListener(event -> sceneChange.accept(Scene.WORDMANAGE));
        exitButton.addActionListener(event -> onExit.run());
    }

    public void addOnExit(Runnable onExit) {
        this.onExit = onExit;
    }

    private void init() {
        contentPanel = new JPanel();
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));
        titlePanel = new JPanel();
        titleLabel = new JLabel();
//        titlePanel.setLayout(new GridLayout(1, 3));
        contentPanel.setLayout(new BorderLayout());
        wordData = new Vector<>();
        startButton = new JButton();
        wordManageButton = new JButton();
        exitButton = new JButton();
//        titleLabel = new JLabel("Drop the Word");
    }
}
