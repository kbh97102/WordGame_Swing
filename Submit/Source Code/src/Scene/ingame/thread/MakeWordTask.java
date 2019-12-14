package Scene.ingame.thread;

import Scene.ingame.Enemy;

import javax.swing.*;
import java.util.Vector;

public class MakeWordTask implements Runnable {
    private Vector<Enemy> enemies;
    private JPanel panel;
    private Vector<String> wordData;

    public MakeWordTask(Vector<Enemy> enemies, JPanel panel, Vector<String> wordData) {
        this.enemies = enemies;
        this.panel = panel;
        this.wordData = wordData;
    }

    /**
     * randomIndex == generate random enemy( = word)
     * randomXpos == default enemy's X position
     * randomHeal == if more than n, generate healing Item( = word) // 100 = 100%
     * randomBlind == if more than n, generate BlindAttack
     */
    public synchronized void run() {
        try {
            int randomIndex = (int) (Math.random() * wordData.size());
            int randomXpos = (int) (Math.random() * 800);
            int randomHeal = (int) (Math.random() * 100);
            int randomBlind = (int) (Math.random() * 100);
            Enemy enemy = new Enemy(wordData.get(randomIndex), randomXpos);
            if (randomHeal >= 95) {
                enemy.changeHeal(true);

            } else if (randomBlind >= 95) {
                enemy.changeBlind(true);
            }
            enemies.add(enemy);
            panel.add(enemy.getWordLabel());
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
