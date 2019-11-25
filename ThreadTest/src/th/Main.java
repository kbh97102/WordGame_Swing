package th;

import java.util.Vector;

public class Main {


    public static void main(String[] args) {

        new T2();
    }


}

class Enemy {
    private String name;
    private int x;
    private int y;

    public Enemy(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public void changeX() {
        x += 10;
    }

    public void changeY() {
        y += 10;
    }

    @Override
    public String toString() {
        return x+y+name;
    }
}

class T2 {
    private Vector<Enemy> enemy;

    public T2() {
        enemy = new Vector<>();
        enemy.add(new Enemy("a", 1, 1));
        enemy.add(new Enemy("b", 2, 2));
        enemy.add(new Enemy("c", 3, 3));
        enemy.add(new Enemy("d", 4, 4));
        enemy.add(new Enemy("f", 5, 5));
        T1 t = new T1();
        t.run();
    }

    class T1 implements Runnable {

        @Override
        public void run() {
            try {
                while(true){
                    for(int i=0;i<enemy.size();i++){
                        Enemy e = enemy.get(i);
                        e.changeX();
                        e.changeY();
                        System.out.println(e.toString());

                    }
                    Thread.sleep(10);
                }

            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
}

