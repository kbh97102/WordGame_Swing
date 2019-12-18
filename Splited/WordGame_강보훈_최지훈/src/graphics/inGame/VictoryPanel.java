package graphics.inGame;

import javax.swing.*;
import java.util.function.Consumer;

public class VictoryPanel extends JPanel implements CustomPanel{
    private JButton nextLevelButton;
    private JButton exitButton;
    private Runnable nextLevel;
    private Consumer<SelectPanel> sceneChange;
    private Runnable setFocus;
    public VictoryPanel(){
        initialize();
        add(nextLevelButton);
        add(exitButton);
        this.setOpaque(false);
        nextLevelButton.addActionListener(e -> {
            setFocus.run();
            sceneChange.accept(SelectPanel.Game);
            nextLevel.run();
        });
        exitButton.addActionListener(event -> System.exit(0));
    }
    private void initialize(){
        nextLevelButton = new JButton("NextLevel");
        exitButton = new JButton("Exit");
    }
    public void addFocus(Runnable setFocus){
        this.setFocus = setFocus;
    }
    public void addNextLevel(Runnable nextLevel){
        this.nextLevel = nextLevel;
    }
    @Override
    public void addSceneChange(Consumer<SelectPanel> sceneChange) {
        this.sceneChange = sceneChange;
    }
}
