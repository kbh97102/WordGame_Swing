package scene;

import javax.swing.*;
import java.util.Vector;
import java.util.function.Consumer;

public class PanelArray {

    protected JPanel contentPanel;
    protected Consumer<Scene> sceneChange;
    protected Vector<String> wordData;

    public JPanel getPanel(){
        return contentPanel;
    }
    public void addSceneChange(Consumer<Scene> sceneChange){
        this.sceneChange = sceneChange;
    }
    public void setWordData(Vector<String> wordData){
        this.wordData = wordData;
    }

}
