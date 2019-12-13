package Scene.ingame.thread;

import Scene.ingame.Enemy;

import java.util.Vector;

/**
 *  This Task only perform if Enemy is alive, move to bottom
 */
public class MoveTask implements Runnable {
    private Vector<Enemy> enemies;
    private int selectedLevel;

    public MoveTask(Vector<Enemy> enemies, int selectedLevel) {
        this.enemies = enemies;
        this.selectedLevel = selectedLevel;
    }
    public void setSelectedLevel(int selectedLevel){
        this.selectedLevel = selectedLevel;
    }
    public void run() {
        try {
            for (Enemy enemy : enemies) {
                if (enemy.getAlive()) {
                    enemy.move(selectedLevel);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return;
        }
    }
}
