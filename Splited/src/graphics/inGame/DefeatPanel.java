package graphics.inGame;

import graphics.Baldi.BaldiWork;

import javax.swing.*;

public class DefeatPanel extends JPanel {
    private JButton reStartButton;
    private JButton exitButton;
    private JButton continueButton;
    private Runnable reStart;
    private Runnable continueGame;
    private BaldiWork baldiWork = new BaldiWork();

    public DefeatPanel(){
        initialize();
        add(reStartButton);
        add(exitButton);
        add(continueButton);

        reStartButton.addActionListener(e -> reStart.run());
        exitButton.addActionListener(event -> System.exit(0));
        continueButton.addActionListener(event -> continueEvent());
    }
    private void initialize(){
        reStartButton = new JButton("ReStart");
        exitButton = new JButton("Exit");
        continueButton = new JButton("Continue?");
    }
    private void continueEvent(){
        if(baldiWork.ask()){
            continueGame.run();
        }
    }
    public void setReStart(Runnable reStart){
        this.reStart = reStart;
    }
    public void setContinueGame(Runnable continueGame){
        this.continueGame = continueGame;
    }
}
