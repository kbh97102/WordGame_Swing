package scene.Ingame;

public class Word {

    private String word;
    private int Xpos;
    private int Ypos;
    private int speed;

    public Word(String word, int Xpos, int speed) {
        this.word = word;
        this.Xpos = Xpos;
        this.Ypos = 100; //TODO 수정
        this.speed = speed;
    }

    public void changeSpeed(int newSpeed){
        speed = newSpeed;
    }
    public void changeYpos(){
        Ypos += speed;
    }

}