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

        if (goalCount == 0) {
            killAll.run();
            if (t1.callOptionPane("Victory! \nReTry?", "You are Winner!") == JOptionPane.YES_OPTION) {
                //TODO Make Reset / dropPanel, goal, life, score, enemies reset
                // level up instead of increase goalCount  and retry
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
        if (lp.getCurrentLife() == 0) {
            try {
                killAll.run();
                BaldiWork baldiWork = new BaldiWork(continueGame);

                if (t1.callOptionPane("YouDied \nReTry?", "Defeat") == JOptionPane.YES_OPTION) {
                    //TODO SameWork goalcount==0
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
