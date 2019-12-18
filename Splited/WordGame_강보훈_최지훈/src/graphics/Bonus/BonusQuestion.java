package graphics.Bonus;

import javax.swing.*;

public class BonusQuestion {
    private boolean solved;
    public BonusQuestion(){
    }
    public boolean ask(){
        QuestionItem questionItem = Questions.getInstance().randomQuestion();
        String input;
        input = JOptionPane.showInputDialog(null,questionItem.getAsking(),"Continue?",JOptionPane.QUESTION_MESSAGE);
        if(input != null){
            System.out.println(input);
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
