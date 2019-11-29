package scene.Ingame;

import core.MainFrame;
import scene.PanelArray;
import scene.Scene;
import scene.WordManage;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Ingame extends PanelArray {

    private Ingame inGameInstance;

    //panel
    private JPanel ingameMainPanel;
    private JPanel ingameRightPanel;
    private JPanel scorePanel;
    private JPanel lifePanel;
    private JPanel userImagePanel;

    private JLabel testLabel;

    private JTextField userInputTF;

    private JButton btn;

    private JToolBar toolBar;
    private JButton pauseButton;
    private JButton reStartButton;
    private JPanel forLifeAndUserImage;

    private ScorePanel scp;
    private LifePanel lp;
    private JButton backToMain;

    private Vector<Enemy> enemies;
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);;
    private int enemyCount = 0;
    public Ingame(){

        init();

        userInputTF.requestFocus();
        setToolBar();
        //test
        testLabel = new JLabel(generateWord());

        //layout
        ingameMainPanel.setLayout(new BorderLayout());
        ingameRightPanel.setLayout(new BorderLayout());
        forLifeAndUserImage.setLayout(new BorderLayout());

        ingameRightPanel.setPreferredSize(new Dimension(320, 720));
        scorePanel.setPreferredSize(new Dimension(320, 180));
        lifePanel.setPreferredSize(new Dimension(320, 180));
        userImagePanel.setPreferredSize(new Dimension(320, 350));



        //background
        ingameMainPanel.setBackground(new Color(240,255,240));
        lifePanel.setBackground(new Color(230,230,255));
        scorePanel.setBackground(new Color(240,255,240));
        //edge
        scorePanel.setBorder(BorderFactory.createLineBorder(Color.GREEN.brighter().brighter(), 1));
        lifePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE.brighter().brighter(), 1));
        userImagePanel.setBorder(new LineBorder(Color.GREEN, 1));
//        userInputTF.setBorder(new LineBorder(Color.GRAY, 1));

        userInputTF.setPreferredSize(new Dimension(ingameMainPanel.getWidth(),50));
        userInputTF.setFont(new Font("굴림", Font.BOLD, 20));


        //add
        ingameMainPanel.add(userInputTF, BorderLayout.SOUTH);
        ingameMainPanel.add(testLabel, BorderLayout.NORTH);

        ingameRightPanel.add(scorePanel, BorderLayout.NORTH);
        ingameRightPanel.add(forLifeAndUserImage, BorderLayout.CENTER);
        forLifeAndUserImage.add(lifePanel, BorderLayout.NORTH);

        contentPanel.add(ingameRightPanel, BorderLayout.EAST);
        contentPanel.add(ingameMainPanel, BorderLayout.CENTER);
        contentPanel.add(toolBar, BorderLayout.NORTH);

        //Thread
        Runnable GenerateRandomWord = () ->{
            synchronized (this){
                int randomIndex = (int)(Math.random()*wordData.size());
                int randomXpos = (int)(Math.random()*1000);
                int blindGenerate = (int)(Math.random()*100);
                if(blindGenerate >= 90){
                    Enemy blindEnemy = new Enemy("imgpath", wordData.get(randomIndex),randomXpos, true);
                    blindEnemy.changeBlind(true);
                    enemies.add(blindEnemy);
                    //폰트변경
                }else{
                    enemies.add(new Enemy("imgpath", wordData.get(randomIndex), randomXpos, true));
                }
                ingameMainPanel.add(enemies.get(enemyCount++).getTestWordLabel(), BorderLayout.CENTER);
            }
        };
        Runnable MoveTask = () ->{
            for(int i=0;i<enemies.size();i++){
                if(enemies.get(i).getIsAlive()){
                    //TODO 여기도 수정
                    if(enemies.get(i).getYPos() >= 800){
                        enemies.get(i).changeIsAlive(false);
                    }
                    else{
                        enemies.get(i).down();
                    }
                }
            }
        };
        Runnable Display = () ->{
            for(int i=0;i<enemies.size();i++){
                if(enemies.get(i).getIsAlive()){
                    if(enemies.get(i).isBlind() && enemies.get(i).getYPos() > 400){
                        enemies.get(i).setBlind();
                    }
                    enemies.get(i).draw();
                    System.out.println("isWork");
                }else{
                    ingameMainPanel.remove(enemies.get(i).getTestWordLabel());
                    ingameMainPanel.revalidate();
                    ingameMainPanel.repaint();
                }
            }
        };

        try{
            scheduledExecutorService.wait();
        }
        catch (InterruptedException e1){
            e1.printStackTrace();
        }


        scheduledExecutorService.scheduleWithFixedDelay(GenerateRandomWord,0,1000, TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleWithFixedDelay(MoveTask,500,500, TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleWithFixedDelay(Display,0,14, TimeUnit.MILLISECONDS);

        //Event
        backToMain.addActionListener(event -> sceneChange.accept(Scene.MAIN));
        btn.addActionListener(event -> sceneChange.accept(Scene.MAIN));
        userInputTF.addActionListener(text->{
            String input = userInputTF.getText();
            userInputTF.setText("");
            if(input.equals(testLabel.getText())){
                testLabel.setText(generateWord());
                scp.scoreIncrease();
            }
        });
    }
    private void init(){
        //panel
        enemies = new Vector<>();
        backToMain = new JButton("BackToMain");
        scp = new ScorePanel();
        lp = new LifePanel();
        contentPanel = new JPanel();
        ingameMainPanel = new JPanel();
        ingameRightPanel = new JPanel();
        ingameRightPanel.setPreferredSize(new Dimension(320, MainFrame.WINDOW_HEIGHT));
        scorePanel = scp.getScorePanel();
        lifePanel = lp.getLifePanel();
        userImagePanel = new JPanel();
        userImagePanel.setBackground(Color.ORANGE);
        forLifeAndUserImage = new JPanel();

        //ingameMainPanel
        userInputTF = new JTextField(30);

        contentPanel.setLayout(new BorderLayout());
        btn = new JButton("click");
    }
//    public Word generateWord(){
//        int randomIndex = (int)(Math.random()*wordData.size());
//        int randomXpos = (int)(Math.random()*(MainFrame.WINDOW_WIDTH-400));
//        int speed = 1; //레벨에따라 수정
//        String word = wordData.get(randomIndex);
//        Word selectedWord = new Word(word, randomXpos, speed);
//        return selectedWord;
//    }
    public String generateWord(){
        wordData = WordManage.getInstance().getWordData();
        int randomIndex = (int)(Math.random()*wordData.size());
        String word = wordData.get(randomIndex);

        return word;
    }

    public void setToolBar(){
        toolBar = new JToolBar();
        pauseButton = new JButton("pause");
        reStartButton = new JButton("Restart");
        toolBar.add(pauseButton);
        toolBar.add(reStartButton);
        toolBar.add(backToMain);
    }
}
