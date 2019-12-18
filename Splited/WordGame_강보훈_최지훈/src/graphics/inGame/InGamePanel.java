package graphics.inGame;

import core.MainFrame;
import logic.GameLogic;
import logic.UpdateLife;
import logic.UpdateScore;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class InGamePanel extends JPanel {
    private JPanel contentPanel = new JPanel();
    private WordDropPanel wordDropPanel = new WordDropPanel();
    private InfoPanel infoPanel;
    private UpdateScore updateScore = new UpdateScore();
    private UpdateLife updateLife = new UpdateLife();
    private GameLogic gameLogic;
    private Runnable updateScoreLife;
    private JTextField userInputTF;
    private Runnable displayLevel;
    private SelectPanel selectScene = SelectPanel.Game;
    private HashMap<SelectPanel, JPanel> sceneList = new HashMap<>();

    public InGamePanel(){
        initialize();
        setFocus();
        contentPanel.add(wordDropPanel.getContentPanel(), BorderLayout.CENTER);
        contentPanel.add(userInputTF, BorderLayout.SOUTH);
        userInputTF.addActionListener(e -> setUserInputTF());
        contentPanel.add(infoPanel.getContentPanel(), BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
    public void sceneChange(SelectPanel scene){
        contentPanel.removeAll();
        if(selectScene == SelectPanel.Defeat){
            selectScene = scene;
            contentPanel.add(infoPanel.getContentPanel(),BorderLayout.NORTH);
            contentPanel.add(userInputTF,BorderLayout.SOUTH);
        }
        if(selectScene == SelectPanel.Victory){
            selectScene = scene;
        }
        if(selectScene == SelectPanel.Game){
            selectScene = scene;
            contentPanel.add(infoPanel.getContentPanel(),BorderLayout.NORTH);
            contentPanel.add(userInputTF,BorderLayout.SOUTH);
        }
        contentPanel.add(sceneList.get(scene), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    private void initialize(){
        infoPanel = new InfoPanel(updateScore.getCurrentScore(),updateLife.getCurrentLife());
        updateScoreLife = infoPanel.getUpdateScoreLife();
        gameLogic = new GameLogic(updateLife, updateScore);
        gameLogic.addWordDropPanel(wordDropPanel);
        gameLogic.addUpdateScoreLIfe(infoPanel.getUpdateScoreLife());
        gameLogic.addUpdateLevel(() ->infoPanel.increaseLevel());
        gameLogic.addSceneChange(this::sceneChange);
        gameLogic.addList(sceneList);
        setLayout(new BorderLayout());
        userInputTF = new JTextField(30);
        userInputTF.setFont(new Font("SanSerif",Font.BOLD,20));
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setOpaque(false);
        displayLevel = () -> infoPanel.updateLevel();
        DefeatPanel defeatPanel = new DefeatPanel();
        defeatPanel.addSceneChange(this::sceneChange);
        defeatPanel.addReStart(() -> gameLogic.newGame(infoPanel.getLevel()));
        defeatPanel.addContinueGame(()->gameLogic.continueGame());
        defeatPanel.addFocus(this::setFocus);
        VictoryPanel victoryPanel = new VictoryPanel();
        victoryPanel.addNextLevel(()->gameLogic.nextLevel());
        victoryPanel.addSceneChange(this::sceneChange);
        victoryPanel.addFocus(this::setFocus);
        sceneList.put(SelectPanel.Defeat,defeatPanel);
        sceneList.put(SelectPanel.Victory,victoryPanel);
        sceneList.put(SelectPanel.Game,wordDropPanel.getContentPanel());
        MainFrame.getInstance().addSceneChange(this::sceneChange);
        MainFrame.getInstance().addLevelDownEvent(() ->infoPanel.decreaseLevel());
        MainFrame.getInstance().addLevelUpEvent(() -> infoPanel.increaseLevel());
        MainFrame.getInstance().addStartEvent(level -> gameLogic.newGame(level));
        MainFrame.getInstance().addStopEvent(this::stopButtonEvent);
        MainFrame.getInstance().addGetLevel(()-> infoPanel.getLevel());
        MainFrame.getInstance().addFocus(this::setFocus);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image defeatImage = new ImageIcon("Resource/Image/fail.jpg").getImage();
        Image victoryImage = new ImageIcon("Resource/Image/stage_clear.png").getImage();
        Image backImage = new ImageIcon("Resource/Image/back.jpg").getImage();
        HashMap<SelectPanel,Image> imageMap = new HashMap<>();
        imageMap.put(SelectPanel.Game,backImage);
        imageMap.put(SelectPanel.Defeat,defeatImage);
        imageMap.put(SelectPanel.Victory,victoryImage);
        painting(g,imageMap.get(selectScene));
    }
    public void setFocus(){
        userInputTF.setFocusable(true);
        userInputTF.requestFocus();
    }
    public void painting(Graphics g, Image image){
        g.drawImage(image,0,0,this.getWidth(),this.getHeight(),null);
    }
    private void stopButtonEvent(){
        wordDropPanel.getContentPanel().removeAll();
        wordDropPanel.getContentPanel().revalidate();
        wordDropPanel.getContentPanel().repaint();
        gameLogic.stopGame(displayLevel);
        updateScoreLife.run();
    }
    private void setUserInputTF(){
        gameLogic.checkAnswer(userInputTF.getText());
        userInputTF.setText("");
    }
}
