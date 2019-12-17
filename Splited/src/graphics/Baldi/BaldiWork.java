package graphics.Baldi;

import javax.swing.*;

public class BaldiWork {

    private boolean solved;
    private boolean isFirstTry;
    public BaldiWork(){
    }
    public boolean ask(){
        QuestionItem questionItem = Questions.getInstance().randomQuestion();
        String input;
        input = JOptionPane.showInputDialog(null,questionItem.getAsking(),"Baldi",JOptionPane.QUESTION_MESSAGE);
        if(input != null){
            if(input.equals(questionItem.getAnswer())){
                solved = true;
            }
            else{
                solved = false;
            }
        }
        return solved;
    }
    public boolean isSolved(){
        return solved;
    }

}
