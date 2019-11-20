package scene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WordManagerWindow extends PanelArray{

    private WordManagerWindow wordManagerWindowInstance;
    private JSplitPane jSplitPane;
    private JPanel displayPanel;
    private JPanel editPanel;
    private JTextArea displayTA;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton addButton;
    private JButton backButton;

    private JTextField addTF;
    private JTextField deleteTF;
    public WordManagerWindow(){
        init();

        loadWordData();
        displayWord();
        //setting
        addTF.requestFocus();
        editPanel.setLayout(new GridLayout(3,2));
        displayPanel.setPreferredSize(new Dimension(600, 700));
        editPanel.setPreferredSize(new Dimension(600, 700));
        displayTA.setEditable(false);
        displayTA.setPreferredSize(displayPanel.getPreferredSize());
        displayTA.setFont(new Font("굴림", Font.BOLD, 20));

        //add
        displayPanel.add(displayTA);
        editPanel.add(addTF);
        editPanel.add(addButton);
        editPanel.add(deleteTF);
        editPanel.add(deleteButton);
        editPanel.add(saveButton);
        editPanel.add(backButton);

        //event
        addButton.addActionListener(this::addWord);
        deleteButton.addActionListener(this::deleteWord);

        jSplitPane.setLeftComponent(displayPanel);
        jSplitPane.setRightComponent(editPanel);
        jSplitPane.resetToPreferredSizes();
        contentPanel.add(jSplitPane);
    }
    private void init(){
        jSplitPane = new JSplitPane();
        contentPanel = new JPanel();
        displayPanel = new JPanel();
        editPanel = new JPanel();
        displayTA = new JTextArea();
        saveButton = new JButton("SAVE");
        addButton = new JButton("ADD");
        deleteButton = new JButton("DELETE");
        backButton = new JButton("BACK");
        addTF = new JTextField("넣을 값을 입력");
        deleteTF = new JTextField("지울 값을 입력");
    }
    public void displayWord(){
        displayTA.setText("");
        for(String word : wordData){
            displayTA.append(word+"\n");
        }
    }
    public void loadWordData(){
        wordData = WordManage.getInstance().getWordData();
    }
    public void addWord(ActionEvent a){
        String newWord = addTF.getText();
        addTF.setText("");
        wordData.add(newWord);
        displayWord();
    }
    public void deleteWord(ActionEvent a){
        String delWord = deleteTF.getText();
        deleteTF.setText("");
        wordData.remove(delWord);
        displayWord();
    }
    public void backButtonAction(ActionEvent a){

    }

}
