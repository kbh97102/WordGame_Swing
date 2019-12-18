package logic;

import java.util.function.Supplier;

public class UpdateLife {
    private int defaultLife = 3;
    private int currentLife;
    private Supplier<Integer> getCurrentLife;
    public UpdateLife(){
        currentLife = defaultLife;
        getCurrentLife = ()-> currentLife;
    }
    public void increaseLife(){
        currentLife += 1;
    }
    public void decreaseLife(){
        currentLife -= 1;
    }
    public Supplier<Integer> getCurrentLife(){
        return getCurrentLife;
    }
    public void initLife(){
        currentLife = defaultLife;
    }
}
