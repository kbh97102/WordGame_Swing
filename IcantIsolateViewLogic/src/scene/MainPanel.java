package scene;

import javax.swing.*;
import java.awt.*;
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

        //수정
        titleLabel.setText("Word Game");
        titleLabel.setFont(new Font("바탕", Font.BOLD, 70));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        titlePanel.add(new JLabel());
        titlePanel.add(titleLabel);
        titlePanel.add(new JLabel());


        buttonPanel.add(startButton);
        buttonPanel.add(wordManageButton);
        buttonPanel.add(exitButton);
        //titleLabel.setBounds(320,50 , 640, 180);
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
        titlePanel.setLayout(new GridLayout(1, 3));
        contentPanel.setLayout(new BorderLayout());
        wordData = new Vector<>();
        startButton = new JButton("START");
        wordManageButton = new JButton("WORDMANAGE");
        exitButton = new JButton("EXIT");
        titleLabel = new JLabel("Drop the Word");
        titleLabel.setFont(new Font("바탕", Font.BOLD, 45));
    }
}
