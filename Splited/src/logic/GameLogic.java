package logic;

import graphics.inGame.DefeatPanel;
import graphics.inGame.VictoryPanel;
import core.Music;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class GameLogic {
    private Vector<String> wordData = new Vector<>();
    private int level=1;
    private int goalCount=0;
    private int goal = 0;
    private JPanel wordDropPanel;
    private final ConcurrentLinkedQueue<Enemy> enemies;
    private ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
    private ScheduledFuture<?> generateWordFuture;
    private ScheduledFuture<?> moveWordFuture;
    private ScheduledFuture<?> drawWordFuture;
    private UpdateScore updateScore;
    private UpdateLife  updateLife;
    private Runnable updateScoreLIfe;
    private JPanel ingamePanel;
    private DefeatPanel defeatPanel = new DefeatPanel();
    private VictoryPanel victoryPanel = new VictoryPanel();
    private Music successEffectSound;
    private Music faliEffectSound;



    private int loopDelay = 1000;

    public GameLogic(UpdateLife updateLife, UpdateScore updateScore) {
        initialize();
        this.updateLife = updateLife;
        this.updateScore = updateScore;
        wordData = WordManage.getInstance().getWordData();
//        enemies = Collections.synchronizedList(new LinkedList<>());
        //TODO Study synchreonizedList and ConcurrentLinkedQueue
        enemies = new ConcurrentLinkedQueue<>();
        goal = level*10;
    }
    private void initialize(){
        defeatPanel.setReStart(() -> newGame(level));
        defeatPanel.setContinueGame(this::continueGame);
        victoryPanel.setNextLevel(() -> newGame(++level));
        successEffectSound =new Music("Resource/Music/nice.wav");
        faliEffectSound = new Music("Resource/Music/dirty.wav");
    }
    public void setUpdateScoreLIfe(Runnable updateScoreLIfe){
        this.updateScoreLIfe = updateScoreLIfe;
    }
    public void setIngamePanel(JPanel ingamePanel){
        this.ingamePanel = ingamePanel;
    }
    public void setWordDropPanel(JPanel dropPanel) {
        this.wordDropPanel = dropPanel;
    }

    public void generateWord() {
        int randomIndex = (int) (Math.random() * wordData.size());
        int randomXpos = (int) (Math.random() * 800);
        int randomHeal = (int) (Math.random() * 100);
        int randomBlind = (int) (Math.random() * 100);
        Enemy enemy = new Enemy(randomXpos, wordData.get(randomIndex));
        if (randomHeal >= 95) {
            enemy.setHeal();
        } else if (randomBlind >= 95) {
            enemy.setBlind();
        }
        enemies.add(enemy);
        wordDropPanel.add(enemy.getWordLabel());

    }

    public void moveWord() {
        for (Enemy enemy : enemies) {
            isCollide(enemy);
            checkBlind(enemy);
            enemy.move(level);
        }
    }

    public void draw() {
        for (Enemy enemy : enemies) {
            enemy.draw();
            wordDropPanel.revalidate();
            wordDropPanel.repaint();
        }

    }

    //TODO Change Collide range , lifeDecrease
    public  void isCollide(Enemy enemy) {
        checkLife();
        if (enemy.getyPos() >= 500) {
            updateLife.decreaseLife();
            updateScoreLIfe.run();
            enemies.remove(enemy);
            wordDropPanel.remove(enemy.getWordLabel());
            wordDropPanel.revalidate();
            wordDropPanel.repaint();
        }
    }
    public void checkBlind(Enemy enemy){
        if(enemy.getyPos() >= 200){
            enemy.getWordLabel().setText("Blind");
        }
    }
    public void checkLife(){
        if(updateLife.getCurrentLife().get() <= 0){
            shutdownThread();
            wordDropPanel.removeAll();
            wordDropPanel.setLayout(new FlowLayout());
            wordDropPanel.add(defeatPanel);
            wordDropPanel.revalidate();
            wordDropPanel.repaint();
        }
    }
    public void checkGoalCount(){
        if(goal <= goalCount){
            shutdownThread();
            wordDropPanel.removeAll();
            wordDropPanel.setLayout(new FlowLayout());
            wordDropPanel.add(victoryPanel);
            wordDropPanel.revalidate();
            wordDropPanel.repaint();
        }
    }
    private void runThread(){
        service = new ScheduledThreadPoolExecutor(3);
        generateWordFuture = service.scheduleAtFixedRate(this::generateWord,0,loopDelay, TimeUnit.MILLISECONDS);
        moveWordFuture = service.scheduleAtFixedRate(this::moveWord,0,loopDelay/10, TimeUnit.MILLISECONDS);
        drawWordFuture = service.scheduleAtFixedRate(this::draw,0,loopDelay/10, TimeUnit.MILLISECONDS);
    }
    public void continueGame(){
        updateLife.increaseLife();
        updateScoreLIfe.run();
        wordDropPanel.removeAll();
        wordDropPanel.setLayout(null);
        wordDropPanel.revalidate();
        wordDropPanel.repaint();
        runThread();
    }
    public void newGame(int level){
        this.level = level;
        enemies.clear();
        updateLife.initLife();
        updateScore.initScore();
        updateScoreLIfe.run();
        wordDropPanel.removeAll();
        wordDropPanel.setLayout(null);
        wordDropPanel.revalidate();
        wordDropPanel.repaint();
        runThread();
    }
    public void stopGame(Runnable displayLevel){
        shutdownThread();
        updateScore.initScore();
        updateLife.initLife();
        displayLevel.run();
    }
    private void shutdownThread(){
        try{
            generateWordFuture.cancel(true);
            moveWordFuture.cancel(true);
            drawWordFuture.cancel(true);
            service.shutdown();
        }catch(NullPointerException e1){
            System.out.println("Thread is not Running");
        }

    }
    private void checkHeal(Enemy enemy){
        if(enemy.getHealState()){
            updateLife.increaseLife();
        }
    }
    public void checkAnswer(String input){
        checkGoalCount();
        boolean isHit = false;
        for(Enemy enemy : enemies){
            if(enemy.getWord().equals(input)){
                isHit = true;
                checkHeal(enemy);
                updateScore.increaseScore();
                updateScoreLIfe.run();
                enemies.remove(enemy);
                wordDropPanel.remove(enemy.getWordLabel());
                wordDropPanel.revalidate();
                wordDropPanel.repaint();
                goalCount++;
                break;
            }
        }
        if(isHit){
            isHit = false;
            successEffectSound.startMusic();
        }else{
            faliEffectSound.startMusic();
        }
    }
    public Consumer<String> getCheckAnswer() {
        return this::checkAnswer;
    }
}
