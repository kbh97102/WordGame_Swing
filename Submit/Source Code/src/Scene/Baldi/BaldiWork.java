package Scene.Baldi;

import javax.swing.*;

public class BaldiWork {

    private boolean isFisrtTry;
    private Runnable continueGame;
    public BaldiWork(Runnable continueGame){
        this.continueGame = continueGame;
        QuestionItem questionItem = Questions.getInstance().randomQuestion();
        String input;
        input = JOptionPane.showInputDialog(null,questionItem.getAsking(),"Baldi",JOptionPane.QUESTION_MESSAGE);
        if(input != null){
            if(input.equals(questionItem.getAnswer())){
                //TODO Continue
                continueGame.run();
            }
            else{
                JOptionPane.showConfirmDialog(null,"You DIed", "you died", JOptionPane.YES_NO_OPTION);
            }
        }
    }


}
