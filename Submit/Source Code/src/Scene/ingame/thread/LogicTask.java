package Scene.ingame.thread;

import Scene.Baldi.BaldiWork;
import Scene.ingame.Enemy;
import Scene.ingame.LifePanel;
import Scene.ingame.ScorePanel;
import Scene.ingame.test;

import javax.swing.*;
import java.util.Vector;

public class LogicTask implements Runnable {
    private test t1;
    private Vector<Enemy> enemies;
    private int goalCount;
    private Runnable killAll;
    private LifePanel lp;
    private JPanel dropPanel;
    private ScorePanel scorePanel;
    private Runnable continueGame;

    public LogicTask(Vector<Enemy> enemies, int goalCount, Runnable killAll, LifePanel lifePanel, JPanel dropPanel, ScorePanel scorePanel,Runnable continueGame) {
        this.continueGame = continueGame;
        this.enemies = enemies;
        this.goalCount = goalCount;
        this.killAll = killAll;
        this.lp = lifePanel;
        this.dropPanel = dropPanel;
        this.scorePanel = scorePanel;
        t1 = (message, title) -> JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
    }
    public void changeGoalCount(){
        goalCount--;
    }
    /**
     * if Victory goalCount increase and restart
     */
    public void run() {
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
                    lp.lifeDecrease();
                }

                if(enemy.isBlind() && enemy.getYpos() > 200){
                    enemy.getWordLabel().setText("Blind");
                }
            }
        }
        /**
         * User Victory
         * Stop all task and ask to user
         * Answer is Yes == don' shutdown program // No == shutdown program
         */
        if (goalCount == 0) {
            killAll.run();
            if (t1.callOptionPane("Victory! \nReTry?", "You are Winner!") == JOptionPane.YES_OPTION) {
                //TODO Make Reset / dropPanel, goal, life, score, enemies reset
                // level up instead of increase goalCount  and retry
                // Change just show firstScreen
                goalCount = 10+10;
                dropPanel.removeAll();
                lp.changeLife(3);
                scorePanel.scoreInit();
                enemies.removeAllElements();
                dropPanel.revalidate();
                dropPanel.repaint();
            } else {
                System.exit(0);
            }
        }

        //TODO check DynaLIst
        /**
         * User is defeat
         * Stop all task and continue Question is working (we call Baldi)
         * If user don't want retry shutdown program
         */
        if (lp.getCurrentLife() == 0) {
            try {
                killAll.run();
                /**
                 * Baldi is the same as continue
                 * If user solve ths problem at first time, user can play continue
                 * But can't solve the problem, that's it, game is over
                 */
                BaldiWork baldiWork = new BaldiWork(continueGame);

                //TODO User failed continue display OptionPane but now always show this fix it
                if (t1.callOptionPane("YouDied \nReTry?", "Defeat") == JOptionPane.YES_OPTION) {
                    //retry
                    goalCount = 10;
                    dropPanel.removeAll();
                    lp.changeLife(3);
                    scorePanel.scoreInit();
                    enemies.removeAllElements();
                    dropPanel.revalidate();
                    dropPanel.repaint();
                } else {
                    System.exit(0);
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }
}
