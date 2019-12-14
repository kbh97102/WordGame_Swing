package Scene.ingame.thread;

import Scene.Baldi.BaldiWork;
import Scene.ingame.Enemy;
import Scene.ingame.LifePanel;
import core.MainFrame;

import javax.swing.*;
import java.util.Vector;

public class LogicTask implements Runnable {
    private Vector<Enemy> enemies;
    private int goalCount;
    private LifePanel lifePanel;
    private Runnable killAll;
    private Runnable continueGame;
    private Runnable initializeGameScreen;
    private Runnable victoryScene;
    private Runnable defeatScene;

    public LogicTask(Vector<Enemy> enemies, int goalCount, LifePanel lifePanel, Runnable killAll, Runnable continueGame, Runnable initializeGameScreen, Runnable victoryScene,Runnable defeatScene) {
        this.enemies = enemies;
        this.goalCount = goalCount;
        this.lifePanel = lifePanel;
        this.killAll = killAll;
        this.initializeGameScreen = initializeGameScreen;
        this.continueGame = continueGame;
        this.victoryScene = victoryScene;
        this.defeatScene = defeatScene;
    }

    public void changeGoalCount() {
        goalCount--;
    }

    /**
     * if Victory goalCount increase and restart
     */
    public int callOptionPane(String message, String title) {
        System.out.println("call");
        int t1=JOptionPane.showConfirmDialog(null,message,title,JOptionPane.YES_NO_OPTION);
//        int t1 = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
        return t1;
    }

    public void setGoalCount(int goalCount) {
        this.goalCount = goalCount;
    }

    public synchronized void run() {
        int test = 0;
        /**
         * This for-loop perform
         *   Condition 1 Enemy is Alive
         *     if Enemy reached bottom kill this Enemy and userLife decrease
         *   Condition 2 Enemy's blind state is true, Enemy's Y position reached specific position = user can't see Word
         */
        for (Enemy enemy : enemies) {
            if (enemy.getAlive()) {
                if (enemy.isReachBottom()) {
                    enemy.changeAlive(false);
                    lifePanel.lifeDecrease();
                }

                if (enemy.isBlind() && enemy.getYpos() > 200) {
                    enemy.getWordLabel().setText("Blind");
                }
            }
        }
        /**
         * User Victory
         * Stop all task and ask to user
         * Answer is Yes == don't shutdown program // No == shutdown program
         */
        if (goalCount == 0) {
            killAll.run();
            victoryScene.run();
        }

        //TODO check DynaLIst
        /**
         * User is defeat
         * Stop all task and continue Question is working (we call Baldi)
         * If user don't want retry shutdown program
         * Baldi is the same as continue
         * If user solve ths problem at first time, user can play continue
         * But can't solve the problem, that's it, game is over
         */
        if (lifePanel.getCurrentLife() <= 0) {
            killAll.run();
            defeatScene.run();
            //TODO Input Baldi
        }
    }
}
