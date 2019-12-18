package graphics.inGame;

import graphics.Bonus.BonusQuestion;

import javax.swing.*;
import java.util.function.Consumer;

public class DefeatPanel extends JPanel implements CustomPanel{
    private JButton reStartButton;
    private JButton exitButton;
    private JButton continueButton;
    private Runnable reStart;
    private Runnable continueGame;
    private BonusQuestion bonusQuestion = new BonusQuestion();
    private Consumer<SelectPanel> sceneChange;
    private Runnable setFocus;
    public DefeatPanel(){
        initialize();
        add(reStartButton);
        add(exitButton);
        add(continueButton);
        this.setOpaque(false);
        reStartButton.addActionListener(e ->{
            setFocus.run();
            sceneChange.accept(SelectPanel.Game);
            reStart.run();
        });
        exitButton.addActionListener(event -> System.exit(0));
        continueButton.addActionListener(event -> continueEvent());
    }
    private void initialize(){
        reStartButton = new JButton("ReStart");
        exitButton = new JButton("Exit");
        continueButton = new JButton("Continue?");
    }
    private void continueEvent(){
        if(bonusQuestion.ask()){
            setFocus.run();
            sceneChange.accept(SelectPanel.Game);
            continueGame.run();
        }
    }
    public void addFocus(Runnable setFocus){
        this.setFocus = setFocus;
    }
    public void addReStart(Runnable reStart){
        this.reStart = reStart;
    }
    public void addContinueGame(Runnable continueGame){
        this.continueGame = continueGame;
    }
    @Override
    public void addSceneChange(Consumer<SelectPanel> sceneChange) {
        this.sceneChange = sceneChange;
    }
}
