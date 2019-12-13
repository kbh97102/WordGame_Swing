package Scene.ingame.thread;

import Scene.ingame.Enemy;

import javax.swing.*;
import java.util.Vector;
import java.util.concurrent.ScheduledFuture;

public class DrawTask implements Runnable {
    private Vector<Enemy> enemies;
    private JPanel panel;
    private ScheduledFuture<?> drawState;

    public DrawTask(Vector<Enemy> enemies, JPanel panel) {
        this.enemies = enemies;
        this.panel = panel;
    }

    /**
     * This task perform
     * Condition 1 Enemy is alive
     *   Draw in panel or remove in panel
     */
    public void run() {
        try {
            for (Enemy enemy : enemies) {
                if (enemy.getAlive()) {
                    enemy.draw();
                } else {
                    panel.remove(enemy.getWordLabel());
                    panel.revalidate();
                    panel.repaint();
                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
