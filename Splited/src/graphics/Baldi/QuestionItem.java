package graphics.Baldi;

public class QuestionItem {
    private final String answer;
    private final String asking;

    public QuestionItem(String answer, String asking){
        this.answer = answer;
        this.asking = asking;
    }
    public String getAsking(){
        return asking;
    }
    public String getAnswer(){
        return answer;
    }
}
