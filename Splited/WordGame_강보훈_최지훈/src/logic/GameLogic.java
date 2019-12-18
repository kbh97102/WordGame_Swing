package logic;

import graphics.Bonus.BonusQuestion;
import graphics.inGame.SelectPanel;
import core.Music;
import graphics.inGame.WordDropPanel;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class GameLogic {
    private Vector<String> wordData = new Vector<>();
    private int level = 1;
    private int goalCount = 0;
    private int goal = 0;
    private WordDropPanel wordDropPanel;
    private final ConcurrentLinkedQueue<Enemy> enemies;
    private ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
    private ScheduledFuture<?> generateWordFuture;
    private ScheduledFuture<?> moveWordFuture;
    private ScheduledFuture<?> drawWordFuture;
    private UpdateScore updateScore;
    private UpdateLife updateLife;
    private Runnable updateScoreLIfe;
    private Music successEffectSound;
    private Music failEffectSound;
    private Runnable updateLevel;
    private BonusQuestion bonusQuestion = new BonusQuestion();
    private Consumer<SelectPanel> sceneChange;
    private HashMap<SelectPanel, JPanel> sceneList;
    private Vector<String> easyWord = new Vector<>();
    private Vector<String> normalWord = new Vector<>();
    private Vector<String> hardWord = new Vector<>();
    private int loopDelay = 1000;

    public GameLogic(UpdateLife updateLife, UpdateScore updateScore) {
        initialize();
        this.updateLife = updateLife;
        this.updateScore = updateScore;
        wordData = WordManage.getInstance().getWordData();
        enemies = new ConcurrentLinkedQueue<>();
        goal = level * 10;
        divideWordData();
    }

    private void initialize() {
        successEffectSound = new Music("Resource/Music/drum.wav");
        failEffectSound = new Music("Resource/Music/nope.wav");
    }

    public void addSceneChange(Consumer<SelectPanel> sceneChange) {
        this.sceneChange = sceneChange;
    }

    public void addList(HashMap<SelectPanel, JPanel> sceneList) {
        this.sceneList = sceneList;
    }

    public void addUpdateLevel(Runnable updateLevel) {
        this.updateLevel = updateLevel;
    }

    public void addUpdateScoreLIfe(Runnable updateScoreLIfe) {
        this.updateScoreLIfe = updateScoreLIfe;
    }

    public void addWordDropPanel(WordDropPanel dropPanel) {
        this.wordDropPanel = dropPanel;
    }

    private void divideWordData() {
        for (String word : wordData) {
            if (word.length() <= 5) {
                easyWord.add(word);
            }
            if (5 < word.length() && word.length() <= 8) {
                normalWord.add(word);
            }
            if (8 < word.length()) {
                hardWord.add(word);
            }
        }
    }

    private void generateWord(Vector<String> wordData) {
        int randomIndex = (int) (Math.random() * wordData.size());
        int randomXpos = (int) (Math.random() * 600);
        int randomHeal = (int) (Math.random() * 100);
        int randomBlind = (int) (Math.random() * 100);
        Enemy enemy = new Enemy(randomXpos, wordData.get(randomIndex));
        if (randomHeal >= 95) {
            enemy.setHeal();
            enemy.setHealColor();
        } else if (randomBlind >= 95) {
            enemy.setBlind();
            enemy.setBlindColor();
        }
        enemies.add(enemy);
        wordDropPanel.getContentPanel().add(enemy.getWordLabel());
    }

    private void moveWord() {
        checkGoalCount();
        for (Enemy enemy : enemies) {
            isCollide(enemy);
            checkBlind(enemy);
            enemy.move(level);
        }
    }

    private void draw() {
        for (Enemy enemy : enemies) {
            enemy.draw();
            wordDropPanel.getContentPanel().revalidate();
            wordDropPanel.getContentPanel().repaint();
        }
    }

    private void isCollide(Enemy enemy) {
        checkLife();
        if (enemy.getyPos() >= 400) {
            updateLife.decreaseLife();
            updateScoreLIfe.run();
            enemies.remove(enemy);
            wordDropPanel.getContentPanel().remove(enemy.getWordLabel());
            wordDropPanel.getContentPanel().revalidate();
            wordDropPanel.getContentPanel().repaint();
        }
    }

    private void checkBlind(Enemy enemy) {
        if (enemy.getBlindState() && enemy.getyPos() >= 100) {
            enemy.getWordLabel().setText("Blind");
        }
    }

    private void checkLife() {
        if (updateLife.getCurrentLife().get() <= 0) {
            shutdownThread();
            sceneChange.accept(SelectPanel.Defeat);
        }
    }

    private void checkGoalCount() {
        if (goal <= goalCount) {
            shutdownThread();
            sceneChange.accept(SelectPanel.Victory);
        }
    }

    private void runThread() {
        service = new ScheduledThreadPoolExecutor(3);
        Vector<String> levelWordData;
        if(level <=3){
            levelWordData = easyWord;
        }
        else if(level <=7){
            levelWordData = normalWord;
        }
        else{
            levelWordData = hardWord;
        }
        generateWordFuture = service.scheduleAtFixedRate(() -> generateWord(levelWordData), 0, loopDelay, TimeUnit.MILLISECONDS);
        moveWordFuture = service.scheduleAtFixedRate(this::moveWord, 0, loopDelay / 60, TimeUnit.MILLISECONDS);
        drawWordFuture = service.scheduleAtFixedRate(this::draw, 0, loopDelay / 60, TimeUnit.MILLISECONDS);
    }

    public void continueGame() {
        updateLife.increaseLife();
        updateScoreLIfe.run();
        enemies.clear();
        wordDropPanel.getContentPanel().removeAll();
        wordDropPanel.getContentPanel().revalidate();
        wordDropPanel.getContentPanel().repaint();
        runThread();
    }

    public void nextLevel() {
        sceneChange.accept(SelectPanel.Game);
        level++;
        updateLevel.run();
        newGame(level);
    }

    public void newGame(int level) {
        wordDropPanel.getContentPanel().removeAll();
        wordDropPanel.getContentPanel().revalidate();
        wordDropPanel.getContentPanel().repaint();
        goalCount = 0;
        goal = level * 10;
        this.level = level;
        enemies.clear();
        updateLife.initLife();
        updateScore.initScore();
        updateScoreLIfe.run();
        runThread();
    }

    public void stopGame(Runnable displayLevel) {
        shutdownThread();
        updateScore.initScore();
        updateLife.initLife();
        displayLevel.run();
    }

    private void shutdownThread() {
        try {
            generateWordFuture.cancel(true);
            moveWordFuture.cancel(true);
            drawWordFuture.cancel(true);
            service.shutdown();
        } catch (NullPointerException e1) {
            System.out.println("Thread is not Running");
        }
    }

    private void checkHeal(Enemy enemy) {
        if (enemy.getHealState()) {
            updateLife.increaseLife();
        }
    }

    public void checkAnswer(String input) {
        boolean isHit = false;
        for (Enemy enemy : enemies) {
            if (enemy.getWord().equals(input)) {
                isHit = true;
                checkHeal(enemy);
                updateScore.increaseScore();
                updateScoreLIfe.run();
                enemies.remove(enemy);
                wordDropPanel.getContentPanel().remove(enemy.getWordLabel());
                wordDropPanel.getContentPanel().revalidate();
                wordDropPanel.getContentPanel().repaint();
                goalCount++;
                break;
            }
        }
        if (isHit) {
            isHit = false;
            successEffectSound.startMusic();
        } else {
            failEffectSound.startMusic();
        }
    }
}
