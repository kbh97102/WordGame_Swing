package graphics.Bonus;

import java.util.ArrayList;

public class Questions {
    private static Questions questionsInstance;
    private ArrayList<QuestionItem> questions;
    private Questions(){
        questions = new ArrayList<>();
        addQuestion();
    }
    private void addQuestion(){
        questions.add(new QuestionItem("HKT", "What is the Smartest Professor's Initial"));
        questions.add(new QuestionItem("WrapperClass", "What class used in ArrayList"));
        questions.add(new QuestionItem("Integer", "Result of wrapping int"));
    }
    public static Questions getInstance(){
        if(questionsInstance == null){
            questionsInstance = new Questions();
        }
        return questionsInstance;
    }
    public QuestionItem randomQuestion(){
        int randomIndex = (int)(Math.random()*questions.size());
        return questions.get(randomIndex);
    }
}
