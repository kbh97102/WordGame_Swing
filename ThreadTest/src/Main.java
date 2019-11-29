package th;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.util.Vector;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        new MainFrame();
    }
}


class T1 {

    public T1() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        Runnable task = () -> System.out.println("123");
        ScheduledFuture<?> future = service.scheduleWithFixedDelay(task, 0, 3, TimeUnit.SECONDS);


    }
}


class MainFrame {

    private JFrame frame;
    private JPanel panel;
    private int count = 0;
    private String path = "./src/Image/test.png";
    private Vector<Enemy> enemy;
    private int randomIndex = 0;
    private JTextField tf;
    private JPanel p1;
    private ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
    private JButton btn;
    public MainFrame() {
        frame = new JFrame();
        panel = new JPanel();
        enemy = new Vector<>();
        tf = new JTextField(30);
        panel.setLayout(null);
        p1 = new JPanel();

        btn =new JButton("Click");

        p1.setLayout(new BorderLayout());
        p1.add(panel, BorderLayout.CENTER);
        p1.add(tf, BorderLayout.SOUTH);
        p1.add(btn, BorderLayout.EAST);






        Runnable t1 = () -> {
            synchronized (this){
                randomIndex = (int) (Math.random() * 1000);
                enemy.add(new Enemy(randomIndex, 0, count + ""));
                panel.add(enemy.get(count++).getImg());
                System.out.println("iswork");

                panel.revalidate();
                panel.repaint();
            }
        };
        Runnable t2 = () -> {
            for (int i = 0; i < enemy.size(); i++) {
                Enemy e = enemy.get(i);
                if (e.getY() > 600) {
                    enemy.remove(e);
                }
                e.down();
            }
        };
        Runnable t3 = () ->{
            tf.addActionListener(event -> {
                String input = tf.getText();
                for (int i = 0; i < enemy.size(); i++) {
                    if (enemy.get(i).word().equals(input)) {
                        panel.remove(enemy.get(i).getImg());
                        //여기가문제
                        enemy.remove(i);
                        panel.revalidate();
                        panel.repaint();
                        break;
                    }
                }
                tf.setText("");
            });
        };
        Thread test1 = new Thread(t1);
        Thread test2 = new Thread(t2);
        Thread test3 = new Thread(t3);

        btn.addActionListener(event -> {
            if(test1.isInterrupted()){
                System.out.println("test1 is down");
            }
            else{
                System.out.println("is alive");
                System.out.println(test1.getState());
            }

        });



        service.scheduleWithFixedDelay(test1, 0, 1, TimeUnit.SECONDS);
        service.scheduleWithFixedDelay(test2, 0, 500, TimeUnit.MILLISECONDS);
        service.submit(test3);


        frame.add(p1);
        frame.setSize(1280, 720);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}

class Enemy {
    private int x;
    private int y;
    private String path;
    private JLabel img;
    private int downSpeed = 20;

    public Enemy(int x, int y, String path) {
        img = new JLabel();
        this.x = x;
        this.y = y;
        this.path = path;
        img.setText(path);
        img.setBounds(x, y, 100, 20);
//        img.setIcon(new ImageIcon(path));
//        img.setBounds(x, y,100,100);
    }

    public JLabel getImg() {
        return img;
    }

    public String word() {
        return path;
    }

    public void down() {
        y += downSpeed;
        img.setLocation(x, y);
    }

    public int getY() {
        return y;
    }

}