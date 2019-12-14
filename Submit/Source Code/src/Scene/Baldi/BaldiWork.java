package Scene.Baldi;

import javax.swing.*;

public class BaldiWork {

    private boolean solved;
    private boolean isFirstTry;
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
                solved = true;
            }
            else{
                solved = false;
            }
        }
    }
    public boolean isSolved(){
        return solved;
    }

}
