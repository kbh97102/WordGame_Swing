package Scene.ingame;

import Scene.WordManage;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IngamePanel {
    //Jpanel
    private JPanel contentPanel;
    private JPanel ingameMainPanel;
    private JPanel ingameRightPanel;
    private JPanel scorePanel;
    private JPanel lifePanel;
    private JPanel userImagePanel;
    private JPanel wordDropPanel;
    private JPanel forLifeAndUserImage;
    private ScorePanel scp;
    private LifePanel lp;
    private JPanel wordManagePanel;
    private JPanel buttonPanel;
    private JPanel textFieldPanel;

    //button
    private JTextField textField;
    private JButton addButton;
    private JButton delButton;
    private JButton saveButton;
    private JButton saveWordDataButton;
    private JButton loadWordDataButton;

    //Thread
    private Runnable makeWordTask;
    private Runnable moveWordTask;
    private Runnable drawWordTask;
    private ScheduledExecutorService loopService;

    //etc
    private JTextField userInputTF;
    private JToolBar toolBar;
    private Vector<String> wordData;
    private Vector<Enemy> enemies;


    public IngamePanel() {
        init();
        setWordManagePanel();
        setting();
        userInputTF.requestFocus();
        setToolBar();


        //Thread
        makeWordTask = () -> {
            int randomIndex = (int) (Math.random() * wordData.size());
            int randomXpos = (int) (Math.random() * 800);
            Enemy normalEnemy = new Enemy(wordData.get(randomIndex), randomXpos);
            enemies.add(normalEnemy);
            wordDropPanel.add(normalEnemy.getWordLabel());
        };
        moveWordTask = () -> {
            for (Enemy enemy : enemies) {
                if (enemy.getAlive()) {
                    if (enemy.isReachBottom()) {
                        enemy.changeAlive(false);
                    } else {
                        enemy.move();
                    }
                }
            }
        };
        drawWordTask = () -> {
            for (Enemy enemy : enemies) {
                if (enemy.getAlive()) {
                    enemy.draw();
                } else {
                    wordDropPanel.remove(enemy.getWordLabel());
                    wordDropPanel.revalidate();
                    wordDropPanel.repaint();
                }
            }
        };
        loopService.scheduleWithFixedDelay(makeWordTask,0,1000, TimeUnit.MILLISECONDS);
        loopService.scheduleWithFixedDelay(moveWordTask,0,1000, TimeUnit.MILLISECONDS);
        loopService.scheduleWithFixedDelay(drawWordTask,0,100, TimeUnit.MILLISECONDS);

        //add
        ingameMainPanel.add(userInputTF, BorderLayout.SOUTH);
        ingameMainPanel.add(wordDropPanel, BorderLayout.CENTER);

        ingameRightPanel.add(scorePanel, BorderLayout.NORTH);
        ingameRightPanel.add(forLifeAndUserImage, BorderLayout.CENTER);

        forLifeAndUserImage.add(wordManagePanel, BorderLayout.CENTER);
        forLifeAndUserImage.add(lifePanel, BorderLayout.NORTH);

        contentPanel.add(ingameRightPanel, BorderLayout.EAST);
        contentPanel.add(ingameMainPanel, BorderLayout.CENTER);
        contentPanel.add(toolBar, BorderLayout.NORTH);



        //event
        userInputTF.addActionListener(event -> {
            String userInput = userInputTF.getText();
//            if(enemies.contains(userInput)){
//                enemies.get(enemies.indexOf(userInput)).changeAlive(false);
//                scp.scoreIncrease();
//            }
            for(Enemy enemy : enemies){
                if(enemy.getWord().equals(userInput)){
                    enemy.changeAlive(false);
                    scp.scoreIncrease();
                }
            }
            userInputTF.setText("");
        });
    }

    private void init() {
        //panel
        contentPanel = new JPanel();
        scp = new ScorePanel();
        lp = new LifePanel();
        contentPanel = new JPanel();
        ingameMainPanel = new JPanel();
        ingameRightPanel = new JPanel();
        scorePanel = scp.getScorePanel();
        lifePanel = lp.getLifePanel();
        userImagePanel = new JPanel();
        forLifeAndUserImage = new JPanel();
        wordDropPanel = new JPanel();
        textFieldPanel = new JPanel();
        wordManagePanel = new JPanel();
        buttonPanel = new JPanel();

        //etc
        userInputTF = new JTextField(30);
        wordData = new Vector<>();
        wordData = WordManage.getInstance().getWordData();
        enemies = new Vector<>();

        //button
        textField = new JTextField(15);
        addButton = new JButton("Add");
        delButton = new JButton("Delete");
        saveButton = new JButton("Save");

        loopService = Executors.newScheduledThreadPool(3);
    }

    private void setting() {
        //layout
        ingameMainPanel.setLayout(new BorderLayout());
        ingameRightPanel.setLayout(new BorderLayout());
        forLifeAndUserImage.setLayout(new BorderLayout());
        contentPanel.setLayout(new BorderLayout());
        wordDropPanel.setLayout(null);

        //setSize
        ingameRightPanel.setPreferredSize(new Dimension(320, 720));
        scorePanel.setPreferredSize(new Dimension(320, 180));
        lifePanel.setPreferredSize(new Dimension(320, 180));
        userImagePanel.setPreferredSize(new Dimension(320, 350));
        userInputTF.setPreferredSize(new Dimension(ingameMainPanel.getWidth(), 50));
        ingameRightPanel.setPreferredSize(new Dimension(320, 720));

        //background
        wordDropPanel.setBackground(new Color(240, 255, 240));
        lifePanel.setBackground(new Color(230, 230, 255));
        scorePanel.setBackground(new Color(240, 255, 240));
        userImagePanel.setBackground(Color.ORANGE);

        //edge
        scorePanel.setBorder(BorderFactory.createLineBorder(Color.GREEN.brighter().brighter(), 1));
        lifePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE.brighter().brighter(), 1));
        userImagePanel.setBorder(new LineBorder(Color.GREEN, 1));

        //Font
        userInputTF.setFont(new Font("굴림", Font.BOLD, 20));

    }
    private void setToolBar() {
        toolBar = new JToolBar();
        saveWordDataButton = new JButton("SaveWordData");
        loadWordDataButton = new JButton("LoadWordData");

        saveWordDataButton.addActionListener(event -> {
            WordManage.getInstance().saveWordToFile();
        });
        loadWordDataButton.addActionListener(event -> {
            wordData = WordManage.getInstance().getWordData();
        });

        toolBar.add(saveWordDataButton);
        toolBar.add(loadWordDataButton);
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public void setWordManagePanel() {
        wordManagePanel.setLayout(new GridLayout(1, 2));
        buttonPanel.setLayout(new GridLayout(3, 1));
        textFieldPanel.setLayout(new GridLayout(1, 1));

        textFieldPanel.add(textField);
        buttonPanel.add(addButton);
        buttonPanel.add(delButton);
        buttonPanel.add(saveButton);

        addButton.addActionListener(event -> {
            String input = textField.getText();
            wordData.add(input);
            textField.setText("");
        });
        delButton.addActionListener(event -> {
            String input = textField.getText();
            if (wordData.contains(input)) {
                wordData.remove(input);
            } else {
                JOptionPane.showMessageDialog(null, "삭제 할 내용이 없습니다.", "삭제 에러", JOptionPane.ERROR_MESSAGE);
            }
            textField.setText("");
        });
        saveButton.addActionListener(event -> {
            WordManage.getInstance().replaceWordData(wordData);
        });

        wordManagePanel.add(textFieldPanel);
        wordManagePanel.add(buttonPanel);
    }
}
