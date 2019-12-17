package graphics.Baldi;

import java.util.ArrayList;

public class Questions {
    private static Questions questionsInstance;


    private ArrayList<QuestionItem> questions;

    private Questions(){
        questions = new ArrayList<>();
        addQuestion();
    }
    private void addQuestion(){
        questions.add(new QuestionItem("뭐", "어쩌라고"));
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
