package scene;

import scene.Ingame.ScorePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WordManagerWindow extends PanelArray{

    private WordManagerWindow wordManagerWindowInstance;
    private JSplitPane jSplitPane;
    private JPanel displayPanel;
    private JPanel editPanel;
    private JTextArea displayLeftTA;
    private JTextArea displayRightTA;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton addButton;
    private JButton backButton;

    private ScorePanel sp;

    private JTextField inputTF;

    private JPanel buttonPanel;

    public WordManagerWindow(){
        init();

        loadWordData();
        displayWord();
        //test
        editPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new GridLayout(4,1));
        displayPanel.setLayout(new GridLayout(1,2));

        //setting
        inputTF.requestFocus();
        displayPanel.setPreferredSize(new Dimension(1200,400));

//        editPanel.setPreferredSize(new Dimension(1200, 200));
        displayLeftTA.setEditable(false);
        displayLeftTA.setFont(new Font("굴림", Font.BOLD, 60));
        displayLeftTA.setBackground(Color.WHITE);

        displayRightTA.setEditable(false);
        displayRightTA.setFont(new Font("굴림", Font.BOLD, 60));
        displayRightTA.setBackground(Color.WHITE);

        inputTF.setFont(new Font("굴림", Font.BOLD, 20));


        //add
        displayPanel.add(displayLeftTA);
        displayPanel.add(displayRightTA);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        editPanel.add(buttonPanel, BorderLayout.EAST);
        editPanel.add(inputTF, BorderLayout.CENTER);

        //event
        addButton.addActionListener(this::addWord);
        deleteButton.addActionListener(this::deleteWord);
        backButton.addActionListener(this::backButtonAction);
        saveButton.addActionListener(event -> WordManage.getInstance().saveWordToFile());

        jSplitPane.setLeftComponent(displayPanel);
        jSplitPane.setRightComponent(editPanel);
        jSplitPane.resetToPreferredSizes();

        contentPanel.setBackground(Color.ORANGE);
        contentPanel.add(jSplitPane);
    }
    private void init(){
        buttonPanel = new JPanel();
        jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        contentPanel = new JPanel();
        displayPanel = new JPanel();
        editPanel = new JPanel();
        displayLeftTA = new JTextArea();
        displayRightTA = new JTextArea();
        saveButton = new JButton("SAVE");
        addButton = new JButton("ADD");
        deleteButton = new JButton("DELETE");
        backButton = new JButton("BACK");
        inputTF = new JTextField("단어 입력");

    }
    public void displayWord(){
        displayLeftTA.setText("");
        displayRightTA.setText("");
        for(int i=0;i<wordData.size()/2;i++){
            displayLeftTA.append(wordData.get(i)+"\n");
        }
        for(int i=wordData.size()/2+1;i<wordData.size();i++){
            displayRightTA.append(wordData.get(i)+"\n");
        }
    }
    public void loadWordData(){
        wordData = WordManage.getInstance().getWordData();
    }
    public void addWord(ActionEvent a){
        String newWord = inputTF.getText();
        inputTF.setText("");
        wordData.add(newWord);
        displayWord();
    }
    public void deleteWord(ActionEvent a){
        String delWord = inputTF.getText();
        inputTF.setText("");
        wordData.remove(delWord);
        displayWord();
    }
    public void backButtonAction(ActionEvent a){
        WordManage.getInstance().replaceWordData(wordData);
        sceneChange.accept(Scene.MAIN);
    }

}
