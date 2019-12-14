package Scene.ingame;

import Scene.Baldi.BaldiWork;
import Scene.Baldi.QuestionItem;
import Scene.Baldi.Questions;

import javax.swing.*;
import java.awt.*;

public class DefeatPanel {
    private JPanel contentpanel;
    private Runnable changeScene;
    private JButton b1;
    private JButton b2;
    private JButton b3;
    private BaldiWork baldiWork;
    private Runnable continueGame;
    private QuestionItem questionItem;

    public DefeatPanel(){

        contentpanel = new JPanel();
        contentpanel.setFocusable(true);
        contentpanel.requestFocus();
        contentpanel.setLayout(new GridLayout(1,3));
        b1 = new JButton("DefeatRestart");
        b2 = new JButton("Exit");
        b3 = new JButton("Continue?");

        b3.addActionListener(event -> asking());
        b2.addActionListener(event -> System.exit(0));
        b1.addActionListener(event -> changeScene.run());
        contentpanel.add(b1);
        contentpanel.add(b2);
        contentpanel.add(b3);
    }
    public void asking(){
        questionItem = Questions.getInstance().randomQuestion();
        String input = JOptionPane.showInputDialog(contentpanel,questionItem.getAsking(),"Question",JOptionPane.QUESTION_MESSAGE);
        if(input.equals(questionItem.getAnswer())){
            continueGame.run();
        }
    }
    public JPanel getContentpanel(){
        return contentpanel;
    }
    public void setChangeScene(Runnable changeScene){
        this.changeScene = changeScene;
    }
    public void setContinueGame(Runnable continueGame){
        this.continueGame = continueGame;
        baldiWork = new BaldiWork(continueGame);
    }
}
